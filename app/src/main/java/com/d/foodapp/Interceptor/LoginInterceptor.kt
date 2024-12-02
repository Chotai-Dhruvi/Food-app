package com.d.foodapp.Interceptor

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class LoginInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //If requested endpoint matched to targeted endpoint, we will return an mocked response.
        if (chain.request().url.toUri().toString().contains("lookup.php")) {
            val responseString = """message: Error 400- Bad Request"""
            return chain.proceed(chain.request())
                .newBuilder()
                .code(400)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    responseString
                        .toByteArray()
                        .toResponseBody(
                            "application/json".toMediaTypeOrNull()
                        )
                )
                .addHeader("content-type", "application/json")
                .build()
        } else {
            //Skip the interception.
            return chain.proceed(chain.request())
        }
    }
}

