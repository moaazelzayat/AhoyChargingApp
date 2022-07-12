package com.ahoy.ahoychargingapp.features.chargingpoints

import android.Manifest
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ahoy.ahoychargingapp.activities.MainActivity
import com.ahoy.ahoychargingapp.activities.RootNavHost
import com.ahoy.ahoychargingapp.activities.RootRoute
import com.ahoy.ahoychargingapp.data.ui.ChargingPointModel
import com.ahoy.ahoychargingapp.ui.ErrorBannerHostState
import com.ahoy.ahoychargingapp.ui.rememberErrorBannerHostState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

val LocalErrorBannerHost = compositionLocalOf<ErrorBannerHostState> {
    error("LocalErrorBannerHost not set")
}

@Composable
fun ChargingPointsScreen(
    navController: NavHostController,
    vm: ChargingViewModel = hiltViewModel()
) {
    val errorBannerHost = rememberErrorBannerHostState()
    val context = LocalContext.current
    val eventProcessor = vm::processEvent
    val viewState by vm.viewState.collectAsState()

    CompositionLocalProvider(LocalErrorBannerHost provides errorBannerHost) {
        RootNavHost(navController = navController)
    }

    val locationPermissionRequest = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                (context as MainActivity).getUserCurrentLocation(eventProcessor = eventProcessor)
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                (context as MainActivity).getUserCurrentLocation(eventProcessor)
            }
            else -> {
                // No location access granted.
            }
        }
    }

    LaunchedEffect(vm.viewEffects) {
        vm.viewEffects.collect {
            when (it) {
                is Route -> {
                    val route = it.target.route(RootRoute)
                    navController.navigate(route)
                }
                is ShowError -> {
                    //TODO show error
                }
                AskForLocationPermission -> {
                    locationPermissionRequest.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            }
        }
    }

    ChargingPointsScreen(viewState)
}

fun Activity.getUserCurrentLocation(eventProcessor: (ChargingPointsEvent) -> Unit) {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    location?.latitude?.let {
        GetChargingPoints(
            it,
            location.longitude
        )
    }?.let {
        eventProcessor(
            it
        )
    }
}

@Composable
fun ChargingPointsScreen(
    viewState: ChargingPointsViewState,
) {
    AnimatedVisibility(
        visible = viewState.data != null,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        val data: List<ChargingPointModel>? = viewState.data?.toList()?.map {
            ChargingPointModel(
                title = it.AddressInfo.Title,
                uuid = it.UUID,
                isRecentlyVerified = it.IsRecentlyVerified
            )
        }
        data?.let { ChargingPoints(chargingPoints = data) }
    }

    AnimatedVisibility(
        visible = viewState.isLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Surface {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun ChargingPoints(chargingPoints: List<ChargingPointModel>) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(chargingPoints, itemContent = { item ->
            CardView(item)
        })
    }
}

@Composable
fun CardView(point: ChargingPointModel) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable {
                (context as MainActivity).showAsBottomSheet {
                    Surface {
                        Column(
                            modifier = Modifier
                                .height(100.dp)
                                .background(Color.White)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = point.title)
                            Text(text = "is verified: ${point.isRecentlyVerified}")
                        }
                    }

                }
            },
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                    ) {
                        append(point.title)
                    }
                }
            )
        }
    }
}

fun Activity.showAsBottomSheet(
    content: @Composable (() -> Unit) -> Unit
) {
    val viewGroup = this.findViewById(
        android.R.id.content
    ) as ViewGroup
    addContentToView(viewGroup, content)
}

fun addContentToView(
    viewGroup: ViewGroup,
    content: @Composable (() -> Unit) -> Unit
) {
    viewGroup.addView(
        ComposeView(viewGroup.context).apply {
            setContent {
                BottomSheetWrapper(viewGroup, this, content)
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomSheetWrapper(
    parent: ViewGroup,
    composeView: ComposeView,
    content: @Composable (() -> Unit) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var isSheetOpened by remember { mutableStateOf(false) }

    ModalBottomSheetLayout(
        sheetBackgroundColor = Color.Transparent,
        sheetState = modalBottomSheetState,
        sheetContent = {
            content {
                coroutineScope.launch {
                    modalBottomSheetState.hide()
                }
            }
        }
    ) {}

    BackHandler {
        coroutineScope.launch {
            modalBottomSheetState.hide()
        }
    }

    LaunchedEffect(modalBottomSheetState.currentValue) {
        when (modalBottomSheetState.currentValue) {
            ModalBottomSheetValue.Hidden -> {
                when {
                    isSheetOpened -> parent.removeView(composeView)
                    else -> {
                        isSheetOpened = true
                        modalBottomSheetState.show()
                    }
                }
            }
            else -> {
                Log.i("Moaaz", "Bottom sheet ${modalBottomSheetState.currentValue} state")
            }
        }
    }
}
