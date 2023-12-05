package server.services.user;

import protocol.request.user.AdminSearchUsersRequest;
import protocol.response.user.AdminSearchUsersResponse;
import protocol.response.Response;
import server.controller.UserController;
import server.dtobject.user.UserDTO;
import server.exceptions.ServerResponseException;
import server.services.ServiceTemplate;
import server.validate.ValidateAdmin;
import server.validate.ValidateToken;

import java.util.List;

public class ServiceAdminSearchUsers extends ServiceTemplate {
    @Override
    public Response<?> startService(String jsonString) throws ServerResponseException{
        AdminSearchUsersRequest request = buildRequest(jsonString, AdminSearchUsersRequest.class);

        ValidateAdmin.validate(request.getHeader().token());
        ValidateToken.validate(request.getHeader().token());

        var userController = UserController.getInstance();
        List<UserDTO> users = userController.findUsers();


        return new AdminSearchUsersResponse(users);
    }
}
