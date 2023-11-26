package protocol.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import protocol.request.header.Header;

@Getter
public class AdminDeleteUserRequest extends Request<AdminDeleteUserRequest.Payload> {
    @NotNull(message = "payload can't be null")
    @Valid
    private final Payload payload;

    public AdminDeleteUserRequest(final String token, final Long registro){
        super(new Header(RequisitionOperations.ADMIN_DELETAR_USUARIO, token));
        payload = new Payload(registro);
    }
    public record Payload (@NotNull Long registro){}
}
