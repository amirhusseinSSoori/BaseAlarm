package com.template.basealarm.presentation.ui.alram

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.template.basealarm.R
import com.template.basealarm.data.service.ServiceAutoLauncher
import com.template.basealarm.databinding.FragmentAlarmBinding
import com.template.basealarm.domain.entity.Alarm
import com.template.basealarm.presentation.ui.adapter.AlarmAdapter
import com.template.basealarm.presentation.view.timepicker.CmtpTimeDialogFragment
import com.template.basealarm.presentation.view.timepicker.OnTime24PickedListener
import com.template.basealarm.presentation.view.timepicker.model.CmtpTime24
import dagger.hilt.android.AndroidEntryPoint
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class AlarmFragment : Fragment(R.layout.fragment_alarm) {
    private var alarmYear = 0
    private var alarmMonth = 0
    private var alarmDay = 0
    private var alarmHour = 0
    private var alarmMinute = 0
    private var enableAlarmRepeat = false




    private var number: Int? = null
    lateinit var id: ArrayList<Int>
    lateinit var binding: FragmentAlarmBinding
    private var picker: PersianDatePickerDialog? = null
    val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
    val timeFormat = SimpleDateFormat("K:mm", Locale.ENGLISH)
    private val viewModel: AlarmViewModel by viewModels()
    lateinit var alarmAdapter: AlarmAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alarmAdapter = AlarmAdapter()

        getDataFromLocalColloct()
        getAlarmIdCollect()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAlarmBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        initVariables()

        viewModel.showDetailsAlarm()


        //getAlarm Id
        viewModel.getAlarmId()


        setUPSwitchInAndroid()

        binding.btnSubmit.setOnClickListener {
            setAlarms(number!!)
            updateAlarmId(number!! + 1)
            binding.switchEnable.isChecked = false
        }


        binding.btnDate.setOnClickListener {

            picker = PersianDatePickerDialog(requireContext())
                .setPositiveButtonString("????????")
                .setNegativeButton("????????????")
                .setTodayButton("??????????")
                .setTodayButtonVisible(true)
                .setMinYear(1300)
                .setAllButtonsTextSize(12)
                .setActionTextColor(Color.WHITE)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setInitDate(alarmYear, alarmMonth, alarmDay)
                .setBackgroundColor(Color.parseColor("#534bae"))
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setTitleColor(Color.WHITE)
                .setShowInBottomSheet(true)
                .setListener(object : PersianPickerListener {
                    override fun onDateSelected(persianPickerDate: PersianPickerDate) {
                        alarmYear = persianPickerDate.gregorianYear
                        alarmMonth = persianPickerDate.gregorianMonth - 1
                        alarmDay = persianPickerDate.gregorianDay


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
            dialog.setInitialTime24(alarmHour, alarmMinute)
            dialog.setOnTime24PickedListener(object : OnTime24PickedListener {
                override fun onTimePicked(time: CmtpTime24) {
                    alarmHour = time.hour
                    alarmMinute = time.minute
                    binding.txtTime.text = "${time.hour} - ${time.minute} "
                }
            })
            dialog.show(requireActivity().supportFragmentManager, "TimePicker")
        }

    }

    private fun setAlarms(alarmid: Int) {
        val calendar = Calendar.getInstance()
        calendar[alarmYear, alarmMonth] = alarmDay
        calendar[Calendar.HOUR_OF_DAY] = alarmHour
        calendar[Calendar.MINUTE] = alarmMinute
        calendar[Calendar.SECOND] = 0





        if (enableAlarmRepeat) {
            calendar.add(Calendar.MINUTE, -1);
        }


        val intent = Intent(requireContext(), ServiceAutoLauncher::class.java)
        intent.putExtra("alarmId", alarmid)
        intent.putExtra("min_main", alarmMinute)
        intent.putExtra("min", checkRepeatAlarm(enableAlarmRepeat, alarmMinute))
        intent.putExtra("hour", alarmHour)

        intent.putExtra("day", alarmDay)
        intent.putExtra("month", alarmMonth)
        intent.putExtra("year", alarmYear)

        intent.putExtra("date", dateFormat.format(calendar.time))
        intent.putExtra("repeat", enableAlarmRepeat)
        val pendingIntent =
            PendingIntent.getBroadcast(requireContext(), alarmid, intent, 0)
        val alarmManager =
            requireContext().getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager




        lifecycleScope.launch {
            viewModel.insertToDbAlarm(
                Alarm(
                    "${calendar[Calendar.HOUR_OF_DAY]}:${alarmMinute}",
                    dateFormat.format(calendar.time),
                    enableAlarmRepeat,
                    "now",
                    alarmid,
                )
            )
        }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

    }

    private fun initVariables() {
        val mCal = Calendar.getInstance()
        mCal.timeZone = TimeZone.getDefault()

        //initial time
        alarmHour = mCal[Calendar.HOUR_OF_DAY]
        alarmMinute = mCal[Calendar.MINUTE]


        //initial  date
        alarmYear = mCal[Calendar.YEAR]
        alarmMonth = mCal[Calendar.MONTH]
        alarmDay = mCal[Calendar.DAY_OF_MONTH]


        //for date
        binding.txtDate.text = dateFormat.format(mCal.time)
        //for time
        binding.txtTime.text = timeFormat.format(mCal.time)
    }

    private fun updateAlarmId(id: Int) {
        viewModel.updateAlarmId(id)
    }

    private fun getDataFromLocalColloct() {
        lifecycleScope.launchWhenStarted {
            viewModel.getDetailsAlarmCollect.collect { data ->
                when (data) {
                    is AlarmViewModel.StatusAlarm.Show -> {
                        alarmAdapter!!.differ.submitList(data.state)
                        setUpRecyclerView()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewAlartFDetails.apply {
            adapter = alarmAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL, false
            )
        }
    }

    private fun getAlarmIdCollect() {
        lifecycleScope.launch() {
            viewModel.getDetailsAlarmIdCollect.collect { alarmStatus ->
                when (alarmStatus) {
                    is AlarmViewModel.StatusAlarmId.Show -> {
                        number = alarmStatus.state
                    }
                    else -> Unit
                }
            }
        }

    }


    private fun setUPSwitchInAndroid() {
        binding.switchEnable.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                enableAlarmRepeat = isChecked
                Log.e("isChecked", "setUPSwitchInAndroid:  ${enableAlarmRepeat}")
                // The toggle is enabled
            } else {
                enableAlarmRepeat = isChecked
                Log.e("isNotChecked", "setUPSwitchInAndroid:  ${enableAlarmRepeat}")
                // The toggle is disabled
            }
        }
    }


    fun checkRepeatAlarm(status: Boolean, min: Int): Int {
        return if (status) {
            min - 1
        } else {
            min
        }

    }

}