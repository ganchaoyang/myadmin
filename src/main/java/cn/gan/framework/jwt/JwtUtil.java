package cn.gan.framework.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * @author ganchaoyang
 * @date 2018/10/11 10:25
 * @description
 */
public class JwtUtil {

    /**
     * Token过期时间，30分钟
     */
    private static final long EXPIRE_TIME = 1800000;


    /**
     * 生成JWT Token
     * @param username
     * @param secret
     * @return
     */
    public static String genJwtToken(String username, String secret) {
        // 计算Token的失效时间
        Date deadTime = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        System.out.println(deadTime);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 生成Token
        return JWT.create().withClaim("username", username)
                .withExpiresAt(deadTime)
                .sign(algorithm);
    }


    /**
     * 验证token是否正确
     * @param token
     *          token
     * @param username
     *          用户名
     * @param secret
     *          用户密码
     * @return true表示token正确，否则表示错误
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

}
