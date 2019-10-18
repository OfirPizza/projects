package com.example.facedetection.network

import com.example.facedetection.network.model.ImageResponse
import io.reactivex.Single

class NetworkManager {

    companion object {
        val INSTANCE = NetworkManager()
    }


    fun getAllImages(): Single<ImageResponse> {
        return RetrofitManager.INSTANCE.getNetworkApi().getAllImages()
    }


}