package server.services.map;

import protocol.request.map.AdminCreatePDIRequest;
import protocol.response.Response;
import protocol.response.map.AdminCreatePDIResponse;
import server.commons.Posicao;
import server.controller.PDIController;
import server.dtobject.pdi.CreatePDI;
import server.exceptions.ServerResponseException;
import server.services.ServiceTemplate;
import server.validate.ValidateAdmin;
import server.validate.ValidateToken;

public class ServiceAdminCreatePDI extends ServiceTemplate {
    @Override
    public Response<?> startService(String jsonString) throws ServerResponseException {
        var req = buildRequest(jsonString, AdminCreatePDIRequest.class);
        ValidateAdmin.validate(req.getHeader().token());
        ValidateToken.validate(req.getHeader().token());

        var payload = req.getPayload();
        var pdi = CreatePDI.builder()
                .nome(payload.nome())
                .x(payload.posicao().x())
                .y(payload.posicao().y())
                .aviso(payload.aviso())
                .acessivel(payload.acessivel())
                .build();
        var createdPDI = PDIController.getInstance().createPDI(pdi);
        return new AdminCreatePDIResponse(createdPDI);
    }
}
