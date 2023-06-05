package com.jualin.apps.data.remote.retrofit

import com.jualin.apps.data.remote.response.DetailUserResponse
import com.jualin.apps.data.remote.response.LoginResponse
import com.jualin.apps.data.remote.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @GET("user/{userid}")
    suspend fun getDetailUser(
        @Path("userid") userid: Int
    ): DetailUserResponse

}