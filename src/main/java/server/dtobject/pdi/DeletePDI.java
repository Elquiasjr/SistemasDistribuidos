package server.dtobject.pdi;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record DeletePDI  (@NonNull Long pdiToDelete){}
