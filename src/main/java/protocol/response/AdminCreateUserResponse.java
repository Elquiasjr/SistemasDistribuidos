package protocol.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import server.entity.UserEntity;
import server.dto.UserDTO;

public record AdminCreateUserResponse(@NotNull @Valid UserDTO payload) implements Response<UserDTO> {
    public static AdminCreateUserResponse of(UserEntity user) {return new AdminCreateUserResponse(UserDTO.of(user));}
}
