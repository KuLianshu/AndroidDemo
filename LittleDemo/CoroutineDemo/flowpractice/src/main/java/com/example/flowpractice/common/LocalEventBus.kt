package com.example.flowpractice.common

import kotlinx.coroutines.flow.MutableSharedFlow

object LocalEventBus {

    val events = MutableSharedFlow<Event>()

    suspend fun postEvent(event: Event){
        events.emit(event)
    }

}

data class Event(val timestamp: Long)