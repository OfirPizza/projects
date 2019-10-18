package com.example.facedetection

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemReselectedListener {
    override fun onNavigationItemReselected(item: MenuItem) {
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNavigationController()
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
}
