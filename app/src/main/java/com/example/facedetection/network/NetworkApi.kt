package com.example.facedetection.network

import com.example.facedetection.network.model.ImageResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetworkApi {

    companion object {
        const val BASE_URL = "https://api.pexels.com/v1/"
        const val API_KEY = "Authorization:563492ad6f917000010000019dd1666b70bd471ebee1a94f68c6fc9e"
        const val CONTENT_TYPE = "Content-Type:application/json"
    }

    @Headers(CONTENT_TYPE, API_KEY)
    @GET("search")
    fun getImagesByPage(
        @Query("query") query: String? = "football",
        @Query("per_page") perPage: String? = "50",
        @Query("page") page: Int? = 1
    ): Single<ImageResponse>


}