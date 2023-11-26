package server.services;

import jwt.JwtHelper;
import protocol.request.SearchUserRequest;
import protocol.response.Response;
import protocol.response.SearchUserResponse;
import server.controller.UserController;
import server.exceptions.ServerResponseException;
import server.validate.ValidateToken;

public class ServiceSearchUser extends ServiceTemplate{
    @Override
    public Response<?> startService(String jsonString) throws ServerResponseException {
        var request = buildRequest(jsonString, SearchUserRequest.class);

        ValidateToken.validate(request.getHeader().token());
        long userId = JwtHelper.getId(request.getHeader().token());
        var user = UserController.getInstance().findUser(userId);

        return new SearchUserResponse(user);
    }
}