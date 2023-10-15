package protocol.request;

import protocol.commons.EmptyPayload;
import protocol.request.header.Header;
public class EmptyRequest extends Request<EmptyPayload> {
    public EmptyRequest(final Header  header) { super(header);}
}
