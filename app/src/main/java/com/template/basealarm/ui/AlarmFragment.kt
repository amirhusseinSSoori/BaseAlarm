package com.template.basealarm.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.template.basealarm.R
import com.template.basealarm.databinding.FragmentAlarmBinding
import com.template.basealarm.util.event.Message
import com.template.basealarm.util.event.MessageEvent
import com.template.calenderproject.service.ServiceAutoLauncher
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*

class AlarmFragment : Fragment(R.layout.fragment_alarm) {
    private var alarmYear = 0
    private var alarmMonth = 0
    private var alarmDay = 0
    private var alarmHour = 0
    private var alarmMinute = 0
    lateinit var binding: FragmentAlarmBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAlarmBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        if (!EventBus().isRegistered(this)) {
            EventBus().register(this)
        }

        initVariables()


        binding.btnSubmit.setOnClickListener {
            setAlarms()

        }


        binding.btnDate.setOnClickListener {
//            val calendar = Calendar.getInstance()
//            val year = calendar[Calendar.YEAR]
//            val month = calendar[Calendar.MONTH]
//            val day = calendar[Calendar.DAY_OF_MONTH]
//            val datePickerDialog = DatePickerDialog(requireContext(),
//                { view, year, month, dayOfMonth ->
//                    val aCal = Calendar.getInstance()
//                    aCal.timeZone = TimeZone.getDefault()
//                    aCal[Calendar.YEAR] = year
//                    aCal[Calendar.MONTH] = month
//                    aCal[Calendar.DAY_OF_MONTH] = dayOfMonth
//                    val simpleDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
//                    val eventTime = simpleDateFormat.format(aCal.time)
//                    alarmYear = year
//                    alarmMonth = month
//                    alarmDay = dayOfMonth
//                    binding.txtDate.text = eventTime
//                }, year, month, day
//            )
//            datePickerDialog.show()
            setUpBottomSheetPay()

        }

        binding.btnTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar[Calendar.HOUR_OF_DAY]
            val minute = calendar[Calendar.MINUTE]
            val timePickerDialog = TimePickerDialog(requireContext(),
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
           // EventBus.getDefault().post(MessageEvent("hello"));
            //findNavController().navigate(R.id.calFragment)
        }

    }

    private fun setAlarms() {
        val calendar = Calendar.getInstance()
        calendar[alarmYear, alarmMonth] = alarmDay
        calendar[Calendar.HOUR_OF_DAY] = alarmHour
        calendar[Calendar.MINUTE] = alarmMinute
        calendar[Calendar.SECOND] = 0

        val intent = Intent(requireContext(), ServiceAutoLauncher::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(requireContext(), 0, intent, 0)
        val alarmManager = requireContext().getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
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



    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }
    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(messageEvent: MessageEvent) {
        binding.txtDate.text = messageEvent.message
        Log.e("TAG", "onMessageEvent: ${messageEvent.message}", )
    }
    private fun setUpBottomSheetPay() {
        val dialogSheet = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val view = layoutInflater.inflate(R.layout.fragment_calender, null)
        var calender = view.findViewById<CalendarView>(R.id.main_Calender)
          calender.setOnDateChangeListener(CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
              EventBus.getDefault().post(MessageEvent("$dayOfMonth-$month-$year"));
              dialogSheet.dismiss()



          })
        dialogSheet.setContentView(view)
        dialogSheet.show()




    }
}