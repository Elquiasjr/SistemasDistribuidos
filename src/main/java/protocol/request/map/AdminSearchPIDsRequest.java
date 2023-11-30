package protocol.request.map;

import protocol.request.EmptyRequest;
import protocol.request.Request;
import protocol.request.RequisitionOperations;
import protocol.request.header.Header;

public class AdminSearchPIDsRequest extends Request<EmptyRequest> {

    public AdminSearchPIDsRequest(final String token){
        super(new Header(RequisitionOperations.BUSCAR_PDIS, token));
    }
}
