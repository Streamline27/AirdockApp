package tti.lv.airdockapp.utilities

import android.util.Base64
import javax.inject.Inject
import io.jsonwebtoken.Jwts



class JwtHelper @Inject constructor() {

    fun extractUserId(token : String?) : String {
        if (token != null) {
            return Jwts.parser()
                    .setSigningKey(Base64.encodeToString("SecretKeyToGenJWTs".toByteArray(), Base64.DEFAULT))
                    .parseClaimsJws(token)
                    .getBody()
                    .get("userId", Integer::class.java)
                    .toString();
        }
        else return ""
    }

}