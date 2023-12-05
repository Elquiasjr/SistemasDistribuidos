package protocol.response.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import protocol.response.Response;
import server.entity.User;
import server.dtobject.user.UserDTO;

public record AdminCreateUserResponse(@NotNull @Valid UserDTO payload) implements Response<UserDTO> {
    public static AdminCreateUserResponse of(User user) {return new AdminCreateUserResponse(UserDTO.of(user));}
}
