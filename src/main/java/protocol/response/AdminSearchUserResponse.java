package protocol.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import server.dtobject.UserDTO;

public record AdminSearchUserResponse(@NotNull @Valid UserDTO payload) implements Response<UserDTO> {
}
