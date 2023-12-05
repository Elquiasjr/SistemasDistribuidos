package protocol.response.map;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import protocol.response.Response;
import server.dtobject.segment.SegmentDTO;
import server.entity.Segment;

public record AdminUpdateSegmentResponse(@NotNull @Valid SegmentDTO payload) implements Response<SegmentDTO> {
    public static AdminUpdateSegmentResponse of (Segment segment){
        return new AdminUpdateSegmentResponse(SegmentDTO.of(segment));
    }



}
