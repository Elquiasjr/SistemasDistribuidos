package protocol.request.map;

import protocol.request.EmptyRequest;
import protocol.request.Request;
import protocol.request.RequisitionOperations;
import protocol.request.header.Header;

public class AdminSearchSegmentsRequest extends Request<EmptyRequest> {

    public AdminSearchSegmentsRequest(final String token){
        super(new Header(RequisitionOperations.BUSCAR_SEGMENTO, token));
    }

}
