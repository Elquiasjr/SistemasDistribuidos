package protocol.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import server.dtobject.UserDTO;
import server.entity.User;

public record CreateUserResponse(@NotNull @Valid UserDTO payload) implements Response<UserDTO> {
    public static protocol.response.CreateUserResponse of(User user) {return new protocol.response.CreateUserResponse(UserDTO.of(user));}

}
