package protocol.request.map;

import protocol.request.EmptyRequest;
import protocol.request.Request;
import protocol.request.RequisitionOperations;
import protocol.request.header.Header;

public class AdminSearchPDIsRequest extends Request<EmptyRequest> {

    public AdminSearchPDIsRequest(final String token){
        super(new Header(RequisitionOperations.BUSCAR_PDIS, token));
    }
}
