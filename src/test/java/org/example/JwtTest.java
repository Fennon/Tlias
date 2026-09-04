package org.example;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JwtTest {

    @Test
    public void test() {



        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("name","lin");
        claims.put("password",123456);

        String jwt= Jwts.builder().signWith(SignatureAlgorithm.HS256,"ZXhhbXBsZQ==")
                .addClaims(claims).setExpiration(new Date(System.currentTimeMillis()+3600*1000))
                .compact();

        System.out.println(jwt);

    }
}
