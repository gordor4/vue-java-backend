package ru.rus.integrato.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TokenUtils {
    public static String issueToken(String login, String id, KeyGenerator keyGenerator, String contextPath) {
        Key key = keyGenerator.generateKey();

        return Jwts.builder()
                .setSubject(login)
                .setId(id)
                .setIssuer(contextPath)
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusDays(15L)))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
