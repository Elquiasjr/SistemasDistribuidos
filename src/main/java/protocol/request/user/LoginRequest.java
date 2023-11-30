package protocol.request.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import protocol.request.Request;
import protocol.request.RequisitionOperations;
import protocol.request.header.Header;

@Getter
public class LoginRequest extends Request<LoginRequest.Payload> {
    @NotNull(message = "payload não pode ser nulo")
    @Valid
    private final Payload payload;

    public LoginRequest(final String email, final String senha){
        super(new Header(RequisitionOperations.LOGIN, null));
        payload = new Payload(email, senha);
    }

    public record Payload(
            @NotBlank(message = "email não pode estar vazio")
            @Email
            String email,
            @NotBlank(message = "senha não pode estar vazio") String senha) {

    }

}
