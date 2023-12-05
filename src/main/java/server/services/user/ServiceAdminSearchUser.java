package server.services.user;

import protocol.request.user.AdminSearchUserRequest;
import protocol.response.user.AdminSearchUserResponse;
import protocol.response.Response;
import server.controller.UserController;
import server.dtobject.user.UserDTO;
import server.exceptions.ServerResponseException;
import server.services.ServiceTemplate;
import server.validate.ValidateAdmin;
import server.validate.ValidateToken;

public class ServiceAdminSearchUser extends ServiceTemplate {
    @Override
    public Response<?> startService(String jsonString) throws ServerResponseException{
        AdminSearchUserRequest request = buildRequest(jsonString, AdminSearchUserRequest.class);

        ValidateAdmin.validate(request.getHeader().token());
        ValidateToken.validate(request.getHeader().token());

        UserController controller = UserController.getInstance();
        UserDTO user = controller.findUser(request.getPayload().registro());
        return new AdminSearchUserResponse(user);
    }

}
