package com.example.facedetection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.facedetection.managers.DataManager
import com.example.facedetection.managers.FaceDetectorManager

class MainActivityViewModel : ViewModel() {

    fun getDetectionStatusLiveData(): MutableLiveData<Boolean> {
        return FaceDetectorManager.INSTANCE.isStartedDetectionLiveData
    }

    private fun geConvertingImagesLiveData(): MutableLiveData<Boolean> {
        return DataManager.INSTANCE.isConvertingImagesLiveData
    }

    fun needToShowNotification(): Boolean {
        return  isInDetectionProcess() || isInConvertingProcess()
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