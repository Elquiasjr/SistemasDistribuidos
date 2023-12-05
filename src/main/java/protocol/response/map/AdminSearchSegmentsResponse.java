package protocol.response.map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import protocol.response.Response;
import protocol.response.user.AdminSearchUsersResponse;
import server.dtobject.segment.SegmentDTO;
import server.dtobject.user.UserDTO;

import java.util.List;

public record AdminSearchSegmentsResponse (@NotNull @Valid Payload payload) implements Response<AdminSearchSegmentsResponse.Payload> {
    public AdminSearchSegmentsResponse(List<SegmentDTO> segmentos) {this(new Payload(segmentos));}

    public record Payload(List<@NotNull @Valid SegmentDTO> segmentos){

    }
}
