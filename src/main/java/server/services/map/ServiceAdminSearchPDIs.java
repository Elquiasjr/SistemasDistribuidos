package server.services.map;


import protocol.request.map.AdminSearchPDIsRequest;
import protocol.response.Response;
import protocol.response.map.AdminSearchPDIsResponse;
import server.controller.PDIController;
import server.dtobject.pdi.PDIDTO;
import server.exceptions.ServerResponseException;
import server.services.ServiceTemplate;
import server.validate.ValidateAdmin;
import server.validate.ValidateToken;

import java.util.List;

public class ServiceAdminSearchPDIs extends ServiceTemplate {
    @Override
    public Response<?> startService(String jsonString) throws ServerResponseException {
        AdminSearchPDIsRequest request = buildRequest(jsonString, AdminSearchPDIsRequest.class);

        ValidateAdmin.validate(request.getHeader().token());
        ValidateToken.validate(request.getHeader().token());

        var pdiController = PDIController.getInstance();
        List<PDIDTO> pdis = pdiController.findPDIs();


        return new AdminSearchPDIsResponse(pdis);
    }
}
