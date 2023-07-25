package Utilities;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static String encodeString(String userId){

        return  Base64.getEncoder().encodeToString(userId.getBytes());
    }

    public static String decodeString(String userId){

        byte[] decode = Base64.getDecoder().decode(userId);

        StringBuilder stringBuilder = new StringBuilder();
        for(byte b : decode) stringBuilder.append(Character.toChars(b));

        return stringBuilder.toString();
    }


    public static String getRequestString(HttpServletRequest request) throws IOException {

        StringBuilder jb = new StringBuilder();

        BufferedReader reader = request.getReader();

        String line;
        while((line = reader.readLine()) != null) {
            jb.append(line);
        }
        reader.close();

        return jb.toString();
    }

    public static String getEmail(HttpServletRequest req) {
        return JwtUtil.getUserEmail((req.getHeader("Authorization")).split(" ")[1]);
    }

    public static String getUserId(HttpSession session){
        return JwtUtil.getUserId((String)session.getAttribute("jwtToken"));

    }
    public static String getUserId(HttpServletRequest req) {
        return JwtUtil.getUserId((req.getHeader("Authorization")).split(" ")[1]);
    }


    public static String passwordEncrypt(String password)  {

        final byte[] passwordBytes = password.getBytes();
        StringBuffer encryptedPassword = new StringBuffer();
        try {
            MessageDigest MD = MessageDigest.getInstance("MD5");
            MD.update(passwordBytes);
            final byte[] messageDigest = MD.digest();

            for(byte m : messageDigest){
                String hex = Integer.toHexString(0xFF & m);
                if(hex.length()==1) encryptedPassword.append("0");
                encryptedPassword.append(m);
            }
        }catch (NoSuchAlgorithmException e){
            System.out.println(e.getMessage());
        }

        return encryptedPassword.toString();
    }

    public static Map<String, String> stringToMap(String s){
        Map<String, String> myMap = new HashMap<String, String>();

        String[] pairs = s.split(", ");
        for (int i=0;i<pairs.length;i++) {
            String pair = pairs[i];
            String[] keyValue = pair.split("=");
            myMap.put(keyValue[0], keyValue[1]);
        }
        return myMap;
    }
}
