package server.dtobject.segment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import server.entity.Segment;

public record SegmentDTO(@NotNull Long id,

                         @NotNull Long pdi_inicial,
                         @NotNull Long pdi_final,
                         @NotNull Float distancia,
                         String aviso,
                         @NotNull Boolean acessivel) {

    public static SegmentDTO of(Segment segment) {
        if (segment == null) {
            return null;
        }
        return new SegmentDTO(segment.getId(), segment.getPdi_inicial(), segment.getPdi_final(),
                segment.getDistancia(), segment.getAviso(), segment.getAcessivel());
    }
}
