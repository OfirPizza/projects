package com.example.facedetection

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.facedetection.dialog.errorDialog.NetworkErrorDialogFragment
import com.example.facedetection.dialog.resultDialog.ResultsDialogFragment
import com.example.facedetection.network.NetworkManager
import com.example.facedetection.notification.ResultsService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initBottomNavigationController()
        initNetworkManager()
    }

    private fun initNetworkManager() {
        NetworkManager.INSTANCE.enable(this)
    }

    private fun initViewModel() {
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        setObservers()
    }

    private fun setObservers() {
        mainActivityViewModel.getDetectionStatusLiveData()
            .observe(this, Observer { onDetectionStatusChanged(it) })

        mainActivityViewModel.getNetworkStatusLiveData()
            .observe(this, Observer { onNetworkStateChange(it) })
    }

    private fun onNetworkStateChange(isNetworkAvailable: Boolean) {
        if (isNetworkAvailable) {
            supportFragmentManager
                .findFragmentByTag(NetworkErrorDialogFragment::class.java.simpleName)
                ?.let {
                    (it as DialogFragment).dismiss()
                }
            return
        }

        showNetworkError()
    }

    override fun onResume() {
        super.onResume()
        removeNotification()
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkManager.INSTANCE.disable(this)
    }

    private fun removeNotification() {
        val intent = Intent(this, ResultsService::class.java)
        stopService(intent)
    }

    private fun onDetectionStatusChanged(isStartDetection: Boolean) {
        if (!isStartDetection) {
            removeNotification()
            showDialog()
        }
    }


    override fun onPause() {
        super.onPause()
        showNotificationIfNeeded()
    }

    private fun showNotificationIfNeeded() {
        if (mainActivityViewModel.needToShowNotification() && mainActivityViewModel.haveInternetConnection()) {
            showNotification()
        }
    }

    private fun showNotification() {

        val intent = Intent(this, ResultsService::class.java)
        ContextCompat.startForegroundService(this, intent)
    }


    private fun initBottomNavigationController() {
        this.bottom_navigation_view.setOnNavigationItemSelectedListener { navigate(it.itemId) }
    }


    private fun navigate(id: Int): Boolean {
        findNavController(R.id.home_nav_host_fragment).run {
            if (currentDestination?.id == id) return false
            navigate(id)
        }
        return true
    }


    private fun showDialog() {
        if (!mainActivityViewModel.haveInternetConnection()) return

        ResultsDialogFragment().show(
            supportFragmentManager,
            ResultsDialogFragment::class.java.simpleName
        )
    }


    private fun showNetworkError() {
        removeNotification()

        NetworkErrorDialogFragment().show(
            supportFragmentManager,
            NetworkErrorDialogFragment()::class.java.simpleName
        )

    }


}
