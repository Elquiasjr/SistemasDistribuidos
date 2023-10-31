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

        String serverHostname;
        int port;

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        BufferedReader stdin = null;
        String token = null;

        try {
            stdin = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Insira o ip do servidor: ");
            serverHostname = stdin.readLine();
            System.out.print("Insira a porta do servidor: ");
            port = Integer.parseInt(stdin.readLine());

            System.out.println ("Attemping to connect to host " +
                serverHostname + " on port " + port + ".");
            echoSocket = new Socket(serverHostname, port);

            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection");
            System.exit(1);
        }

        try{
            while(true) {
                Request<?> request = requestGenerator(stdin, token);
                String jsonRequest = JsonHelper.toJson(request);
                out.println(jsonRequest);
                System.out.println();
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

                if(response == null){
                    continue;
                }
                if (response instanceof LoginResponse) {
                    token = ((LoginResponse) response).payload().token();
                    System.out.println("token was set");
                }
                if(response instanceof LogoutResponse){
                    break;
                }

                System.out.println();
            }
        } catch (IOException e){
            System.out.println("erro lendo a stdin");
        }

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
                case RequisitionOperations.CADASTRAR_USUARIO:
                    return createRequest(stdin,token, CreateUserRequest.class);
            }
        }
    }

    private static <T> T createRequest(BufferedReader stdin, String token, Class<T> classType) throws IOException {
        for(Constructor<?> constructor : classType.getConstructors()) {
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
                }else if(parameters[i].getType() == Long.class){
                    constructorArguments[i] = Integer.parseInt(line);
                }else if(parameters[i].getType() == Integer.class){
                    constructorArguments[i] = Integer.parseInt(line);
                }else if(parameters[i].getType() == Boolean.class){
                    constructorArguments[i] = Boolean.parseBoolean(line);
                }else{
                    constructorArguments[i] = line;
                }
            }
            try{
                return (T) constructor.newInstance(constructorArguments);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e){
                throw new RuntimeException(e);
            }
        }

        throw new RuntimeException("Unable to create a new instance of " + classType.getName());
    }

    private static Response<?> handleResponse(String json, Request<?> request){
        Response<?> response = null;
        try{
            Class<?> classType = request.getClass();
            if(classType == LoginRequest.class){
                response = JsonHelper.fromJson(json, LoginResponse.class);
            }
            if(classType == LogoutRequest.class){
                response = JsonHelper.fromJson(json, LogoutResponse.class);
            }
            if(classType == AdminCreateUserRequest.class){
                response = JsonHelper.fromJson(json, AdminCreateUserResponse.class);
            }
            if(classType == CreateUserRequest.class){
                response = JsonHelper.fromJson(json, CreateUserResponse.class);
            }

            if(response == null || response.payload() == null){
                response = JsonHelper.fromJson(json, ErrorResponse.class);
            }
            ValidationHelper.validate(response);
            return response;
        } catch (ConstraintViolated e){
            System.err.println("Não foi possível validar a resposta\n" + e.getMessage());
            return response;
        } catch (JsonSyntaxException e){
            System.err.println("Erro no json recebido");
        }
        return null;
    }
}
