package server.dtobject.user;

import jakarta.validation.constraints.*;
import server.entity.User;

public record UserDTO(
        @NotBlank
        @Size(min = 3, max = 255)
        String nome,
        @NotBlank
        @Email
        String email,
        @NotNull
        Boolean tipo,
        @Positive
        Long registro
) {
    public static UserDTO of(User user){
        if(user == null){
            return null;
        }
        return new UserDTO(user.getNome(), user.getEmail(), user.getTipo(), user.getRegistro());
    }
}
