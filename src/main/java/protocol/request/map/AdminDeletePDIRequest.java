package protocol.request.map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import protocol.request.Request;
import protocol.request.RequisitionOperations;
import protocol.request.header.Header;

@Getter
public class AdminDeletePDIRequest extends Request<AdminDeletePDIRequest.Payload> {

    @NotNull(message = "payload can't be null")
    @Valid
    private final AdminDeletePDIRequest.Payload payload;

    public AdminDeletePDIRequest(final String token, final Long id){
        super(new Header(RequisitionOperations.DELETAR_PDI, token));
        payload = new Payload(id);
    }

    public record Payload(@Positive Long id){}
}
