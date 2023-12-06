package server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import server.dtobject.pdi.CreatePDI;
import server.dtobject.pdi.UpdatePDI;

@Entity
@Table(name = "PDI")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class PDI {
    @NotNull
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min= 3, max = 255)
    @NaturalId(mutable = true)
    private String nome;

    @NotNull
    private Double x;

    @NotNull
    private Double y;

    private String aviso;

    @NotNull
    private Boolean acessivel;

    public PDI(){
    }

    public static PDI of(CreatePDI pdi){
        var entity = new PDI();
        entity.setNome(pdi.nome());
        entity.setX(pdi.x());
        entity.setY(pdi.y());
        entity.setAviso(pdi.aviso());
        entity.setAcessivel(pdi.acessivel());
        return entity;
    }

    public static PDI of(UpdatePDI pdi) {
        var entity = new PDI();
        entity.setId(pdi.id());
        entity.setNome(pdi.nome());
        entity.setAviso(pdi.aviso());
        entity.setAcessivel(pdi.acessivel());
        return entity;
    }

    public void update(PDI info) {
        if(info.getNome() != null) {
            setNome(info.getNome());
        }
        if(info.getAviso() != null) {
            setAviso(info.getAviso());
        }
        if(info.getAcessivel() != null) {
            setAcessivel(info.getAcessivel());
        }
    }

}
