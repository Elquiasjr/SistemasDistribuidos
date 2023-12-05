package server.services.map;

import protocol.request.map.AdminCreateSegmentRequest;
import protocol.response.Response;
import protocol.response.map.AdminCreateSegmentResponse;
import server.commons.EuclidianDistance;
import server.commons.Posicao;
import server.controller.PDIController;
import server.controller.SegmentController;
import server.dtobject.pdi.PDIDTO;
import server.dtobject.segment.CreateSegment;
import server.exceptions.ServerResponseException;
import server.services.ServiceTemplate;
import server.validate.ValidateAdmin;
import server.validate.ValidateToken;

public class ServiceAdminCreateSegment extends ServiceTemplate  {
    @Override
    public Response<?> startService(String jsonString) throws ServerResponseException{
        var req = buildRequest(jsonString, AdminCreateSegmentRequest.class);
        ValidateAdmin.validate(req.getHeader().token());
        ValidateToken.validate(req.getHeader().token());

        var payload = req.getPayload();

        PDIController controller = PDIController.getInstance();
        PDIDTO pdi_inicial = controller.findPDI(payload.pdi_inicial());
        PDIDTO pdi_final = controller.findPDI(payload.pdi_final());

        Posicao posInicial = pdi_inicial.posicao();

        Posicao posFinal = pdi_final.posicao();

        Double distanciaSegmento = EuclidianDistance.CalculateDistance(posInicial, posFinal);

        var segment = CreateSegment.builder()
                .pdi_inicial(payload.pdi_inicial())
                .pdi_final(payload.pdi_final())
                .acessivel(payload.acessivel())
                .aviso(payload.aviso())
                .distancia(distanciaSegmento)
                .build();

        var createdPDI = SegmentController.getInstance().createSegment(segment);
        return new AdminCreateSegmentResponse(createdPDI);

    }
}
