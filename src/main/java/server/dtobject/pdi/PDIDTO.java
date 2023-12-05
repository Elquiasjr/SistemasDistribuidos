package server.dtobject.pdi;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import server.entity.PDI;
import server.commons.Posicao;

public record PDIDTO (@Positive
                      Long id,
                      @NotBlank
                      @Size(min = 3, max = 255)
                      String nome,

                      @NotNull
                      Posicao posicao,

                      String aviso,
                      @NotNull
                      Boolean acessivel){

    public static PDIDTO of(PDI pdi) {
        if (pdi == null) {
            return null;
        }
        return new PDIDTO(pdi.getId(), pdi.getNome(), new Posicao(pdi.getX(), pdi.getY()),
                pdi.getAviso(), pdi.getAcessivel());
    }

}
