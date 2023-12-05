package protocol.response.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import protocol.response.Response;
import server.dtobject.user.UserDTO;

public record AdminSearchUserResponse(@NotNull @Valid UserDTO payload) implements Response<UserDTO> {

}
