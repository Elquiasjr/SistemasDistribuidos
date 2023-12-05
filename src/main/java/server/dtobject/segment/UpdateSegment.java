package server.dtobject.segment;

import lombok.Builder;

@Builder
public record UpdateSegment(Long id,
                            Long pdi_inicial,
                            Long pdi_final,
                            String aviso,
                            Boolean acessivel) {}