package server.services.map;

import jwt.JwtHelper;
import protocol.request.map.AdminDeleteSegmentRequest;
import protocol.request.user.AdminDeleteUserRequest;
import protocol.response.Response;
import protocol.response.map.AdminDeleteSegmentResponse;
import protocol.response.user.AdminDeleteUserResponse;
import server.controller.SegmentController;
import server.controller.UserController;
import server.dtobject.segment.DeleteSegment;
import server.dtobject.user.DeleteUser;
import server.exceptions.ServerResponseException;
import server.services.Service;
import server.services.ServiceTemplate;
import server.validate.ValidateAdmin;
import server.validate.ValidateToken;

public class ServiceAdminDeleteSegment extends ServiceTemplate {

    @Override
    public Response<?> startService(String jsonString) throws ServerResponseException {
        var request = buildRequest(jsonString, AdminDeleteSegmentRequest.class);
        ValidateAdmin.validate(request.getHeader().token());
        ValidateToken.validate(request.getHeader().token());

        var payload = request.getPayload();

        SegmentController controller = SegmentController.getInstance();
        var searchedSegment = controller.findSegment(payload.pdi_inicial(), payload.pdi_final());

        var segment = DeleteSegment.builder()
                .idToDelete(searchedSegment.id())
                .pdiInicial(payload.pdi_inicial())
                .pdiFinal(payload.pdi_final())
                .build();


        SegmentController.getInstance().deleteSegment(segment);
        return new AdminDeleteSegmentResponse(payload.pdi_inicial(), segment.pdiFinal());
    }
}
