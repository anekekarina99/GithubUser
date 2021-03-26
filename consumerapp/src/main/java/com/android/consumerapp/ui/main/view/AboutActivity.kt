package com.android.consumerapp.ui.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.consumerapp.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val actionbar = supportActionBar
        actionbar!!.title = "About"
        actionbar.setDisplayHomeAsUpEnabled(true)


    }

    //kembali
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}
