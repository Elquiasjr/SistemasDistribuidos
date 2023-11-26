package protocol.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import protocol.request.header.Header;

@Getter
public class AdminSearchUserRequest extends Request<AdminSearchUserRequest.Payload> {
    @NotNull(message = "payload can't be null")
    @Valid
    private final Payload payload;

    public AdminSearchUserRequest(String token, Long registro){
        super(new Header(RequisitionOperations.ADMIN_BUSCAR_USUARIO, token));
        payload = new Payload(registro);
        }

    public record Payload(@Positive long registro){}

}
