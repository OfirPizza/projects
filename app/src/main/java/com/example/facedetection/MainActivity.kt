package com.example.facedetection

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.facedetection.dialog.ResultsDialogFragment
import com.example.facedetection.notification.ResultsService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initBottomNavigationController()
    }

    private fun initViewModel() {
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        setObservers()
    }

    private fun setObservers() {
        mainActivityViewModel.getDetectionStatusLiveData()
            .observe(this, Observer { onDetectionStatusChanged(it) })
    }

    override fun onResume() {
        super.onResume()
        removeNotification()
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
        if (mainActivityViewModel.needToShowNotification()) {
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
        ResultsDialogFragment().show(
            supportFragmentManager,
            ResultsDialogFragment::class.java.simpleName
        )
    }
}
