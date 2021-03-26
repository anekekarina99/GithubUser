package com.android.dcdsubfunddua.ui.main.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.dcdsubfunddua.R
import com.android.dcdsubfunddua.alarm.AlarmReceiver
import com.android.dcdsubfunddua.databinding.ActivityAlarmBinding
import com.android.dcdsubfunddua.databinding.ActivityKontakBinding

class AlarmActivity : AppCompatActivity() {
    companion object {
        const val PREFS_NAME = "AlarmPref"
        private const val DAILY = "daily"
    }

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var mySharePref: SharedPreferences


    private lateinit var binding: ActivityAlarmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()
        setAlarm()
    }

    private fun setAlarm() {
        alarmReceiver = AlarmReceiver()
        mySharePref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        setSwitch()
    }

    private fun setSwitch() {
        binding.swDaily.isChecked = mySharePref.getBoolean(DAILY, false)
        binding.swDaily.setOnClickListener {
            if (binding.swDaily.isChecked) {
                alarmReceiver.setAlarm(
                    this,
                    AlarmReceiver.TYPE_DAILY,
                    "Liat yuk di github"
                )
            } else {
                alarmReceiver.delayAlarm(this)
            }

            saveAlarmEdit(DAILY,binding.swDaily.isChecked)

        }
    }

    private fun saveAlarmEdit(k: String,v: Boolean) {
        val edit = mySharePref.edit()
        edit.putBoolean(k, v)
        edit.apply()
    }

    private fun setActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.settings)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this, KontakActivity::class.java)
        startActivity(i)
        finish()
    }


}