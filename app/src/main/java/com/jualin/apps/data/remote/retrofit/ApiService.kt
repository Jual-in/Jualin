package com.jualin.apps.data.remote.retrofit

import com.jualin.apps.data.remote.response.GeneralResponse
import com.jualin.apps.data.remote.response.auth.LoginResponse
import com.jualin.apps.data.remote.response.auth.RegisterResponse
import com.jualin.apps.data.remote.response.nearby.NearbyUmkmResponseItem
import com.jualin.apps.data.remote.response.product.AddProductResponse
import com.jualin.apps.data.remote.response.search.ProductResponse
import com.jualin.apps.data.remote.response.search.ServiceResponse
import com.jualin.apps.data.remote.response.service.AddServiceResponse
import com.jualin.apps.data.remote.response.umkm.AddUMKMResponse
import com.jualin.apps.data.remote.response.umkm.BusinessDetailResponse
import com.jualin.apps.data.remote.response.user.DetailUserResponse
import com.jualin.apps.data.remote.response.user.UpdateUserResponse
import com.jualin.apps.data.remote.response.user.UploadPhotoUserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
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
    @POST("api/umkm/create/users/{id_user}")
    suspend fun addUMKM(
        @Path("id_user") idUser: Int,
        @Field("Nama_usaha") namaUsaha: String,
        @Field("Kategori") kategori: String,
        @Field("No_hp") noHp: String,
        @Field("Deskripsi") deskripsi: String,
        @Field("latitude") latitude: Double?,
        @Field("longitude") longitude: Double?
    ): AddUMKMResponse

    @Multipart
    @POST("api/user/upload-photo/{id_user}")
    suspend fun uploadPhoto(
        @Path("id_user") idUser: Int,
        @Part photo: MultipartBody.Part
    ): UploadPhotoUserResponse

    @GET("api/umkm/{businessid}")
    suspend fun getBusinessById(
        @Path("businessid") id: Int
    ): BusinessDetailResponse

    @FormUrlEncoded
    @PUT("api/umkm/update/users/{id_user}")
    suspend fun editBusiness(
        @Path("id_user") idUser: Int,
        @Field("Nama_usaha") namaUsaha: String,
        @Field("Deskripsi") deskripsi: String,
        @Field("Kategori") kategori: String,
        @Field("No_hp") noHp: String,
        @Field("latitude") latitude: Double?,
        @Field("longitude") longitude: Double?
    ): GeneralResponse

    @GET("api/umkm/nearby")
    suspend fun getNearbyUMKM(
        @Query("latitude") latitude: Double?,
        @Query("longitude") longitude: Double?
    ): List<NearbyUmkmResponseItem>

    @GET("api/umkm/{businessid}/product")
    suspend fun getProductsByBusinessId(
        @Path("businessid") businessId: Int
    ): List<ProductResponse>

    @GET("api/umkm/{businessid}/service")
    suspend fun getServicesByBusinessId(
        @Path("businessid") businessId: Int
    ): List<ServiceResponse>

    @Multipart
    @POST("api/product/createProduct")
    suspend fun addProduct(
        @Part("id_umkm") businessId: Int,
        @Part("Nama") name: RequestBody,
        @Part("Harga") price: Int,
        @Part("Diskon") discount: Int,
        @Part photo: MultipartBody.Part
    ): AddProductResponse

    @FormUrlEncoded
    @POST("api/service/createService")
    suspend fun addService(
        @Field("id_umkm") businessId: Int,
        @Field("Nama") name: String,
        @Field("Harga") price: Int,
        @Field("Diskon") discount: Int
    ): AddServiceResponse

    @GET("api/service/{serviceId}")
    suspend fun getServiceById(
        @Path("serviceId") serviceId: Int
    ): ServiceResponse

    @FormUrlEncoded
    @PUT("api/service/updateService/{serviceId}")
    suspend fun editService(
        @Path("serviceId") serviceId: Int,
        @Field("Nama") name: String,
        @Field("Harga") price: Int,
        @Field("Diskon") discount: Int
    ): GeneralResponse

    @DELETE("api/service/deleteService/{serviceId}")
    suspend fun deleteService(
        @Path("serviceId") serviceId: Int
    ): GeneralResponse
}