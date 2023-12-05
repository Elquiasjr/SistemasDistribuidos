package protocol.request.map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import protocol.Optional;
import protocol.request.Request;
import protocol.request.RequisitionOperations;
import protocol.request.header.Header;

@Getter
public class AdminCreateSegmentRequest extends Request<AdminCreateSegmentRequest.Payload> {

    @NotNull(message = "payload can't be null")
    @Valid
    private final AdminCreateSegmentRequest.Payload payload;

    public AdminCreateSegmentRequest(final String token, final Long pdi_inicial,
                                     final Long pdi_final, @Optional String aviso, Boolean acessivel ){
        super( new Header(RequisitionOperations.CADASTRAR_SEGMENTO, token));
        payload = new AdminCreateSegmentRequest.Payload(pdi_inicial, pdi_final, aviso, acessivel);
    }

    public record Payload(@NotNull(message="Segment must have a pdi_inicial")
                          Long pdi_inicial,
                          @NotNull(message="Segment must have a pdi_final")
                          Long pdi_final,
                          String aviso,
                          @NotNull(message = "acessible can't be null")
                          Boolean acessivel){}
}
