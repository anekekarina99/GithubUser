package com.android.dcdsubfunddua

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.android.dcdsubfunddua.SplashScreen.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*Splashscreen and viewPager*/

        val splashScreen = SectionPagerAdapter(this,supportFragmentManager)
        val viewPager : ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = splashScreen

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        supportActionBar?.elevation  = 0f

    /* End Splashscreen and viewPager*/

    }
}