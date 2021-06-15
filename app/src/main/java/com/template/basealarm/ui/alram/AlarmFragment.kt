package com.template.basealarm.ui.alram

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.michaldrabik.classicmaterialtimepicker.CmtpTimeDialogFragment
import com.michaldrabik.classicmaterialtimepicker.utilities.setOnTime24PickedListener
import com.template.basealarm.R
import com.template.basealarm.databinding.FragmentAlarmBinding
import com.template.calenderproject.service.ServiceAutoLauncher
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AlarmFragment : Fragment(R.layout.fragment_alarm) {
    private var alarmYear = 0
    private var alarmMonth = 0
    private var alarmDay = 0
    private var alarmHour = 0
    private var alarmMinute = 0
    lateinit var id: ArrayList<Int>
    lateinit var binding: FragmentAlarmBinding
    private var picker: PersianDatePickerDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAlarmBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        id = ArrayList()
        Log.e("GGG", "onViewCreated: ${id.size}", )



        initVariables()


        binding.btnSubmit.setOnClickListener {
            setAlarms()

        }
        binding.btnDate.setOnClickListener {
            picker = PersianDatePickerDialog(requireContext())
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setMinYear(1300)
                .setAllButtonsTextSize(12)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setInitDate(alarmYear, alarmMonth, alarmDay)
                .setActionTextColor(Color.GRAY)
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setShowInBottomSheet(true)
                .setListener(object : PersianPickerListener {
                    override fun onDateSelected(persianPickerDate: PersianPickerDate) {


                        alarmYear = persianPickerDate.gregorianYear
                        alarmMonth = persianPickerDate.gregorianMonth - 1
                        alarmDay = persianPickerDate.gregorianDay



                        Log.e(
                            "TAG",
                            "onDateSelected: ${persianPickerDate.gregorianDay}  -   ${persianPickerDate.gregorianMonth}  -  ${persianPickerDate.gregorianYear}",
                        )

                        Toast.makeText(
                            requireContext(),
                            persianPickerDate.persianYear.toString() + "/" + persianPickerDate.persianMonth + "/" + persianPickerDate.persianDay,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onDismissed() {
                        Toast.makeText(requireContext(), "Dismissed", Toast.LENGTH_SHORT).show()
                    }
                })

            picker!!.show()
        }

        binding.btnTime.setOnClickListener {
            val dialog = CmtpTimeDialogFragment.newInstance()
            dialog.setInitialTime24(23, 30)
            dialog.setOnTime24PickedListener {
                alarmHour = it.hour
                alarmMinute = it.minute
                binding.txtTime.text = "${it.hour} - ${it.minute} "
                Log.e("TAG", "onViewCreated: ${it.hour} - ${it.minute} ")
            }
            dialog.show(requireActivity().supportFragmentManager, "TimePicker")


        }

    }

    private fun setAlarms() {
        Log.e("TAG", "setAlarms:${alarmMonth} ")
        val calendar = Calendar.getInstance()
        calendar[alarmYear, alarmMonth] = alarmDay
        calendar[Calendar.HOUR_OF_DAY] = alarmHour
        calendar[Calendar.MINUTE] = alarmMinute
        calendar[Calendar.SECOND] = 0
        id.add(1)

        val intent = Intent(requireContext(), ServiceAutoLauncher::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), id.size + 1, intent, 0)
        val alarmManager =
            requireContext().getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager

           // calendar.add(Calendar.MINUTE ,-2)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    private fun initVariables() {
        val mCal = Calendar.getInstance()
        mCal.timeZone = TimeZone.getDefault()

        //initail time
        alarmHour = mCal[Calendar.HOUR_OF_DAY]
        alarmMinute = mCal[Calendar.MINUTE]


        //initail date
        alarmYear = mCal[Calendar.YEAR]
        alarmMonth = mCal[Calendar.MONTH]
        alarmDay = mCal[Calendar.DAY_OF_MONTH]




        Log.e(
            "Details",
            "initVariables:   hour : $alarmHour  minute : $alarmMinute   Year : $alarmYear  Month : ${mCal[Calendar.MONTH]} Day: $alarmDay  "
        )

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