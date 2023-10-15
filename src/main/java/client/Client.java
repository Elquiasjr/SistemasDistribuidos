package client;

import com.google.gson.JsonSyntaxException;
import protocol.request.*;
import protocol.response.*;
import protocol.request.header.Header;
import protocol.Optional;

import java.io.*;
import java.net.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

import helper.json.JsonHelper;
import helper.validation.ConstraintViolated;
import helper.validation.ValidationHelper;





public class Client {
    public static void main(String[] args) throws IOException {

        String serverHostname = "127.0.0.1";
        int port = 10008;

        System.out.println ("Attemping to connect to host " +
                serverHostname + " on port " + port + ".");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        BufferedReader stdin = null;
        String token = null;

        try {
            echoSocket = new Socket(serverHostname, 10008);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            stdin = new BufferedReader(new InputStreamReader(System.in));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: " + serverHostname);
            System.exit(1);
        }

        try{
            while(true) {
                Request<?> request = requestGenerator(stdin, token);
                String jsonRequest = JsonHelper.toJson(request);
                System.out.println(jsonRequest);
                System.out.println("Objeto de envio criado: " + request);
                System.out.println("Enviado: " + jsonRequest);
                System.out.println();

                String jsonResponse = in.readLine();
                if(jsonResponse == null){
                    System.err.println("Erro recebendo dados do servidor");
                    break;
                }
                System.out.println("Recebido: " + jsonResponse);
                Response<?> response = handleResponse(jsonResponse, request);
                System.out.println("Objeto criado: " + response);



            }
        } catch (IOException e){
            System.out.println("erro lendo a stdin");
        }

        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));
        String userInput;

        System.out.println ("Type Message (\"Bye.\" to quit)");
        while ((userInput = stdIn.readLine()) != null)
        {
            out.println(userInput);

            // end loop
            if (userInput.equals("Bye."))
                break;

            System.out.println("echo: " + in.readLine());
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }

    private static Request<?> requestGenerator(BufferedReader stdin, String token) throws IOException{
        String operation;

        while(true) {
            System.out.print("Insira a operação: ");
            operation = stdin.readLine();
            if(operation == null){
                throw new IOException();
            }

            switch(operation){
                case RequisitionOperations.LOGIN:
                    return createRequest(stdin,token, LoginRequest.class);
                case RequisitionOperations.LOGOUT:
                    return createRequest(stdin,token, LogoutRequest.class);
                case RequisitionOperations.ADMIN_CADASTRAR_USUARIO:
                    return createRequest(stdin,token, AdminCreateUserRequest.class);
            }
        }
    }

    private static Response<?> handleResponse(String json, Request<?> request){
        Response<?> response = null;
        try{
            Class<?> clazz = request.getClass();
            if(clazz == LoginRequest.class){
                response = JsonHelper.fromJson(json, LoginResponse.class);
            }
            if(clazz == LogoutRequest.class){
                response = JsonHelper.fromJson(json, LogoutResponse.class);
            }
            if(clazz == AdminCreateUserRequest.class){
                response = JsonHelper.fromJson(json, AdminCreateUserResponse.class);
            }

            if(response == null || response.payload() == null){
                response = JsonHelper.fromJson(json, ErrorResponse.class);
            }
            ValidationHelper.validate(response);
            return response;
        } catch (ConstraintViolated e){
            System.err.println("Não foi possível valida a resposta\n" + e.getMessage());
            return response;
        } catch (JsonSyntaxException e){
            System.err.println("Erro no json recebido");
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> T createRequest(BufferedReader stdin, String token, Class<T> clazz) throws IOException {
        for(Constructor<?> constructor : clazz.getConstructors()) {
            Parameter[] parameters = constructor.getParameters();
            boolean shouldSkip = false;

            for(Parameter parameter : parameters){
                if(parameter.getType() == Header.class){
                    shouldSkip = true;
                    break;
                }
            }
            if(shouldSkip){
                // it is the default constructors
                continue;
            }

            Object[] constructorArguments = new Object[parameters.length];
            for(int i = 0; i < parameters.length; i++){
                if(parameters[i].getName().toLowerCase().contains("token")){
                    constructorArguments[i] = token;
                    continue;
                }
                System.out.print(parameters[i].getName());
                if(parameters[i].isAnnotationPresent(Optional.class)){
                    System.out.println(" (opcional)");
                }
                System.out.print(": ");
                String line = stdin.readLine();
                if(line.isBlank() || line.isEmpty()){
                    constructorArguments[i] = null;
                }else if(parameters[i].getType() == Integer.class){
                    constructorArguments[i] = Integer.parseInt(line);
                }else if(parameters[i].getType() == Boolean.class){
                    constructorArguments[i] = Boolean.parseBoolean(line);
                }else{
                    constructorArguments[i] = line;
                }
            }
            // this casting is fine, but the compiler cant be sure because its generic type
            // was erased up there
            try{
                return (T) constructor.newInstance(constructorArguments);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e){
                throw new RuntimeException(e);
            }
        }

        throw new RuntimeException("Unable to create a new instance of " + clazz.getName());
    }
}
