package server.services.user;

import jwt.JwtHelper;
import protocol.request.user.DeleteUserRequest;
import protocol.request.user.LoginRequest;
import protocol.response.user.DeleteUserResponse;
import protocol.response.Response;
import server.controller.UserController;
import server.dtobject.user.DeleteUser;
import server.exceptions.ForbiddenAccessException;
import server.exceptions.ServerResponseException;
import server.services.ServiceTemplate;
import server.validate.ValidateToken;

public class ServiceDeleteUser extends ServiceTemplate {
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
