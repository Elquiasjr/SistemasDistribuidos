package server.layer.interfaces;

import protocol.request.Request;
import protocol.response.Response;
import server.exceptions.ServerResponseException;

public interface Layer <Req extends Request<?>, Res extends Response<?>>{
}
