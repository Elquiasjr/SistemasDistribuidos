package protocol.response.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import protocol.response.Response;

public record LogoutResponse(@NotNull @Valid Payload payload) implements Response<LogoutResponse.Payload> {
    public LogoutResponse() {this(new Payload("desconectado"));}

    public record Payload(String mensagem){

    }
}
