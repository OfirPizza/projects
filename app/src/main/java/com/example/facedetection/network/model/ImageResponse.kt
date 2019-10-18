package com.example.facedetection.network.model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class ImageResponse(
    @SerializedName("photos")
    val photos: ArrayList<PhotosItemResponse>
)


data class PhotosItemResponse(
    @SerializedName("src")
    val src: ImageItemResponse
)

data class ImageItemResponse(
    @SerializedName("medium")
    val imgUrl: String
)