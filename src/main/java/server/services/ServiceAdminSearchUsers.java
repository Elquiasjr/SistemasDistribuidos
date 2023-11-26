package server.services;

import protocol.request.AdminSearchUsersRequest;
import protocol.response.AdminSearchUsersResponse;
import protocol.response.Response;
import server.controller.UserController;
import server.dtobject.UserDTO;
import server.exceptions.ServerResponseException;
import server.validate.ValidateAdmin;
import server.validate.ValidateToken;

import java.util.List;

public class ServiceAdminSearchUsers extends ServiceTemplate{
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
