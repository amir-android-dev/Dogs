package com.amir.dogs.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.amir.dogs.R

class MainActivity : AppCompatActivity() {
    private lateinit var navCotroller : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navCotroller = Navigation.findNavController(this,R.id.fragmentContainerView)
        NavigationUI.setupActionBarWithNavController(this,navCotroller)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navCotroller,null)
    }
}