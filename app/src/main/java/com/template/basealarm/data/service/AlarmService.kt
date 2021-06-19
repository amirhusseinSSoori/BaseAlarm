package com.template.basealarm.data.service

import android.app.*
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.template.basealarm.MainActivity
import com.template.basealarm.R
import com.template.basealarm.data.db.dao.AlarmDao
import com.template.basealarm.data.db.entity.AlarmEntity
import com.template.basealarm.showNotificationWithFullScreenIntent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AlarmService : Service() {

    private val TAG = this.javaClass.simpleName

    private var bundle: Bundle? = null
    private var alarmId: Int? = null
    private var timeAlarm: String? = null
    private var dateAlarm: String? = null
    private var eventNote: String? = "null"
    private var eventColor: Int? = null
    private var interval: String? = null
    private var eventTimeStamp: String? = null
    private var notificationId: Int? = 0
    private var soundName: String? = null

    @Inject
    lateinit var db: AlarmDao

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")
        if (intent != null) {
            bundle = intent.extras
            alarmId = bundle!!.getInt("alarmId", 100)
            timeAlarm = bundle!!.getString("time", "00:00")
            dateAlarm = bundle!!.getString("date", "00/00/00")
            CoroutineScope(context = Dispatchers.IO).launch {
                db.update(AlarmEntity(timeAlarm, dateAlarm, false, true, alarmId))
            }

            showNotification()

            Log.e("Show", "onStartCommand: ")

        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun showNotification() {
        // Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        val ringtoneUri = Uri.parse(
            "android.resource://$packageName/" + getSoundResourceId(
                "Juntos"!!
            )
        )
        val r = RingtoneManager.getRingtone(this, ringtoneUri)
        r.play()
        val vibrate = true
        val vibratePattern = longArrayOf(0L, 1000L)
        val notificationBuilder = NotificationCompat.Builder(this, "0")
        val mNotifyManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        grantUriPermission(
            "com.android.systemui",
            ringtoneUri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        val activityIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            notificationId!!, activityIntent, PendingIntent.FLAG_ONE_SHOT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val att = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            val mChannel =
                NotificationChannel("0", "Event Notification", NotificationManager.IMPORTANCE_HIGH)
            mChannel.description = "eventNote"
            mChannel.enableLights(true)
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = vibratePattern
            //  mChannel.lightColor = eventColor!!
            mChannel.setSound(ringtoneUri, att)
            mChannel.setBypassDnd(true)
            mChannel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            mChannel.setShowBadge(true)
            mNotifyManager?.createNotificationChannel(mChannel)
            notificationBuilder
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setVibrate(vibratePattern)
                .setSound(ringtoneUri)
                //.setColor(eventColor!!)
                // .setContentTitle(eventTitle)
                .setStyle(NotificationCompat.BigTextStyle().bigText("eventNote"))
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)
        } else {
            notificationBuilder.setContentTitle("eventTitle")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_VIBRATE or Notification.DEFAULT_SOUND)
                .setVibrate(vibratePattern)
                .setSound(ringtoneUri)
                .setStyle(NotificationCompat.BigTextStyle().bigText(eventNote))
                .setColor(eventColor!!)
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)
        }
        notificationBuilder.setContentText(eventNote)
        mNotifyManager?.notify(notificationId!!, notificationBuilder.build())
        Log.d(TAG, "showNotification: End")
    }

    private fun setNewAlarm() {
        val intent = Intent(this, ServiceAutoLauncher::class.java)
        intent.putExtras(bundle!!)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val triggerAtMillis = getNextEventTriggerMillis()
        if (triggerAtMillis != 0L) {
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            Log.d(
                "APP_TEST",
                "repeatingAlarm: Alarm at " + java.lang.Long.toString(triggerAtMillis)
            )
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
        }
    }

    private fun getNextEventTriggerMillis(): Long {
        val calendar = Calendar.getInstance()
        if (interval == getString(R.string.daily)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        } else if (interval == getString(R.string.weekly)) {
            calendar.add(Calendar.DAY_OF_MONTH, 7)
        } else if (interval == getString(R.string.monthly)) {
            return getNextMonthMillis()
        } else if (interval == getString(R.string.yearly)) {
            calendar.add(Calendar.YEAR, 1)
        } else {
            return 0
        }
        return calendar.timeInMillis
    }

    private fun getNextMonthMillis(): Long {
        val cal = Calendar.getInstance()
        var currentMonth = cal[Calendar.MONTH]
        currentMonth++
        if (currentMonth > Calendar.DECEMBER) {
            currentMonth = Calendar.JANUARY
            cal[Calendar.YEAR] = cal[Calendar.YEAR] + 1
        }
        cal[Calendar.MONTH] = currentMonth
        val maximumDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        cal[Calendar.DAY_OF_MONTH] = maximumDay
        return cal.timeInMillis
    }

    private fun getSoundResourceId(soundName: String): Int {
        when (soundName) {
            "consequence" -> return R.raw.consequence
            "Juntos" -> return R.raw.juntos
            "Piece of cake" -> return R.raw.piece_of_cake
            "Point blank" -> return R.raw.point_blank
            "Slow spring board" -> return R.raw.slow_spring_board
        }
        return R.raw.consequence
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}