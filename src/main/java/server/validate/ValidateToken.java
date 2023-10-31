package server.validate;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jwt.JwtHelper;
import server.exceptions.ServerResponseException;
import server.exceptions.UnauthorizedAccessException;

public class ValidateToken{
    public static void validate(String token) throws ServerResponseException {
        if (token == null) {
            throw new UnauthorizedAccessException();
        }
        DecodedJWT jwt;
        try {
            // checando a validade do token
            jwt = JwtHelper.verify(token);
        } catch (JWTVerificationException ex) {
            throw new UnauthorizedAccessException();
        }

        // checando se o token possui os campos obrigatórios
        Claim userId = jwt.getClaim("userId");
        Claim isAdmin = jwt.getClaim("isAdmin");
        if (userId.isMissing() || userId.isNull() || isAdmin.isMissing() || isAdmin.isNull()) {
            throw new UnauthorizedAccessException();
        }
    }
}
