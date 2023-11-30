package protocol.request.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import protocol.commons.EmptyPayload;
import protocol.request.Request;
import protocol.request.RequisitionOperations;
import protocol.request.header.Header;

public class SearchUserRequest extends Request<EmptyPayload> {
    public SearchUserRequest(String token){
        super(new Header(RequisitionOperations.BUSCAR_USUARIO, token));
    }

}
