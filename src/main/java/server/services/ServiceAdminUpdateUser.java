package server.services;

import jwt.JwtHelper;
import protocol.request.user.AdminUpdateUserRequest;
import protocol.response.Response;
import protocol.response.UpdateUserResponse;
import server.controller.UserController;
import server.dtobject.UpdateUser;
import server.dtobject.UserDTO;
import server.exceptions.ServerResponseException;
import server.validate.ValidateAdmin;
import server.validate.ValidateToken;

public class ServiceAdminUpdateUser extends ServiceTemplate {
    @Override
    public Response<?> startService(String jsonString) throws ServerResponseException{
        var request = buildRequest(jsonString, AdminUpdateUserRequest.class);

        ValidateAdmin.validate(request.getHeader().token());
        ValidateToken.validate(request.getHeader().token());

        UserController controller = UserController.getInstance();
        var payload = request.getPayload();
        var user = UpdateUser.builder()
                .senha(payload.senha())
                .email(payload.email())
                .nome(payload.nome())
                .tipo(payload.tipo())
                .registro(payload.registro())
                .build();
        if(payload.registro() == JwtHelper.getId(request.getHeader().token()) && user.tipo()!=null){
            if(!user.tipo()) {
                controller.verifyUniqueAdmin();
            }
        }
        UserDTO updateUser = controller.updateUser(user);
        return new UpdateUserResponse(updateUser);
    }
}
