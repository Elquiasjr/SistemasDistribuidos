package server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import server.dtobject.segment.CreateSegment;
import server.dtobject.segment.UpdateSegment;

@Entity
@Table(name = "Segment", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"pdi_inicial", "pdi_final"})})
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Segment {
    @NotNull
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Long pdi_inicial;

    @NotNull
    private Long pdi_final;

    @NotNull
    private Float distancia;

    private String aviso;

    @NotNull
    private Boolean acessivel;

    public Segment() {

    }

    public static Segment of(CreateSegment segment) {
        var entity = new Segment();
        entity.setPdi_inicial(segment.pdi_inicial());
        entity.setPdi_final(segment.pdi_final());
        entity.setDistancia(segment.distancia());
        entity.setAviso(segment.aviso());
        entity.setAcessivel(segment.acessivel());
        return entity;
    }

    public static Segment of(UpdateSegment segment) {
        var entity = new Segment();
        entity.setPdi_inicial(segment.pdi_inicial());
        entity.setPdi_final(segment.pdi_final());
        entity.setAviso(segment.aviso());
        entity.setAcessivel(segment.acessivel());
        return entity;
    }

    public void update(Segment info) {
        if(info.getPdi_inicial() != null) {
            setPdi_inicial(info.getPdi_inicial());
        }
        if(info.getPdi_final() != null) {
            setPdi_final(info.getPdi_final());
        }
        if(info.getDistancia() != null) {
            setDistancia(info.getDistancia());
        }
        if(info.getAviso() != null) {
            setAviso(info.getAviso());
        }
        if(info.getAcessivel() != null) {
            setAcessivel(info.getAcessivel());
        }
    }
}