package protocol.request.user;

import protocol.request.EmptyRequest;
import protocol.request.Request;
import protocol.request.RequisitionOperations;
import protocol.request.header.Header;

public class AdminSearchUsersRequest extends Request<EmptyRequest> {
    public AdminSearchUsersRequest(final String token){
        super(new Header(RequisitionOperations.ADMIN_BUSCAR_USUARIOS, token));
    }
}
