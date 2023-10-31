package server.validate;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jwt.JwtHelper;
import server.exceptions.ForbiddenAccessException;
import server.exceptions.ServerResponseException;

public class ValidateAdmin {
    public static void validate(String token) throws ServerResponseException {
        try {
            DecodedJWT jwt = JwtHelper.decode(token);
            Claim isAdmin = jwt.getClaim("isAdmin");
            Claim userId = jwt.getClaim("userId");
            if (!isAdmin.asBoolean()) {
                if (!userId.isMissing()) {
                    throw new ForbiddenAccessException(userId.asLong().toString());
                }
                throw new ForbiddenAccessException();
            }
        } catch (JWTVerificationException e) {
            throw new ForbiddenAccessException();
        }
    }
}
