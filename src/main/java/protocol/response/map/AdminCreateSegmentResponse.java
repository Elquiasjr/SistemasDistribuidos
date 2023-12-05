package protocol.response.map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import protocol.response.Response;
import server.dtobject.segment.SegmentDTO;
import server.dtobject.user.UserDTO;
import server.entity.Segment;

public record AdminCreateSegmentResponse(@NotNull @Valid SegmentDTO payload)implements Response<SegmentDTO> {
    public static AdminCreateSegmentResponse of (Segment segment){ return new AdminCreateSegmentResponse(SegmentDTO.of(segment));}
}
