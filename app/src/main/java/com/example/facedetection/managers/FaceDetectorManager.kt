package com.example.facedetection.managers

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions


class FaceDetectorManager {

    private val TAG = FaceDetectorManager::class.java.simpleName
    val imagesWithFacesLiveData = MutableLiveData<List<Bitmap>>()
    val imagesWithoutFacesLiveData = MutableLiveData<List<Bitmap>>()
    val isStartedDetectionLiveData = MutableLiveData<Boolean>()

    companion object {
        val INSTANCE = FaceDetectorManager()
    }

    fun startDetection(bitmapList: ArrayList<Bitmap>) {
        val detector = getDetector(getDetectorOptions())
        val imgWithFace = arrayListOf<Bitmap>()
        val imgWithoutFace = arrayListOf<Bitmap>()
        var i = 0
        var isTaskDone = true
        postStartDetection()
        while (i < bitmapList.size - 1) {

            if (!isTaskDone) {
                continue
            }

            isTaskDone = false

            val detectFaceInImage = detectFaceInImage(bitmapList[i], detector)

            detectFaceInImage.addOnSuccessListener {
                if (imageHaveFace(it)) imgWithFace.add(bitmapList[i]) else imgWithoutFace.add(
                    bitmapList[i]
                )
                isTaskDone = true
                i++
            }

            detectFaceInImage.addOnFailureListener {
                imgWithoutFace.add(bitmapList[i])
                isTaskDone = true
                i++
            }
        }

        postResults(imgWithFace, imgWithoutFace)
        postFinishedDetection()

    }

    private fun postFinishedDetection() {
        Log.d(TAG, "FinishedDetection")
        isStartedDetectionLiveData.postValue(false)

    }

    private fun postStartDetection() {
        Log.d(TAG, "StartDetection")
        isStartedDetectionLiveData.postValue(true)
    }


    private fun postResults(imgWithFace: ArrayList<Bitmap>, imgWithoutFace: ArrayList<Bitmap>) {
        postImageWithFaceResults(imgWithFace)
        postImageWithOutFaceResults(imgWithoutFace)
    }

    private fun postImageWithOutFaceResults(imgWithoutFace: ArrayList<Bitmap>) {
        Log.d(TAG, "Detect ${imgWithoutFace.size} images without faces")
        imagesWithoutFacesLiveData.postValue(imgWithoutFace)
    }

    private fun postImageWithFaceResults(imgWithFace: ArrayList<Bitmap>) {
        Log.d(TAG, "Detect ${imgWithFace.size} images with faces")
        imagesWithFacesLiveData.postValue(imgWithFace)
    }

    private fun onDetectFailed(message: String?) {
        Log.d(TAG, message.toString())
    }

    private fun imageHaveFace(list: MutableList<FirebaseVisionFace>?): Boolean {
        return !list.isNullOrEmpty()
    }


    private fun detectFaceInImage(
        bitmap: Bitmap,
        detector: FirebaseVisionFaceDetector
    ): Task<MutableList<FirebaseVisionFace>> {
        val visionImage = FirebaseVisionImage.fromBitmap(bitmap)
        return detector.detectInImage(visionImage)
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