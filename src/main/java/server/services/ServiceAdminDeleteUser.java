package server.services;

import com.mysql.cj.x.protobuf.MysqlxCrud.Delete;

import jwt.JwtHelper;
import protocol.request.AdminDeleteUserRequest;
import protocol.response.AdminDeleteUserResponse;
import protocol.response.Response;
import server.exceptions.ServerResponseException;
import server.validate.ValidateAdmin;
import server.validate.ValidateToken;
import server.controller.UserController;
import server.dtobject.DeleteUser;

public class ServiceAdminDeleteUser extends ServiceTemplate{
    @Override
    public Response<?> startService(String jsonString) throws ServerResponseException {
        var request = buildRequest(jsonString, AdminDeleteUserRequest.class);
        ValidateAdmin.validate(request.getHeader().token());
        ValidateToken.validate(request.getHeader().token());

        long verifyRegistro = JwtHelper.getId(request.getHeader().token());
        boolean isAdm = JwtHelper.getAdminStatus(request.getHeader().token());
        var payload = request.getPayload();
        var user = DeleteUser.builder()
                .registroToVerify(verifyRegistro)
                .isAdmin(isAdm)
                .registroToDelete(payload.registro())
                .build();

        UserController controller = UserController.getInstance();
        controller.findUser(payload.registro());
        UserController.getInstance().deleteUser(user);
        return new AdminDeleteUserResponse(payload.registro());
    }
}
