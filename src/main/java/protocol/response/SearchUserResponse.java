package protocol.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import server.dtobject.UserDTO;

public record SearchUserResponse(@NotNull @Valid UserDTO payload) implements Response<UserDTO> {
}
