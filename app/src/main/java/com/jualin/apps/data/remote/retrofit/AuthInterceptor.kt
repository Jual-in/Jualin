package com.jualin.apps.data.remote.retrofit

import com.jualin.apps.data.local.preferences.UserPreferences
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val pref: UserPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            pref.getToken()
        }
        val req = chain.request().newBuilder().header("Authorization", token)
        return chain.proceed(req.build())
    }
}