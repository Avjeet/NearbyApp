package com.express.nearbyappassgn.data.interceptor

import com.express.nearbyappassgn.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

object RetrofitInterceptor {
    fun apiKeyAsQuery(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder()
                .url(
                    chain.request().url
                        .newBuilder()
                        .addQueryParameter("client_id", BuildConfig.NEARBY_CLIENT_ID)
                        .build()
                ).build()
        )
    }
}