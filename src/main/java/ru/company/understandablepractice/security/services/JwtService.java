package ru.company.understandablepractice.security.services;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import ru.company.understandablepractice.model.CustomerCredentials;
import ru.company.understandablepractice.model.UserCredentials;
import ru.company.understandablepractice.model.types.ApplicationFormStatus;
import ru.company.understandablepractice.model.types.ClientType;
import ru.company.understandablepractice.security.JwtType;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    public static final String BEARER_PREFIX = "Bearer ";
    @Value("${jwt.access.key}")
    private String accessKey;

    @Value("${jwt.access.expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh.key}")
    private String refreshKey;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    @Value("${jwt.person.expiration}")
    private long personExpiration;

    /**
     * Извлечение id пользователя из токена
     *
     * @param token токен
     * @return имя пользователя
     */
    public Long extractUserId(String token, JwtType type) {
        if (StringUtils.startsWith(token, BEARER_PREFIX)) {
            return Long.parseLong(extractClaim(token.substring(BEARER_PREFIX.length()), type, Claims::getId));
        }
        return Long.parseLong(extractClaim(token, type, Claims::getId));
    }

    /**
     * Проверка токена на валидность
     *
     * @param token       токен
     * @param userDetails данные пользователя
     * @return true, если токен валиден
     */
    public boolean isTokenValid(String token, JwtType type, UserCredentials userDetails) {
        final Long userId = extractUserId(token, type);

        return (userId.equals(userDetails.getUser().getId())) && !isTokenExpired(token, type);
    }

    public boolean isTokenValid(String token, JwtType type, CustomerCredentials userDetails) {
        final Long userId = extractUserId(token, type);

        return (userId.equals(userDetails.getCustomer().getId())) && !isTokenExpired(token, type);
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
        } else if (userDetails instanceof CustomerCredentials customerCredentials) {
            claims.put("jti", customerCredentials.getCustomer().getId());
        }

        if (type.equals(JwtType.ACCESS)) {
            return generateToken(claims, userDetails, accessKey, accessExpiration);
        } else return generateToken(claims, userDetails, refreshKey, refreshExpiration);
    }

    public String generatePersonToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof CustomerCredentials customerCredentials) {
            claims.put("jti", customerCredentials.getCustomer().getId());
        }
        return generateToken(claims, userDetails, accessKey, personExpiration);

    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, String key, long expiration) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(key), SignatureAlgorithm.HS256).compact();
    }

    private String generateToken(Map<String, Object> extraClaims, String key, long expiration) {
        return Jwts.builder().setClaims(extraClaims)
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
    public boolean isTokenExpired(String token, JwtType type) {
        return extractExpiration(token, type).before(new Date());
    }

    /**
     * Извлечение даты истечения токена
     *
     * @param token токен
     * @return дата истечения
     */
    private Date extractExpiration(String token, JwtType type) {
        return extractClaim(token, type, Claims::getExpiration);
    }

    /**
     * Извлечение всех данных из токена
     *
     * @param token токен
     * @return данные
     */
    private Claims extractAllClaims(String token, JwtType type) {
        String key = type.equals(JwtType.ACCESS) ? accessKey : refreshKey;
        return Jwts.parser()
                .setSigningKey(getSigningKey(key))
                .parseClaimsJws(token)
                .getBody();
    }


}
