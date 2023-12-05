package server.dtobject.pdi;

import lombok.Builder;
import lombok.Getter;
import server.commons.Posicao;

@Builder
public record UpdatePDI (Long id,
                         String nome,
                         String aviso,
                         Boolean acessivel){
}
