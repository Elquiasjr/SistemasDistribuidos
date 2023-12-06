package server.services.map;

import protocol.request.map.AdminDeletePDIRequest;
import protocol.request.map.AdminDeleteSegmentRequest;
import protocol.response.Response;
import protocol.response.map.AdminDeletePDIResponse;
import protocol.response.map.AdminDeleteSegmentResponse;
import server.controller.PDIController;
import server.controller.SegmentController;
import server.dtobject.pdi.DeletePDI;
import server.dtobject.segment.DeleteSegment;
import server.exceptions.ServerResponseException;
import server.services.Service;
import server.services.ServiceTemplate;
import server.validate.ValidateAdmin;
import server.validate.ValidateToken;

public class ServiceAdminDeletePDI extends ServiceTemplate {

    @Override
    public Response<?> startService(String jsonString) throws ServerResponseException {
        var request = buildRequest(jsonString, AdminDeletePDIRequest.class);
        ValidateAdmin.validate(request.getHeader().token());
        ValidateToken.validate(request.getHeader().token());

        var payload = request.getPayload();

        var pdi = DeletePDI.builder()
                .pdiToDelete(payload.id())
                .build();

        Long idDelete = pdi.pdiToDelete();
        PDIController.getInstance().deletePDI(pdi);

        SegmentController.getInstance().deleteLinkedSegments(idDelete);

        return new AdminDeletePDIResponse(payload.id());
    }
}
