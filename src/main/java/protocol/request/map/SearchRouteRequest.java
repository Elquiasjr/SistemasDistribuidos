package protocol.request.map;

import jakarta.validation.constraints.Positive;
import protocol.request.Request;
import protocol.request.RequisitionOperations;
import protocol.request.header.Header;

public class SearchRouteRequest extends Request<SearchRouteRequest.Payload> {
    public final SearchRouteRequest.Payload payload;

    public SearchRouteRequest(String token, Long pdi_inicial, Long pdi_final) {
        super(new Header(RequisitionOperations.BUSCAR_ROTA, token));
        payload = new SearchRouteRequest.Payload(pdi_inicial,pdi_final);
    }

    public record Payload(@Positive Long pdi_inicial,
                          @Positive Long pdi_final){}
}
