package server.services.map;


import protocol.response.Response;
import server.controller.SegmentController;
import server.dtobject.segment.SegmentDTO;
import server.exceptions.ServerResponseException;
import server.services.ServiceTemplate;
import server.validate.ValidateAdmin;
import server.validate.ValidateToken;
import protocol.request.map.AdminSearchSegmentsRequest;
import protocol.response.map.AdminSearchSegmentsResponse;

import java.util.List;

public class ServiceAdminSearchSegments extends ServiceTemplate {
    @Override
    public Response<?> startService(String jsonString) throws ServerResponseException {
        AdminSearchSegmentsRequest request = buildRequest(jsonString, AdminSearchSegmentsRequest.class);

        ValidateAdmin.validate(request.getHeader().token());
        ValidateToken.validate(request.getHeader().token());

        var segmentController = SegmentController.getInstance();
        List<SegmentDTO> segments = segmentController.findSegments();


        return new AdminSearchSegmentsResponse(segments);
    }
}
