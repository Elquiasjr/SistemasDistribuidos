package protocol.response;

import jakarta.validation.constraints.NotBlank;


public record AdminDeleteUserResponse(Payload payload) implements Response<AdminDeleteUserResponse.Payload>{
    public AdminDeleteUserResponse(Long id){
        this(new AdminDeleteUserResponse.Payload("Usuário deletado com sucesso: " + id));
    }
    public record Payload( @NotBlank String mensagem){
    }
}
