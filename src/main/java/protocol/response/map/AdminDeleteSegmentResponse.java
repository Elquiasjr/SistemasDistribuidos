package protocol.response.map;

import jakarta.validation.constraints.NotBlank;
import protocol.response.Response;

public record AdminDeleteSegmentResponse (Payload payload) implements Response<AdminDeleteSegmentResponse.Payload> {
    public AdminDeleteSegmentResponse(Long idInicial, Long idFinal) {
        this(new AdminDeleteSegmentResponse.Payload("Segmento entre os pontos " +
                idInicial + " e " + idFinal + " foi deletado"));
    }

    public record Payload( @NotBlank String mensagem){
    }
}
