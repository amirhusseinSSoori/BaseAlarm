package com.template.basealarm.presentation.view.timepicker

import androidx.annotation.NonNull
import com.template.basealarm.presentation.view.timepicker.model.CmtpTime24


interface OnTime24PickedListener {

  fun onTimePicked(@NonNull time: CmtpTime24)
}