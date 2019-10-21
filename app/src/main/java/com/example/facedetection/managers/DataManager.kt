package com.example.facedetection.managers

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.facedetection.network.NetworkManager
import com.example.facedetection.network.model.ImageResponse
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import java.util.*


class DataManager {
    private val TAG = DataManager::class.java.simpleName

    private lateinit var disposable: Disposable
    val isConvertingImagesLiveData = MutableLiveData<Boolean>()
    companion object {
        val INSTANCE = DataManager()
    }


    fun getImagesByPage(pageNum: Int): Single<ImageResponse> {
        return NetworkManager.INSTANCE.getImagesByPage(pageNum)
    }


    fun detectImages(imgUrlList: List<String>) {
        postConvertingImages()
        val bitmapList = arrayListOf<Bitmap>()
        disposable = imgUrlList.toObservable()
            .flatMap { i -> Observable.just(getImageAsBitmap(i)) }
            .subscribeOn(Schedulers.newThread())
            .subscribeBy(
                onComplete = {
                    startDetection(bitmapList)
                    disposable.dispose()
                },
                onError = { onFailed(it.message) },
                onNext = { bitmapList.add(it) }
            )
    }

    private fun startDetection(bitmapList: ArrayList<Bitmap>) {
        postFinishedConvertingImages()
        FaceDetectorManager.INSTANCE.startDetection(bitmapList)
    }


    private fun getImageAsBitmap(url: String): Bitmap {
        return NetworkManager.INSTANCE.getImageAsBitmap(url)
    }

    private fun onFailed(message: String?) {
        Log.e(TAG, message.toString())
    }

    private fun postFinishedConvertingImages() {
        Log.d(TAG, "FinishedConvertingImages")
        isConvertingImagesLiveData.postValue(false)

    }

    private fun postConvertingImages() {
        Log.d(TAG, "StartConvertingImages")
        isConvertingImagesLiveData.postValue(true)
    }

}