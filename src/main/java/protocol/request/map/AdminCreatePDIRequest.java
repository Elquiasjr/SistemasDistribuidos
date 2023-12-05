package protocol.request.map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import protocol.Optional;
import protocol.request.Request;
import protocol.request.RequisitionOperations;
import protocol.request.header.Header;
import server.commons.Posicao;

@Getter
public class AdminCreatePDIRequest extends Request<AdminCreatePDIRequest.Payload> {

    @NotNull(message = "payload não pode ser nulo")
    @Valid
    private final AdminCreatePDIRequest.Payload payload;

    public AdminCreatePDIRequest(final String token, final String nome,
                                 final Double x, final Double y, @Optional String aviso, Boolean acessivel){
        super(new Header(RequisitionOperations.CADASTRAR_PDI, token));
        payload = new AdminCreatePDIRequest.Payload(nome, new Posicao(x,y), aviso, acessivel);
    }

    public record Payload(
            @NotBlank(message="PDI must have a name")
            @Size(min=3, max=255, message = "field must contain between 3 and 255 characters")
            String nome,
            @NotNull(message = "position can't be null")
            Posicao posicao,
            String aviso,
            @NotNull(message = "acessible can't be null")
            Boolean acessivel
    ){
    }
}
