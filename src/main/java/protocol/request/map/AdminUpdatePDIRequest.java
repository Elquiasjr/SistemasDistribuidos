package protocol.request.map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import protocol.Optional;
import protocol.request.Request;
import protocol.request.RequisitionOperations;
import protocol.request.header.Header;
import server.commons.Posicao;

@Getter
public class AdminUpdatePDIRequest extends Request<AdminUpdatePDIRequest.Payload> {

    @NotNull
    @Valid
    private final AdminUpdatePDIRequest.Payload payload;

    public AdminUpdatePDIRequest(String token, Long id, @Optional String nome,
                                  @Optional String aviso, @Optional Boolean acessivel){
        super(new Header(RequisitionOperations.ATUALIZAR_PDI, token));
        payload = new Payload(id, nome, aviso, acessivel);
    }

    public record Payload(
            @Positive(message = "id can't be null")
            Long id,
            @Size(min = 3, max = 255)
            String nome,
            String aviso,
            Boolean acessivel
    ){}
}
