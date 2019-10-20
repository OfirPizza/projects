package com.example.facedetection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.facedetection.managers.FaceDetectorManager

class MainActivityViewModel : ViewModel() {

    fun getDetectionStatusLiveData(): MutableLiveData<Boolean> {
        return FaceDetectorManager.INSTANCE.isStartedDetectionLiveData
    }


}