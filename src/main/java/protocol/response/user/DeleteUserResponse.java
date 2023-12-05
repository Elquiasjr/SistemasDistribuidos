package protocol.response.user;

import jakarta.validation.constraints.NotBlank;
import protocol.response.Response;


public record DeleteUserResponse(Payload payload) implements Response<DeleteUserResponse.Payload> {
    public DeleteUserResponse(int id){
        this(new Payload("Usuário deletado com sucesso: " + id));
    }
    public record Payload( @NotBlank String mensagem){
    }
}
