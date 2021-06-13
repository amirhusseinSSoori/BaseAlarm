package com.template.basealarm

import android.app.Application
import com.template.basealarm.util.event.Message
import org.greenrobot.eventbus.EventBus

class EventBusApp: Application() {

    override fun onCreate() {
        super.onCreate()

        EventBus.builder()
            // have a look at the index class to see which methods are picked up
            // if not in the index @Subscribe methods will be looked up at runtime (expensive)

            .installDefaultEventBus()
    }

}