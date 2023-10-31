package server.services;

import protocol.response.Response;
import server.exceptions.ServerResponseException;

public interface ServiceInterface {
    <T> T buildRequest(String jsonString, Class<T> classType)throws ServerResponseException;

    Response<?> startService(String jsonString) throws ServerResponseException;
}
