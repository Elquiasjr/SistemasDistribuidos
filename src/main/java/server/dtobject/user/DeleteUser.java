package server.dtobject.user;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record DeleteUser(@NonNull Long registroToVerify , @NonNull Boolean isAdmin, @NonNull Long registroToDelete){}