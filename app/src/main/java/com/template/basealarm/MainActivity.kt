package com.template.basealarm

import android.app.*
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.template.basealarm.databinding.ActivityMainBinding
import com.template.calenderproject.service.ServiceAutoLauncher
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var alarmYear = 0
    private var alarmMonth = 0
    private var alarmDay = 0
    private var alarmHour = 0
    private var alarmMinute = 0


    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVariables()


        binding.btnSubmit.setOnClickListener {
            setAlarms()

        }


        binding.btnDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]
            val datePickerDialog = DatePickerDialog(this,
                { view, year, month, dayOfMonth ->
                    val aCal = Calendar.getInstance()
                    aCal.timeZone = TimeZone.getDefault()
                    aCal[Calendar.YEAR] = year
                    aCal[Calendar.MONTH] = month
                    aCal[Calendar.DAY_OF_MONTH] = dayOfMonth
                    val simpleDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
                    val eventTime = simpleDateFormat.format(aCal.time)
                    alarmYear = year
                    alarmMonth = month
                    alarmDay = dayOfMonth
                    binding.txtDate.text = eventTime
                }, year, month, day
            )
            datePickerDialog.show()
        }

        binding.btnTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar[Calendar.HOUR_OF_DAY]
            val minute = calendar[Calendar.MINUTE]
            val timePickerDialog = TimePickerDialog(this,
                { view, hourOfDay, minute ->
                    val aCal = Calendar.getInstance()
                    aCal.timeZone = TimeZone.getDefault()
                    aCal[Calendar.HOUR_OF_DAY] = hourOfDay
                    aCal[Calendar.MINUTE] = minute
                    val simpleDateFormat = SimpleDateFormat("K:mm a", Locale.ENGLISH)
                    val eventTime = simpleDateFormat.format(aCal.time)
                    alarmHour = hourOfDay
                    alarmMinute = minute
                }, hour, minute, false
            )
            timePickerDialog.show()
        }
    }

    private fun setAlarms() {
        val calendar = Calendar.getInstance()
        calendar[alarmYear, alarmMonth] = alarmDay
        calendar[Calendar.HOUR_OF_DAY] = alarmHour
        calendar[Calendar.MINUTE] = alarmMinute
        calendar[Calendar.SECOND] = 0

        val intent = Intent(applicationContext, ServiceAutoLauncher::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(applicationContext, 0, intent, 0)
        val alarmManager = applicationContext.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
    private fun initVariables() {
        val mCal = Calendar.getInstance()
        mCal.timeZone = TimeZone.getDefault()
        alarmHour = mCal[Calendar.HOUR_OF_DAY]
        alarmMinute = mCal[Calendar.MINUTE]



        alarmYear = mCal[Calendar.YEAR]
        alarmMonth = mCal[Calendar.MONTH]
        alarmDay = mCal[Calendar.DAY_OF_MONTH]



         //for date
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
        val eventDate = dateFormat.format(mCal.time)
        binding.txtDate.text = eventDate



        //for date
        val timeFormat = SimpleDateFormat("K:mm a", Locale.ENGLISH)
        val eventTime = timeFormat.format(mCal.time)
        binding.txtTime.text = eventTime

    }




}