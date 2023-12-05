package server.services.map;


import jwt.JwtHelper;
import protocol.request.map.AdminUpdatePDIRequest;
import protocol.response.Response;
import protocol.response.map.AdminUpdatePDIResponse;
import server.controller.PDIController;
import server.dtobject.pdi.PDIDTO;
import server.dtobject.pdi.UpdatePDI;
import server.exceptions.ServerResponseException;
import server.services.ServiceTemplate;
import server.validate.ValidateAdmin;
import server.validate.ValidateToken;

public class ServiceAdminUpdatePDI extends ServiceTemplate {

    @Override
    public Response<?> startService(String jsonString) throws ServerResponseException {
        var request = buildRequest(jsonString, AdminUpdatePDIRequest.class);

        ValidateAdmin.validate(request.getHeader().token());
        ValidateToken.validate(request.getHeader().token());

        PDIController controller = PDIController.getInstance();
        var payload = request.getPayload();
        var pdi = UpdatePDI.builder()
                .id(payload.id())
                .nome(payload.nome())
                .aviso(payload.aviso())
                .acessivel(payload.acessivel())
                .build();
        PDIDTO updatePDI = controller.updatePDI(pdi);
        return new AdminUpdatePDIResponse(updatePDI);
    }
}
