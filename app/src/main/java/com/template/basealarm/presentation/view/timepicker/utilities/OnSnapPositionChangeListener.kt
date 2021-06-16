package com.template.basealarm.presentation.view.timepicker.utilities

import androidx.recyclerview.widget.RecyclerView

interface OnSnapPositionChangeListener {

  fun onSnapPositionChange(position: Int, rv: RecyclerView)
}