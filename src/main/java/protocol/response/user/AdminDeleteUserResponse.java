package protocol.response.user;

import jakarta.validation.constraints.NotBlank;
import protocol.response.Response;


public record AdminDeleteUserResponse(Payload payload) implements Response<AdminDeleteUserResponse.Payload> {
    public AdminDeleteUserResponse(Long id){
        this(new AdminDeleteUserResponse.Payload("Usuário deletado com sucesso: " + id));
    }
    public record Payload( @NotBlank String mensagem){
    }
}
