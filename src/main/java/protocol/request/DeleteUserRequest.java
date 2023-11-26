package protocol.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import protocol.request.header.Header;

@Getter
public class DeleteUserRequest extends Request<DeleteUserRequest.Payload>{
    @NotNull(message = "payload can't be null")
    @Valid
    private final Payload payload;

    public DeleteUserRequest(final String token, final String email, final String senha){
        super(new Header(RequisitionOperations.DELETAR_USUARIO, token));
        payload = new Payload(email, senha);
    }
    public record Payload (@Email(message = "Email can't be empty")
                           @NotBlank(message = "Should be a valid email")
                           String email,
                           @NotBlank(message = "Senha can't be empty")
                           String senha){}
}
