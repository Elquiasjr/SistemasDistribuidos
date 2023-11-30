package protocol.request.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import protocol.Optional;
import protocol.request.Request;
import protocol.request.RequisitionOperations;
import protocol.request.header.Header;

@Getter
public class UpdateUserRequest extends Request<UpdateUserRequest.Payload> {

    @NotNull(message = "payload can't be null")
    @Valid
    private final Payload payload;

    public UpdateUserRequest(String token, @Optional String email, @Optional String nome, @Optional String senha){
        super(new Header(RequisitionOperations.ATUALIZAR_USUARIO, token));
        payload = new Payload(email, nome, senha);
    }

    public record Payload(
            @Email(message = "email must be well formed")
            String email,
            @Size(min = 3, max = 255)
            String nome,
            String senha){
    }
}
