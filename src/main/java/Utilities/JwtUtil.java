package Utilities;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private final static String clientSecret = "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";
    static Algorithm algorithm = Algorithm.HMAC256(clientSecret);

    public static String generateToken(String userId) {

        String encodedUserId = "1234567890!@#$%^&*()"+Utils.encodeString(userId);

        return JWT.create()
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("userId",encodedUserId)
//                .withClaim("userId", userId)
//                .withClaim("userEmail",userEmail)
                .sign(algorithm);
    }

    public static DecodedJWT decodeToken(String tkn){
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(tkn);
    }
    public static boolean verifyToken(String tkn){
        DecodedJWT decodedTkn = decodeToken(tkn);
        System.out.println(decodedTkn.getClaim("userId").asString());
        return !decodedTkn.getClaim("userId").isMissing();
    }

    public static String getUserEmail(String tkn){
        DecodedJWT decodedJWT = decodeToken(tkn);
        return decodedJWT.getClaim("userEmail").asString();
    }

    public static String getPlayLists(HttpSession session){
        String jwtToken = (String) session.getAttribute("jwtToken");
        return decodeToken(jwtToken).getClaim("playLists").asString();
    }
    public static String getUserId(String tkn){
        DecodedJWT decodedJWT = decodeToken(tkn);
        String encodedUserId = decodedJWT.getClaim("userId").asString().substring(20);
        return Utils.decodeString(encodedUserId);
    }

    public static void setPlayLists(HttpSession session, Map<String, String> playListsMap) {

    }
}
