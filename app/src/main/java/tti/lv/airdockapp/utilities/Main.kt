package tti.lv.airdockapp.utilities

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*

fun main(args: Array<String>) {
    val token = Jwts.builder()
            .setSubject("user")
            .setExpiration(Date(System.currentTimeMillis() + 1232312312312))
            .signWith(SignatureAlgorithm.HS512, "SecretKeyToGenJWTs")
            .claim("userId", "123")
            .compact()

    println(Jwts.parser()
            .setSigningKey("SecretKeyToGenJWTs")
            .parseClaimsJws(token)
            .getBody()
            .get("userId", String::class.java))
}