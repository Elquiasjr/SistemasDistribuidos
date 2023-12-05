package protocol.response.map;


import jakarta.validation.constraints.NotBlank;
import protocol.response.Response;

public record AdminDeletePDIResponse(Payload payload) implements Response<AdminDeletePDIResponse.Payload> {
    public AdminDeletePDIResponse(Long id) {
        this(new AdminDeletePDIResponse.Payload("PDI deletado com sucesso: " + id));
    }
        public record Payload( @NotBlank String mensagem){
        }
}
