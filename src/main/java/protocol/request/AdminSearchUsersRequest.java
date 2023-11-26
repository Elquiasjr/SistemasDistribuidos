package protocol.request;

import protocol.request.header.Header;

public class AdminSearchUsersRequest extends Request<EmptyRequest> {
    public AdminSearchUsersRequest(final String token){
        super(new Header(RequisitionOperations.ADMIN_BUSCAR_USUARIOS, token));
    }
}
