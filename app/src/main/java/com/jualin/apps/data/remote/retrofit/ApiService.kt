package com.jualin.apps.data.remote.retrofit

import com.jualin.apps.data.remote.response.auth.LoginResponse
import com.jualin.apps.data.remote.response.auth.RegisterResponse
import com.jualin.apps.data.remote.response.search.ProductResponse
import com.jualin.apps.data.remote.response.search.ServiceResponse
import com.jualin.apps.data.remote.response.umkm.AddUMKMResponse
import com.jualin.apps.data.remote.response.user.DetailUserResponse
import com.jualin.apps.data.remote.response.user.UpdateUserResponse
import com.jualin.apps.data.remote.response.user.UploadPhotoUserResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("api/user/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("api/user/register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("role") role: String
    ): RegisterResponse

    @GET("api/user/{userid}")
    suspend fun getDetailUser(
        @Path("userid") userid: Int
    ): DetailUserResponse

    @GET("api/search/product")
    suspend fun searchProduct(
        @Query("type") query: String
    ): List<ProductResponse>

    @GET("api/search/service")
    suspend fun searchService(
        @Query("type") query: String
    ): List<ServiceResponse>

    @FormUrlEncoded
    @PUT("api/user/update/{userid}")
    suspend fun updateUser(
        @Path("userid") userid: Int,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("alamat") alamat: String
    ): UpdateUserResponse

    @Multipart
    @POST("predict")
    suspend fun predictImage(
        @Part image: MultipartBody.Part
    ): String
  
    @FormUrlEncoded
    @POST("umkm/create/users/{id_user}")
    suspend fun addUMKM(
        @Header("Authorization") auth: String,
        @Path("id_user") idUser: Int,
        @Field("Nama_usaha") namaUsaha: String,
        @Field("Kategori") kategori: String,
        @Field("No_hp") noHp: String,
        @Field("Deskripsi") deskripsi: String,
        @Field("latitude") latitude: Double,
        @Field("longitude") longitude: Double
    ): AddUMKMResponse

    @Multipart
    @POST("user/upload-photo/{id_user}")
    suspend fun uploadPhoto(
        @Header("Authorization") auth: String,
        @Path("id_user") idUser: Int,
        @Part photo: MultipartBody.Part
    ): UploadPhotoUserResponse

}