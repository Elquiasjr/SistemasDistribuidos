package server.services;

import com.google.gson.JsonSyntaxException;
import helper.validation.ValidationHelper;
import server.exceptions.ServerResponseException;
import helper.json.JsonHelper;
import helper.validation.ConstraintViolated;
import server.exceptions.BadRequestException;

public abstract class ServiceTemplate implements ServiceInterface{

    public <T> T buildRequest(String jsonString, Class<T> classType) throws ServerResponseException{
        try{
            var request = JsonHelper.fromJson(jsonString, classType);
            ValidationHelper.validate(request);
            return request;
        } catch (JsonSyntaxException e){
            throw new BadRequestException("");
        } catch (ConstraintViolated e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
