package server.exceptions;

public class LastAdminException extends ServerResponseException{
    public LastAdminException() {
        super(403,"Ultimo adm não pode ser alterado.");
    }

}
