package com.villageapp.managers

import org.greenrobot.eventbus.EventBus

object EventBusHelper {

    fun sendEvent(data: Any) {
        EventBus.getDefault().post(data)
    }


}