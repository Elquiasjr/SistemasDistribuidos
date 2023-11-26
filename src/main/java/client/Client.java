package client;

import client.interfaces.*;
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
import java.util.Objects;

import helper.json.JsonHelper;
import helper.validation.ConstraintViolated;
import helper.validation.ValidationHelper;
import server.dtobject.DeleteUser;


public class Client {
    public static void main(String[] args) throws IOException {

        String serverHostname;
        int port;

        Socket echoSocket;
        PrintWriter out = null;
        BufferedReader in = null;
        BufferedReader stdin = null;
        String token = null;

        try {
            stdin = new BufferedReader(new InputStreamReader(System.in));
            //InitialPage initialPage  = new InitialPage(null);
            //port = initialPage.getPort();
            //serverHostname = initialPage.getIp();

            serverHostname = "localhost";
            port = 24800;
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
        } catch (IllegalArgumentException e) {
            System.err.println("Arguments for port or server are not appropriate");
            System.exit(1);
        }
        while(token == null){
            token  = landingPage(out, in);
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

    public static String displayTerminal(Request<?> request, String jsonRequest, BufferedReader in) throws IOException {
        System.out.println();
        System.out.println("Objeto de envio criado: " + request);
        System.out.println("Enviado: " + jsonRequest);
        System.out.println();
        String jsonResponse = in.readLine();
        if(jsonResponse == null){
            System.err.println("Erro recebendo dados do servidor");
        }else {
            System.out.println("Recebido: " + jsonResponse);
            Response<?> response = handleResponse(jsonResponse, request);
            System.out.println("Objeto criado: " + response);
            if (response instanceof LoginResponse) {
                System.out.println("token was set");
                return ((LoginResponse) response).payload().token();
            }
        }
        return null;
    }
    public static String landingPage(PrintWriter out, BufferedReader in) throws IOException {
        LandingPage landingPage = new LandingPage(null);
        if(landingPage.getOperation() == null){
            return null;
        }
        if(landingPage.getOperation().equals(RequisitionOperations.LOGIN)){
            LoginRequest request = new LoginRequest(landingPage.getEmail(), landingPage.getPassword());
            String jsonRequest = JsonHelper.toJson(request);
            out.println(jsonRequest);
            return displayTerminal(request, jsonRequest, in);
        }else if(landingPage.getOperation().equals(RequisitionOperations.CADASTRAR_USUARIO)){
            CreateUserPage cUP = new CreateUserPage(null);
            CreateUserRequest request = new CreateUserRequest(cUP.getUserName(), cUP.getUserEmail(), cUP.getUserPassword());
            String jsonRequest = JsonHelper.toJson(request);
            out.println(jsonRequest);
            displayTerminal(request, jsonRequest, in);
        }
        return null;
    }

    private static Request<?> requestGenerator(BufferedReader stdin, String token) throws IOException{
        UserOptions userOptions = new UserOptions(null);
        String operation = userOptions.getOperation();

        while(true) {
//            System.out.print("Insira a operação: ");
//            operation = stdin.readLine();
            if(operation == null){
                throw new IOException();
            }

            switch(operation){
                case RequisitionOperations.LOGOUT:
                    return new LogoutRequest(token);
                case RequisitionOperations.ADMIN_CADASTRAR_USUARIO:
                    CreateUserPage AdminCUP = new CreateUserPage(null);
                    return new AdminCreateUserRequest(token, AdminCUP.getUserName(), AdminCUP.getUserEmail(), AdminCUP.getUserPassword());
                case RequisitionOperations.CADASTRAR_USUARIO:
                    CreateUserPage cUP = new CreateUserPage(null);
                    return new CreateUserRequest(cUP.getUserName(), cUP.getUserEmail(), cUP.getUserPassword());
                case RequisitionOperations.ADMIN_DELETAR_USUARIO:
                    AdminDeleteUserPage adminDUP = new AdminDeleteUserPage(null);
                    return new AdminDeleteUserRequest(token, adminDUP.getId());
                case RequisitionOperations.ADMIN_ATUALIZAR_USUARIO:
                    AdminUpdateUserPage adminUUPage = new AdminUpdateUserPage(null);
                    return new AdminUpdateUserRequest(token, adminUUPage.getUserID(), adminUUPage.getUserEmail(), adminUUPage.getUserName(),
                            adminUUPage.getUserPassword(), adminUUPage.getUserType());
                case RequisitionOperations.ADMIN_BUSCAR_USUARIOS:
                    return new AdminSearchUsersRequest(token);
                case RequisitionOperations.ADMIN_BUSCAR_USUARIO:
                    AdminSearchUserPage adminSUP = new AdminSearchUserPage(null);
                    return new AdminSearchUserRequest(token, adminSUP.getId());
                case RequisitionOperations.DELETAR_USUARIO:
                    DeleteUserPage dUP = new DeleteUserPage(null);
                    return new DeleteUserRequest(token, dUP.getUserEmail(), dUP.getUserPassword());
                case RequisitionOperations.ATUALIZAR_USUARIO:
                    UpdateUserPage UUPage = new UpdateUserPage(null);
                    return new UpdateUserRequest(token, UUPage.getUserEmail(), UUPage.getUserName(), UUPage.getUserPassword());
                case RequisitionOperations.BUSCAR_USUARIO:
                    return new SearchUserRequest(token);

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
                    System.out.print(" (opcional)");
                }
                System.out.print(": ");
                String line = stdin.readLine();
                if(line.isBlank() || line.isEmpty()){
                    constructorArguments[i] = null;
                }else if(parameters[i].getType() == Long.class){
                    constructorArguments[i] = Long.parseLong(line);
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
                if(response != null && response.payload() != null){
                    MessagePage messagePage = new MessagePage(null, "Message", "Login was successful!");
                }
            }
            if(classType == LogoutRequest.class){
                response = JsonHelper.fromJson(json, LogoutResponse.class);
                if(response != null && response.payload() != null){
                    MessagePage messagePage = new MessagePage(null, "Message", "Logout was successful!");
                }
            }
            if(classType == AdminCreateUserRequest.class){
                response = JsonHelper.fromJson(json, AdminCreateUserResponse.class);
                if(response != null && response.payload() != null){
                    MessagePage messagePage = new MessagePage(null, "Message", "New admin created with success!");
                }
            }
            if(classType == CreateUserRequest.class){
                response = JsonHelper.fromJson(json, CreateUserResponse.class);
                if(response != null && response.payload() != null){
                    MessagePage messagePage = new MessagePage(null, "Message", "New user created with success!");
                }
            }
            if(classType == AdminDeleteUserRequest.class){
                AdminDeleteUserResponse adminDUResponse = JsonHelper.fromJson(json, AdminDeleteUserResponse.class);
                if(adminDUResponse != null && adminDUResponse.payload() != null){
                    MessagePage messagePage = new MessagePage(null, "Message", adminDUResponse.payload().mensagem());
                }
            }
            if(classType == DeleteUserRequest.class){
                response = JsonHelper.fromJson(json, DeleteUserResponse.class);
                if(response != null && response.payload() != null){
                    MessagePage messagePage = new MessagePage(null, "Message", "Account deleted with success!");
                }
            }
            if(classType == AdminSearchUserRequest.class){
                AdminSearchUserResponse adminSUResponse = JsonHelper.fromJson(json, AdminSearchUserResponse.class);
                if(adminSUResponse != null && adminSUResponse.payload() != null){
                    DisplayUserPage displayUserPage = new DisplayUserPage(null, "User Found", adminSUResponse.payload());
                }
            }
            if(classType == AdminSearchUsersRequest.class){
                AdminSearchUsersResponse adminSUsersResponse = JsonHelper.fromJson(json, AdminSearchUsersResponse.class);
                if(adminSUsersResponse != null && adminSUsersResponse.payload() != null){
                    DisplayUsersPage displayUsersPage = new DisplayUsersPage(null, adminSUsersResponse.payload().usuarios());
                }
            }
            if(classType == AdminUpdateUserRequest.class){
                AdminUpdateUserResponse adminUUResponse = JsonHelper.fromJson(json, AdminUpdateUserResponse.class);
                if(adminUUResponse != null && adminUUResponse.payload() != null){
                    DisplayUserPage displayUserPage = new DisplayUserPage(null, "User Updated", adminUUResponse.payload());
                }
            }
            if(classType == SearchUserRequest.class){
                SearchUserResponse sUResponse = JsonHelper.fromJson(json, SearchUserResponse.class);
                if(sUResponse != null && sUResponse.payload() != null){
                    DisplayUserPage displayUserPage = new DisplayUserPage(null, "User Found", sUResponse.payload());
                }
            }
            if(classType == UpdateUserRequest.class){
                UpdateUserResponse uUResponse = JsonHelper.fromJson(json, UpdateUserResponse.class);
                if(uUResponse != null && uUResponse.payload() != null){
                    DisplayUserPage displayUserPage = new DisplayUserPage(null, "User Updated", uUResponse.payload());
                }
            }

            if(response == null || response.payload() == null){
                response = JsonHelper.fromJson(json, ErrorResponse.class);
                ErrorResponse errorResponse = (ErrorResponse) response;
                MessagePage messagePage = new MessagePage(null, "Error", errorResponse.payload().mensagem());
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
