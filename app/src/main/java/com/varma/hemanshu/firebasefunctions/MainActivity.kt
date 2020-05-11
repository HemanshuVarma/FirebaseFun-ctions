package com.varma.hemanshu.firebasefunctions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.varma.hemanshu.firebasefunctions.ui.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance()).commitNow()
        }
    }
}
