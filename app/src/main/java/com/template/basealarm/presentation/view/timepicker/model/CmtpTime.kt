package com.template.basealarm.presentation.view.timepicker.model

interface CmtpTime {
  val hour: Int
  val minute: Int

  fun getType(): CmtpTimeType
}