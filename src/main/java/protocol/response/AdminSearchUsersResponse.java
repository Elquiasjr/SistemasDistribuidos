package protocol.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import server.dtobject.UserDTO;

import java.util.List;

public record AdminSearchUsersResponse(@NotNull @Valid Payload payload) implements Response<AdminSearchUsersResponse.Payload> {
    public AdminSearchUsersResponse(List<UserDTO> usuarios) {this(new Payload(usuarios));}

    public record Payload(List<@NotNull @Valid UserDTO> usuarios){

    }
}
