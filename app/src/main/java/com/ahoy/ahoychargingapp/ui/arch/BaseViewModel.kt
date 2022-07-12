package com.ahoy.ahoychargingapp.ui.arch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<STATE_T, EVENT_T> : ViewModel() {
    abstract val viewState: StateFlow<STATE_T>
    abstract fun processEvent(eventT: EVENT_T)
}

interface WithEffects<EFFECT_T> {
    val viewEffects: Flow<EFFECT_T>
}