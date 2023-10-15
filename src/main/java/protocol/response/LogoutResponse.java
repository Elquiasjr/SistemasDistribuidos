package protocol.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;

public record LogoutResponse(@NotNull @Valid Payload payload) implements Response<LogoutResponse.Payload> {
    public LogoutResponse() {this(new Payload("desconectado"));}

    public record Payload(String mensagem){

    }
}
