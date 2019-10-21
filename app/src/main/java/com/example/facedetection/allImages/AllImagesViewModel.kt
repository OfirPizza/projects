package com.example.facedetection.allImages

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.facedetection.allImages.model.ImageUiModel
import com.example.facedetection.managers.DataManager
import com.example.facedetection.managers.FaceDetectorManager
import com.example.facedetection.network.model.ImageResponse
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class AllImagesViewModel : ViewModel() {

    private val TAG = AllImagesViewModel::class.java.simpleName
    val imageListLiveData = MutableLiveData<List<ImageUiModel>>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    private lateinit var disposable: Disposable


    fun getImagesByPage(pageNum: Int) {
        postLoading(true)
        disposable = DataManager.INSTANCE.getImagesByPage(pageNum)
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onError = { postImageListFailed(it) },
                onSuccess = { postImageList(it) }
            )

    }

    private fun postLoading(isLoading: Boolean) {
        isLoadingLiveData.postValue(isLoading)
    }

    private fun postImageList(response: ImageResponse) {
        postLoading(false)
        imageListLiveData.postValue(response.photos.map { ImageUiModel(it.src.imgUrl) })
    }

    private fun postImageListFailed(tr: Throwable) {
        postLoading(true)
        Log.e(TAG, tr.message.toString())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    fun startDetection(data: ArrayList<ImageUiModel>) {
        DataManager.INSTANCE.detectImages(data.map { it.imageUrl })
    }

    fun getIsStartedDetectionLiveData(): MutableLiveData<Boolean> {
        return FaceDetectorManager.INSTANCE.isStartedDetectionLiveData
    }


}