package protocol.response.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import protocol.response.Response;
import server.dtobject.user.UserDTO;
import server.entity.User;

public record AdminUpdateUserResponse(@NotNull @Valid UserDTO payload) implements Response<UserDTO> {
    public static AdminUpdateUserResponse of(User user) {
        return new AdminUpdateUserResponse(UserDTO.of(user));}
}
