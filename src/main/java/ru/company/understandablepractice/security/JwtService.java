package ru.company.understandablepractice.security;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.UserCredentials;
import ru.company.understandablepractice.service.UserCredentialsService;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.access.key}")
    private String accessKey;

    @Value("${jwt.access.expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh.key}")
    private String refreshKey;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;


    /**
     * Извлечение имени пользователя из токена
     *
     * @param token токен
     * @return имя пользователя
     */
    public String extractUserName(String token, JwtType type) {
        return extractClaim(token,type, Claims::getId);
    }


    /**
     * Проверка токена на валидность
     *
     * @param token       токен
     * @param userDetails данные пользователя
     * @return true, если токен валиден
     */
    public boolean isTokenValid(String token,JwtType type, UserCredentials userDetails) {
        final Long userId = Long.parseLong(extractUserName(token, type));

        return (userId.equals(userDetails.getUser().getId())) && !isTokenExpired(token, type);
    }

    /**
     * Извлечение данных из токена
     *
     * @param token           токен
     * @param claimsResolvers функция извлечения данных
     * @param <T>             тип данных
     * @return данные
     */
    private <T> T extractClaim(String token, JwtType type, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token, type);
        return claimsResolvers.apply(claims);
    }

    /**
     * Генерация токена
     *
     * @param userDetails данные пользователя
     * @return токен
     */
    public String generateToken(UserDetails userDetails, JwtType type) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof UserCredentials customUserDetails) {
            claims.put("jti", customUserDetails.getUser().getId());
        }
        if (type.equals(JwtType.ACCESS)) {
            return generateToken(claims, userDetails, accessKey, accessExpiration);
        } else return generateToken(claims, userDetails, refreshKey, refreshExpiration);

    }


    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, String key, long expiration) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(key), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Получение ключа для подписи токена
     *
     * @return ключ
     */
    private Key getSigningKey(String key) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Проверка токена на просроченность
     *
     * @param token токен
     * @return true, если токен просрочен
     */
    private boolean isTokenExpired(String token, JwtType type) {
        return extractExpiration(token, type).before(new Date());
    }

    /**
     * Извлечение даты истечения токена
     *
     * @param token токен
     * @return дата истечения
     */
    private Date extractExpiration(String token, JwtType type) {
        return extractClaim(token,type, Claims::getExpiration);
    }

    /**
     * Извлечение всех данных из токена
     *
     * @param token токен
     * @return данные
     */
    private Claims extractAllClaims(String token, JwtType type) {
        String key = type.equals(JwtType.ACCESS)? accessKey: refreshKey;
        return Jwts.parser().setSigningKey(getSigningKey(key)).parseClaimsJws(token)
                .getBody();
    }


}
