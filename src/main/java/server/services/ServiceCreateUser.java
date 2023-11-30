package server.services;

import protocol.request.user.CreateUserRequest;
import protocol.response.CreateUserResponse;
import protocol.response.Response;
import server.controller.UserController;
import server.dtobject.CreateUser;
import server.exceptions.ServerResponseException;

public class ServiceCreateUser extends ServiceTemplate {
    @Override
    public Response<?> startService(String jsonString) throws ServerResponseException {
        var request = buildRequest(jsonString, CreateUserRequest.class);

        var payload = request.getPayload();
        var user = CreateUser.builder()
                .tipo(false)
                .nome(payload.nome())
                .senha(payload.senha())
                .email(payload.email())
                .build();
        var createdUser = UserController.getInstance().createUser(user);
        return new CreateUserResponse(createdUser);

    }
}
