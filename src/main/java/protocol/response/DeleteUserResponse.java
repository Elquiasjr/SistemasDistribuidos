package protocol.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import protocol.commons.EmptyPayload;


public record DeleteUserResponse(Payload payload) implements Response<DeleteUserResponse.Payload>{
    public DeleteUserResponse(int id){
        this(new Payload("Usuário deletado com sucesso: " + id));
    }
    public record Payload( @NotBlank String mensagem){
    }
}
