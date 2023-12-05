package protocol.response.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import protocol.response.Response;
import server.dtobject.user.UserDTO;
import server.entity.User;

public record CreateUserResponse(@NotNull @Valid UserDTO payload) implements Response<UserDTO> {
    public static CreateUserResponse of(User user) {return new CreateUserResponse(UserDTO.of(user));}

}
