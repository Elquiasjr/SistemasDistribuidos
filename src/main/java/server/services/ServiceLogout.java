package server.services;

import com.google.gson.JsonSyntaxException;
import helper.json.JsonHelper;
import helper.validation.ConstraintViolated;
import helper.validation.ValidationHelper;
import protocol.request.user.LogoutRequest;
import protocol.response.LogoutResponse;
import protocol.response.Response;
import server.exceptions.BadRequestException;
import server.exceptions.ServerResponseException;

public class ServiceLogout extends ServiceTemplate {

    public Response<?> startService(String jsonString) throws ServerResponseException{
        LogoutRequest logoutRequest = new LogoutRequest(jsonString);
        try {
            var request = JsonHelper.fromJson(jsonString, logoutRequest.getClass());
            ValidationHelper.validate(request);
            return new LogoutResponse();
        } catch (JsonSyntaxException e) {
            throw new BadRequestException("");
        } catch (ConstraintViolated e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
