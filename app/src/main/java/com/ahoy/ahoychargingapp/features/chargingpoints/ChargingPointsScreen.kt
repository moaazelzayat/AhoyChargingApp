package com.ahoy.ahoychargingapp.features.chargingpoints

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ahoy.ahoychargingapp.activities.RootNavHost
import com.ahoy.ahoychargingapp.activities.RootRoute
import com.ahoy.ahoychargingapp.ui.ErrorBannerHostState
import com.ahoy.ahoychargingapp.ui.rememberErrorBannerHostState
import kotlinx.coroutines.flow.collect

val LocalErrorBannerHost = compositionLocalOf<ErrorBannerHostState> {
    error("LocalErrorBannerHost not set")
}

@Composable
fun ChargingPointsScreen(
    navController: NavHostController,
    vm: ChargingViewModel = hiltViewModel()
) {
    val errorBannerHost = rememberErrorBannerHostState()

    CompositionLocalProvider(LocalErrorBannerHost provides errorBannerHost) {
        RootNavHost(navController = navController)
    }


    val viewState by vm.viewState.collectAsState()
    val eventProcessor = vm::processEvent

    LaunchedEffect(vm.viewEffects){
        vm.viewEffects.collect {
            when(it) {
                is Route -> {
                    val route = it.target.route(RootRoute)
                    navController.navigate(route)
                }
                is ShowError -> {

                }
            }
        }
    }

    ChargingPointsScreen(viewState, eventProcessor)
}

@Composable
fun ChargingPointsScreen(
    viewState: ChargingPointsViewState,
    eventProcessor: (ChargingPointsEvent) -> Unit,
) {

    Surface {
        AnimatedVisibility(
            visible = viewState.isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(modifier = Modifier.fillMaxSize()){
                Button(onClick = { eventProcessor(ChargingPointClicked("dd")) }) {
                    Text(text = "Hi all")
                }
            }
        }
    }
}