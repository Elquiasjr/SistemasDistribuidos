package protocol.response.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import protocol.response.Response;
import server.dtobject.user.UserDTO;
import server.entity.User;

public record UpdateUserResponse(@NotNull @Valid UserDTO payload) implements Response<UserDTO> {
    public static UpdateUserResponse of(User user) {
        return new UpdateUserResponse(UserDTO.of(user));}
}
