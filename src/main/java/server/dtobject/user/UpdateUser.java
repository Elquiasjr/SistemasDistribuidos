package server.dtobject.user;

import lombok.Builder;

@Builder
public record UpdateUser (long registro, String email, String nome, String senha, Boolean tipo){
}
