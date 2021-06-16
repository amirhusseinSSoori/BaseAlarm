package com.template.basealarm.presentation.view.timepicker

import androidx.annotation.NonNull
import com.template.basealarm.presentation.view.timepicker.model.CmtpTime12


interface OnTime12PickedListener {

  fun onTimePicked(@NonNull time: CmtpTime12)
}