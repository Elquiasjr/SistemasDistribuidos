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
import protocol.request.user.AdminUpdateUserRequest;

@Getter
public class AdminUpdatePDIRequest extends Request<AdminUpdatePDIRequest.Payload> {

    @NotNull
    @Valid
    private final AdminUpdatePDIRequest.Payload payload;

    public AdminUpdatePDIRequest(String token, Long id, @Optional String nome,
                                 @Optional int x, @Optional int y, @Optional String aviso, @Optional Boolean acessivel){
        super(new Header(RequisitionOperations.ATUALIZAR_PDI, token));
        payload = new Payload(id, nome, new Posicao(x, y), aviso, acessivel);
    }
    public record Posicao(
            int x, int y
    ){}

    public record Payload(
            @Positive(message = "id can't be null")
            Long id,
            @Size(min = 3, max = 255)
            String nome,
            Posicao posicao,
            String aviso,
            Boolean acessivel
    ){}
}
