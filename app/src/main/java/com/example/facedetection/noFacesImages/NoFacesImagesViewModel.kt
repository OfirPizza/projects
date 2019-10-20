package com.example.facedetection.noFacesImages

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.facedetection.managers.FaceDetectorManager

class NoFacesImagesViewModel : ViewModel() {


    fun getImagesWithoutFacesLiveData(): MutableLiveData<List<Bitmap>> {
        return FaceDetectorManager.INSTANCE.imagesWithoutFacesLiveData
    }


}