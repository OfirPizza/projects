package com.example.facedetection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.facedetection.managers.DataManager
import com.example.facedetection.managers.FaceDetectorManager
import com.example.facedetection.network.NetworkManager

class MainActivityViewModel : ViewModel() {

    fun getDetectionStatusLiveData(): MutableLiveData<Boolean> {
        return FaceDetectorManager.INSTANCE.isStartedDetectionLiveData
    }

    fun getNetworkStatusLiveData(): MutableLiveData<Boolean> {
        return NetworkManager.INSTANCE.isNetworkAvailable
    }

    private fun geConvertingImagesLiveData(): MutableLiveData<Boolean> {
        return DataManager.INSTANCE.isConvertingImagesLiveData
    }

    fun needToShowNotification(): Boolean {
        return isInDetectionProcess() || isInConvertingProcess()
    }


    fun haveInternetConnection(): Boolean {
        NetworkManager.INSTANCE.isNetworkAvailable.value?.let { return it }
        return false
    }


    private fun isInDetectionProcess(): Boolean {
        getDetectionStatusLiveData().value?.let { return it }
        return false
    }


    private fun isInConvertingProcess(): Boolean {
        geConvertingImagesLiveData().value?.let { return it }
        return false
    }


}