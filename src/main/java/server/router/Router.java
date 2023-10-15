package server.router;

import com.google.gson.JsonSyntaxException;
import helper.json.JsonHelper;
import helper.validation.ConstraintViolated;
import helper.validation.ValidationHelper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import protocol.request.EmptyRequest;
import protocol.response.Response;
import server.exceptions.BadRequestException;
import server.exceptions.MethodNotAllowedException;
import server.exceptions.ServerResponseException;
import server.layer.interfaces.InitialLayer;
public class Router {

    private Map<String, InitialLayer> routes;

    public static RouterBuilder builder() { return new RouterBuilder();}

    public Response<?> server(final String string_request) throws ServerResponseException{
        EmptyRequest req;
    }
}
