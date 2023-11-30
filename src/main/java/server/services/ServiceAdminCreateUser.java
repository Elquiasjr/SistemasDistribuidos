package server.services;

import protocol.request.user.AdminCreateUserRequest;
import protocol.response.AdminCreateUserResponse;
import protocol.response.Response;
import server.controller.UserController;
import server.dtobject.CreateUser;
import server.exceptions.ServerResponseException;
import server.validate.ValidateToken;
import server.validate.ValidateAdmin;

public class ServiceAdminCreateUser extends ServiceTemplate {

    @Override
    public Response<?> startService(String jsonString) throws ServerResponseException {
        var req = buildRequest(jsonString, AdminCreateUserRequest.class);
        ValidateAdmin.validate(req.getHeader().token());
        ValidateToken.validate(req.getHeader().token());

        var payload = req.getPayload();
        var user = CreateUser.builder()
                .tipo(true)
                .nome(payload.nome())
                .senha(payload.senha())
                .email(payload.email())
                .build();
        var createdUser = UserController.getInstance().createUser(user);
        return new AdminCreateUserResponse(createdUser);
    }
}
