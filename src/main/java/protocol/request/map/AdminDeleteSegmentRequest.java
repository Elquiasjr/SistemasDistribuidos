package protocol.request.map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import protocol.request.Request;
import protocol.request.RequisitionOperations;
import protocol.request.header.Header;

@Getter
public class AdminDeleteSegmentRequest extends Request<AdminDeleteSegmentRequest.Payload> {

    @NotNull(message = "payload can't be null")
    @Valid
    private final AdminDeleteSegmentRequest.Payload payload;

    public AdminDeleteSegmentRequest(final String token, final Long pdi_inicial,
                                     final Long pdi_final){
        super(new Header(RequisitionOperations.DELETAR_SEGMENTO, token));
        payload = new Payload(pdi_inicial, pdi_final);
    }

    public record Payload (
            @Positive Long pdi_inicial,
            @Positive Long pdi_final
    ){}
}
