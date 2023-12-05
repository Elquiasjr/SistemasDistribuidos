package server.dtobject.segment;

import lombok.Builder;

@Builder
public record CreateSegment ( Long pdi_inicial,
                             Long pdi_final,
                             Double distancia,
                             String aviso,
                             Boolean acessivel){
}
