package com.template.basealarm.presentation.view.timepicker

import com.template.basealarm.presentation.view.timepicker.model.CmtpTime12


internal object CmtpTimeData {
  val HOURS_24 = (0..23)

  val HOURS_12 = (1..12)

  val MINUTES = (0..59)

  val PM_AM = CmtpTime12.PmAm.values().map { it.name }
}