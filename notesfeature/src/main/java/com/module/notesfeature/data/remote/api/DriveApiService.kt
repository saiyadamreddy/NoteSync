package com.module.notesfeature.data.remote.api


import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface DriveApiService {

    @Multipart
    @POST("files?uploadType=multipart")
    suspend fun uploadFile(
        @Part("metadata") metadata: RequestBody,
        @Part file: MultipartBody.Part
    ): Map<String, Any>

    @GET("files")
    suspend fun listFiles(
        @Query("q") query: String? = null,
        @Query("spaces") spaces: String = "drive"
    ): Map<String, Any>
}