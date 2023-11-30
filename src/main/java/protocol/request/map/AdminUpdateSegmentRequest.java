package protocol.request.map;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import protocol.Optional;
import protocol.request.Request;
import protocol.request.RequisitionOperations;
import protocol.request.header.Header;

@Getter
public class AdminUpdateSegmentRequest extends Request<AdminUpdateSegmentRequest.Payload> {

    private final AdminUpdateSegmentRequest.Payload payload;

    public AdminUpdateSegmentRequest(String token, Long pdi_inicial, Long pdi_final,
                                     @Optional String aviso, @Optional Boolean acessivel){
        super(new Header(RequisitionOperations.ATUALIZAR_SEGMENTO, token));
        payload = new AdminUpdateSegmentRequest.Payload(pdi_inicial, pdi_final, aviso, acessivel);
    }


    public record Payload(@Positive Long pdi_inicial,
                          @Positive Long pdi_final,
                          String aviso,
                          Boolean acessivel){}
}
