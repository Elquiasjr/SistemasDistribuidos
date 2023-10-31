package protocol.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import server.entity.User;
import server.dtobject.UserDTO;

public record AdminCreateUserResponse(@NotNull @Valid UserDTO payload) implements Response<UserDTO> {
    public static AdminCreateUserResponse of(User user) {return new AdminCreateUserResponse(UserDTO.of(user));}
}
