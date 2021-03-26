package com.android.dcdsubfunddua.alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.android.dcdsubfunddua.R
import com.android.dcdsubfunddua.ui.main.view.KontakActivity
import java.util.*


class AlarmReceiver : BroadcastReceiver() {
   
   companion object{
       const val TYPE_DAILY = "Alarm Pengingat"
       const val EXTRA_MESSAGE = "message"
       const val EXTRA_TYPE ="type"

       private const val ID_DAILY = 90
       private const val TIME_DAILY = "09:00"
   }
    override fun onReceive(p0: Context, p1: Intent) {
        val message = p1.getStringExtra(EXTRA_MESSAGE)
        showAlarmNotification(p0,message)
    }

    fun setAlarm(context: Context, type:String, message: String){
        val alarmMng = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val i = Intent(context, AlarmReceiver::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.putExtra(EXTRA_MESSAGE, message)
        i.putExtra(EXTRA_TYPE, type)


        val time =
            TIME_DAILY.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]))
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ID_DAILY, i, PendingIntent.FLAG_ONE_SHOT
        )

        alarmMng.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Toast.makeText(context, "Pengingat sudah di set", Toast.LENGTH_SHORT).show()

    }
    private fun showAlarmNotification(p0: Context, message: String?) {
        val channelId = "android"
        val channelName = "Alarm notif"

        val intent = Intent(p0, KontakActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            p0, 0, intent, PendingIntent.FLAG_ONE_SHOT
        )

        val notifManagerCompat =
            p0.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(p0,channelId)
            .setSmallIcon(R.drawable.ic_baseline_favorite_24)
            .setContentTitle("Alarm Pengingat")
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            builder.setChannelId(channelId)
            notifManagerCompat.createNotificationChannel(channel)

        }
        val notification = builder.build()
        notifManagerCompat.notify(100, notification)
    }

    fun delayAlarm(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val i = Intent(context, AlarmReceiver::class.java)
        val reqCode = ID_DAILY
        val pendIntent = PendingIntent.getBroadcast(context,reqCode,i,0)
        pendIntent.cancel()
        alarmManager.cancel(pendIntent)
        Toast.makeText(context, "Alarm ditunda", Toast.LENGTH_SHORT).show()
    }
}