package com.template.basealarm.ui

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import com.template.basealarm.R
import com.template.basealarm.databinding.FragmentCalenderBinding
import com.template.basealarm.util.event.Message
import com.template.basealarm.util.event.MessageEvent
import org.greenrobot.eventbus.EventBus


class CalFragment : Fragment(R.layout.fragment_calender) {



    lateinit var binding: FragmentCalenderBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCalenderBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        binding.mainCalender.setOnDateChangeListener(CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
            binding.txtDate.text = "$dayOfMonth-$view-$year"

            var message=MessageEvent()
            message.message="year/month/dayOfMonth"
            EventBus.getDefault().post(message);

        })




    }




}