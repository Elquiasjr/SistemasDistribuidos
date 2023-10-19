package server.layer.InitialLayer;

import com.google.gson.JsonSyntaxException;
import helper.validation.ValidationHelper;
import server.exceptions.ServerResponseException;
import helper.json.JsonHelper;
import helper.validation.ConstraintViolated;
import server.exceptions.BadRequestException;
import server.exceptions.ServerResponseException;
import server.layer.interfaces.InitialLayer;

public abstract class StartTemplate implements InitialLayer {

    @Override
    public <T> T buildRequest(String jsonString, Class<T> clazz) throws ServerResponseException{
        try{
            var request = JsonHelper.fromJson(jsonString, clazz);
            ValidationHelper.validate(request);
            return request;
        } catch (JsonSyntaxException e){
            throw new BadRequestException("");
        } catch (ConstraintViolated e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
