package com.template.basealarm

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.template.basealarm.databinding.ActivityFullScreenBinding
import com.template.basealarm.databinding.FragmentAlarmBinding
import com.template.basealarm.util.SwipeListener

class FullScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivityFullScreenBinding
     val SCREEN_OFF = "screen_off"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFullScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val win = window
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        if (!intent.getBooleanExtra(
              SCREEN_OFF,
                false
            )
        ) {
            win.addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
            )
        }

        SwipeListener(binding.mainContainer,this,applicationContext)

    }
}