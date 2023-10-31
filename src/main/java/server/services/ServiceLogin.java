package server.services;

import server.exceptions.ServerResponseException;
import protocol.response.LoginResponse;
import protocol.response.Response;
import protocol.request.LoginRequest;
import server.controller.UserController;

public class ServiceLogin extends ServiceTemplate {
    @Override
    public Response<?> startService(String jsonString) throws ServerResponseException{
        LoginRequest loginRequest = buildRequest(jsonString, LoginRequest.class);

        String token = UserController.getInstance().login(loginRequest.getPayload());
        return new LoginResponse(token);
    }
}
