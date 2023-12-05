package server.services.map;

import protocol.request.map.AdminUpdateSegmentRequest;
import protocol.response.Response;
import protocol.response.map.AdminUpdateSegmentResponse;
import server.controller.SegmentController;
import server.dtobject.segment.SegmentDTO;
import server.dtobject.segment.UpdateSegment;
import server.exceptions.ServerResponseException;
import server.services.ServiceTemplate;
import server.validate.ValidateAdmin;
import server.validate.ValidateToken;

public class ServiceAdminUpdateSegment extends ServiceTemplate {

    @Override
    public Response<?> startService(String jsonString) throws ServerResponseException {
        var request = buildRequest(jsonString, AdminUpdateSegmentRequest.class);

        ValidateAdmin.validate(request.getHeader().token());
        ValidateToken.validate(request.getHeader().token());

        SegmentController controller = SegmentController.getInstance();
        var payload = request.getPayload();
        var user = UpdateSegment.builder()
                .id(controller.findSegment(payload.pdi_inicial(), payload.pdi_final()).id())
                .pdi_inicial(payload.pdi_inicial())
                .pdi_final(payload.pdi_final())
                .acessivel(payload.acessivel())
                .aviso(payload.aviso())
                .build();

        SegmentDTO updateSegment = controller.updateSegment(user);
        return new AdminUpdateSegmentResponse(updateSegment);
    }

}
