package com.example.facedetection.network

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.*
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.facedetection.network.model.ImageResponse
import io.reactivex.Single
import java.lang.Exception
import java.net.URL

class NetworkManager : ConnectivityManager.NetworkCallback() {
    private val TAG = NetworkManager::class.java.simpleName


    val isNetworkAvailable = MutableLiveData<Boolean>()

    companion object {
        val INSTANCE = NetworkManager()
    }


    fun getImagesByPage(pageNum: Int): Single<ImageResponse> {
        return RetrofitManager.INSTANCE.getNetworkApi().getImagesByPage(page = pageNum)
    }


    fun getImageAsBitmap(imgUrl: String): Bitmap {
        try {
            val url = URL(imgUrl)
            val connection = url.openConnection()
            connection.connect()
            val input = connection.getInputStream()
            return BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
        return BitmapFactory.decodeStream(null)
    }

    fun enable(context: Context) {
        val networkRequest =
            NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build()
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(networkRequest, this)
        checkCurrentConnection(connectivityManager.activeNetworkInfo)
    }

    fun disable(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.unregisterNetworkCallback(this)
    }

    private fun checkCurrentConnection(networks: NetworkInfo?) {
        val isConnected = networks != null && networks.isConnected

        postNetworkStatus(isConnected)
    }


    override fun onAvailable(network: Network) {
        postNetworkStatus(true)
    }

    override fun onLost(network: Network) {
        postNetworkStatus(false)
    }

    private fun postNetworkStatus(haveInternet: Boolean) {
        isNetworkAvailable.postValue(haveInternet)

    }


}