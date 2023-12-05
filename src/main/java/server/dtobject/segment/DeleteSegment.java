package server.dtobject.segment;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
public record DeleteSegment(@NonNull Long idToDelete, @NonNull Long pdiInicial,
                            @NonNull Long pdiFinal) {}