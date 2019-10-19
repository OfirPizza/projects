package com.example.facedetection.allImages

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.facedetection.allImages.model.ImageUiModel
import com.example.facedetection.managers.DataManager
import com.example.facedetection.network.model.ImageResponse
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class AllImagesViewModel : ViewModel() {

    private val TAG = AllImagesViewModel::class.java.simpleName
    val imageListLiveData = MutableLiveData<List<ImageUiModel>>()

    private lateinit var disposable: Disposable


    fun getAllImages() {
        disposable = DataManager.INSTANCE.getAllImages()
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onError = { postImageListFailed(it) },
                onSuccess = { postImageList(it) }
            )

    }

    private fun postImageList(response: ImageResponse) {
        imageListLiveData.postValue(response.photos.map { ImageUiModel(it.src.imgUrl) })
    }

    private fun postImageListFailed(tr: Throwable) {
        Log.e(TAG, tr.message)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    fun startDetection() {
        imageListLiveData.value?.let {
            val list = it.map { it.imageUrl }
            DataManager.INSTANCE.detectImages(list)
        }
    }


}