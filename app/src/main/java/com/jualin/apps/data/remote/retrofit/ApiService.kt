package com.jualin.apps.data.remote.retrofit

import com.jualin.apps.data.remote.response.DetailUserResponse
import com.jualin.apps.data.remote.response.LoginResponse
import com.jualin.apps.data.remote.response.ProductResponse
import com.jualin.apps.data.remote.response.ServiceResponse
import com.jualin.apps.data.remote.response.auth.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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
        @Field("password") password: String,
        @Field("role") role: String
    ): RegisterResponse

    @GET("user/{userid}")
    suspend fun getDetailUser(
        @Path("userid") userid: Int
    ): DetailUserResponse

    @GET("search/product")
    suspend fun searchProduct(
        @Query("type") query: String
    ): List<ProductResponse>

    @GET("search/service")
    suspend fun searchService(
        @Query("type") query: String
    ): List<ServiceResponse>
}