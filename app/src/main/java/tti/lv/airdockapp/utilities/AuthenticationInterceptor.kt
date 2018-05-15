package tti.lv.airdockapp.utilities

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(private val provider: SharedPreferenceProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = provider.getToken()
        if (token != null && !token.isEmpty()) {
            return chain.proceed(
                    chain.request().newBuilder()
                            .header("Authorization", token)
                            .build()
            )
        }
        else return chain.proceed(chain.request())
    }

}