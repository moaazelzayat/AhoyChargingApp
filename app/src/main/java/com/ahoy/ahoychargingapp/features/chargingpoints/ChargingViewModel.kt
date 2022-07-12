package com.ahoy.ahoychargingapp.features.chargingpoints

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ahoy.ahoychargingapp.activities.RootRoute
import com.ahoy.ahoychargingapp.data.Resource
import com.ahoy.ahoychargingapp.data.catchErrorsAsResource
import com.ahoy.ahoychargingapp.data.resourceFlow
import com.ahoy.ahoychargingapp.data.windowedLoadDebounce
import com.ahoy.ahoychargingapp.repository.ChargingPointsRepo
import com.ahoy.ahoychargingapp.ui.arch.BaseViewModel
import com.ahoy.ahoychargingapp.ui.arch.WithEffects
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChargingViewModel @Inject constructor(
    private val repository: ChargingPointsRepo
) : BaseViewModel<ChargingPointsViewState, ChargingPointsEvent>(), WithEffects<ChargingPointsEffect>{

    private val _viewState = MutableStateFlow(ChargingPointsViewState())
    override val viewState: StateFlow<ChargingPointsViewState>
        get() = _viewState.asStateFlow()

    private val _effects = Channel<ChargingPointsEffect>(Channel.BUFFERED)
    override val viewEffects: Flow<ChargingPointsEffect>
        get() = _effects.receiveAsFlow()

    init {
        _effects.trySend(AskForLocationPermission)
    }

    override fun processEvent(eventT: ChargingPointsEvent) {
        when(eventT) {
            is ChargingPointClicked -> {
                _effects.trySend(Route(RootRoute.SinglePoint))
            }
            is GetChargingPoints -> {
                viewModelScope.launch {
                    getChargingPoints(eventT.lat, eventT.lng)
                }
            }
        }
    }

    private suspend fun getChargingPoints(lat: Double, lng: Double) {
        resourceFlow {
            repository.getChargingPoints(lat = lat, lng = lng)
        }.catchErrorsAsResource()
            .windowedLoadDebounce()
            .collect { resource ->
                when(resource){
                    is Resource.Error -> {
                        //TODO show error
                    }
                    is Resource.Loading -> {
                        _viewState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _viewState.update {
                            it.copy(data = resource.data, isLoading = false)
                        }
                    }
                }
            }
    }
}