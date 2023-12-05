package protocol.response.map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import protocol.response.Response;
import protocol.response.user.AdminSearchUsersResponse;
import server.dtobject.pdi.PDIDTO;
import server.dtobject.user.UserDTO;

import java.util.List;

public record AdminSearchPDIsResponse (@NotNull @Valid Payload payload) implements Response<AdminSearchPDIsResponse.Payload> {

    public AdminSearchPDIsResponse(List<PDIDTO> pdis){this(new Payload(pdis));}
    public record Payload(List<@NotNull @Valid PDIDTO> pdis){

    }
}
