package server.services.user;

import server.exceptions.ServerResponseException;
import protocol.response.user.LoginResponse;
import protocol.response.Response;
import protocol.request.user.LoginRequest;
import server.controller.UserController;
import server.services.ServiceTemplate;

public class ServiceLogin extends ServiceTemplate {
    @Override
    public Response<?> startService(String jsonString) throws ServerResponseException{
        LoginRequest loginRequest = buildRequest(jsonString, LoginRequest.class);

        String token = UserController.getInstance().login(loginRequest.getPayload());
        return new LoginResponse(token);
    }
}
