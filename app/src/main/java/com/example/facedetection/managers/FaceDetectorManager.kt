package com.example.facedetection.managers

import android.graphics.Bitmap
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions

class FaceDetectorManager {

    companion object {
        val INSTANCE = FaceDetectorManager()
    }

    fun startDetection(bitmapList: ArrayList<Bitmap>) {
        val detector = getDetector(getDetectorOptions())
        bitmapList.forEach {
            detectFaceInImage(detector, it)
        }

    }

    private fun detectFaceInImage(detector: FirebaseVisionFaceDetector, bitmap: Bitmap) {
        val visionImage = FirebaseVisionImage.fromBitmap(bitmap)


        detector.detectInImage(visionImage).addOnSuccessListener {
            if (it.size > 0) {
                //work in progress
            }
        }
    }

    private fun getDetector(detectorOptions: FirebaseVisionFaceDetectorOptions): FirebaseVisionFaceDetector {
        return FirebaseVision
            .getInstance()
            .getVisionFaceDetector(detectorOptions)
    }

    private fun getDetectorOptions(): FirebaseVisionFaceDetectorOptions {
        return FirebaseVisionFaceDetectorOptions.Builder()
            .setContourMode(
                FirebaseVisionFaceDetectorOptions.ALL_CONTOURS
            ).build()
    }
}