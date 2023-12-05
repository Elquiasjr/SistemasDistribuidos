package protocol.response.map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import protocol.response.Response;
import server.dtobject.pdi.PDIDTO;
import server.entity.PDI;

public record AdminUpdatePDIResponse(@NotNull @Valid PDIDTO payload) implements Response<PDIDTO> {
    public static AdminUpdatePDIResponse of(PDI pdi){
        return new AdminUpdatePDIResponse(PDIDTO.of(pdi));
    }

}
