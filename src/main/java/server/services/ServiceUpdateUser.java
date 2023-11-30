package server.services;

import jwt.JwtHelper;
import protocol.request.user.UpdateUserRequest;
import protocol.response.AdminUpdateUserResponse;
import protocol.response.Response;
import server.controller.UserController;
import server.dtobject.UpdateUser;
import server.dtobject.UserDTO;
import server.exceptions.ServerResponseException;

public class ServiceUpdateUser extends ServiceTemplate {

    @Override
    public Response<?> startService(String jsonString) throws ServerResponseException{
        var request = buildRequest(jsonString, UpdateUserRequest.class);

        var payload = request.getPayload();
        long id = JwtHelper.getId(request.getHeader().token());

        var user = UpdateUser.builder()
                .senha(payload.senha())
                .email(payload.email())
                .nome(payload.nome())
                .registro(id)
                .build();

        UserDTO updateUser = UserController.getInstance().updateUser(user);

        return new AdminUpdateUserResponse(updateUser);
    }

}
