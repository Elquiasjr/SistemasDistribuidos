package protocol.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import protocol.request.header.Header;
@Getter
public class CreateUserRequest extends Request<CreateUserRequest.Payload>{
    @NotNull(message = "payload não pode ser nulo")
    @Valid
    private final CreateUserRequest.Payload payload;

    public CreateUserRequest(final String nome, final String email,
                                 final String senha){
        super(new Header(RequisitionOperations.CADASTRAR_USUARIO, null));
        payload = new CreateUserRequest.Payload(nome, email, senha, false);
    }

    public record Payload(
            @NotBlank(message = "can´t be empty")
            @Size(min = 3, max = 255, message = "nome must be between 3 and 255 characters")
            String nome,
            @NotBlank(message = "email can´t be empty")
            @Email
            String email,
            @NotNull(message = "senha can´t be null")
            String senha,
            @NotNull(message = "tipo can´t be null")
            Boolean tipo
    ){
    }
}
