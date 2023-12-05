package server.dtobject.pdi;

import lombok.Builder;
import server.commons.Posicao;

@Builder
public record CreatePDI (String nome,
                         Double x,
                         Double y,
                         String aviso,
                         Boolean acessivel){
}
