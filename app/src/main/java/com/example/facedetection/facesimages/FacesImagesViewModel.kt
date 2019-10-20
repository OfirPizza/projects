package com.example.facedetection.facesimages

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.facedetection.managers.FaceDetectorManager

class FacesImagesViewModel : ViewModel() {


    fun getFacesImagesLiveData(): MutableLiveData<List<Bitmap>> {
        return FaceDetectorManager.INSTANCE.imagesWithFacesLiveData
    }


}