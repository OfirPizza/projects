package com.example.facedetection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.facedetection.dialog.ResultsDialogFragment
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
        mainActivityViewModel.getDetectionStatusLiveData().observe(this, Observer { onDetectionStatusChanged(it)})
    }

    private fun onDetectionStatusChanged(isStartDetection: Boolean) {
        if (!isStartDetection){
            showDialog()
        }


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
