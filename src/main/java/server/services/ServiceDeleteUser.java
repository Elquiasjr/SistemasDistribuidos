package server.services;

import jwt.JwtHelper;
import protocol.request.AdminDeleteUserRequest;
import protocol.request.DeleteUserRequest;
import protocol.request.LoginRequest;
import protocol.response.AdminDeleteUserResponse;
import protocol.response.DeleteUserResponse;
import protocol.response.Response;
import server.controller.UserController;
import server.dtobject.DeleteUser;
import server.entity.User;
import server.exceptions.ForbiddenAccessException;
import server.exceptions.ServerResponseException;
import server.validate.ValidateAdmin;
import server.validate.ValidateToken;

public class ServiceDeleteUser extends ServiceTemplate{
    @Override
    public Response<?> startService(String jsonString) throws ServerResponseException {
        var request = buildRequest(jsonString, DeleteUserRequest.class);
        ValidateToken.validate(request.getHeader().token());

        UserController controller = UserController.getInstance();
        LoginRequest.Payload loginValidation = new LoginRequest.Payload(request.getPayload().email(),
                request.getPayload().senha());
        String validationToken = controller.login(loginValidation);

        long requestUserId = JwtHelper.getId(request.getHeader().token());
        long loginUserId = JwtHelper.getId(validationToken);

        if(requestUserId != loginUserId){
            throw new ForbiddenAccessException();
        }
        String token = request.getHeader().token();
        var deleteUser = new DeleteUser(requestUserId, JwtHelper.getAdminStatus(token), requestUserId);
        controller.deleteUserPreparation(deleteUser, loginValidation.email(), loginValidation.senha());
        return new DeleteUserResponse(JwtHelper.getId(request.getHeader().token()));
    }
}
