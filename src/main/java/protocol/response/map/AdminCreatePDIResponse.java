package protocol.response.map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import protocol.response.Response;
import server.dtobject.pdi.PDIDTO;
import server.entity.PDI;

public record AdminCreatePDIResponse (@NotNull @Valid PDIDTO payload) implements Response<PDIDTO> {

    public static AdminCreatePDIResponse of(PDI pdi) { return new AdminCreatePDIResponse(PDIDTO.of(pdi));}
}
