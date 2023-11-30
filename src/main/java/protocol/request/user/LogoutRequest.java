package protocol.request.user;

import protocol.request.Request;
import protocol.request.RequisitionOperations;
import protocol.request.header.Header;
import protocol.commons.EmptyPayload;

public class LogoutRequest extends Request<EmptyPayload> {
    public LogoutRequest(final String userToken){ super(new Header(RequisitionOperations.LOGOUT, userToken));}
}
