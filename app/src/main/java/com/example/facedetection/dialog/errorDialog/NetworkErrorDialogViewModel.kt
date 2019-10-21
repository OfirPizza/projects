package com.example.facedetection.dialog.errorDialog

import androidx.lifecycle.ViewModel

class NetworkErrorDialogViewModel : ViewModel() {

    fun getText(): String {
        return "No Internet connection"
    }


}