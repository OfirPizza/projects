package com.example.facedetection.dialog

import androidx.lifecycle.ViewModel
import com.example.facedetection.managers.FaceDetectorManager

class ResultsDialogViewModel : ViewModel() {

    fun getText(): String {
        var sizeOfFacesFound = 0
        var totalSize = 0

        FaceDetectorManager.INSTANCE.imagesWithFacesLiveData.value?.let {
            sizeOfFacesFound = it.size
        }
        FaceDetectorManager.INSTANCE.imagesWithoutFacesLiveData.value?.let {
            totalSize = it.size + sizeOfFacesFound
        }

        return "Found ${sizeOfFacesFound} from ${totalSize}"
    }


}