package com.template.basealarm.presentation.view.timepicker

import androidx.annotation.NonNull
import com.template.basealarm.presentation.view.timepicker.model.CmtpDate


interface OnDatePickedListener {

  fun onDatePicked(@NonNull date: CmtpDate)
}