package com.ahoy.ahoychargingapp.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.ahoy.ahoychargingapp.data.ChargingPointsResponse
import com.ahoy.ahoychargingapp.data.ChargingPointsResponseItem
import com.ahoy.ahoychargingapp.data.ui.ChargingPointModel
import com.ahoy.ahoychargingapp.features.chargingpoints.ChargingPointsScreen
import com.ahoy.ahoychargingapp.features.chargingpoints.ChargingViewModel
import com.ahoy.ahoychargingapp.features.chargingpoints.GetChargingPoints
import com.ahoy.ahoychargingapp.service.AhoyService
import com.ahoy.ahoychargingapp.ui.theme.AhoyChargingAppTheme
import com.ahoy.ahoychargingapp.utils.keepVisibleForDuration
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ChargingViewModel>()
    private lateinit var locationManager: LocationManager

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                getUserCurrentLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                getUserCurrentLocation()
            } else -> {
            // No location access granted.
        }
        }
    }

    private fun getUserCurrentLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        location?.latitude?.let {
            GetChargingPoints(
                it,
                location.longitude
            )
        }?.let {
            viewModel.processEvent(
                it
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewState = viewModel.viewState.collectAsState()
            AhoyChargingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val data: List<ChargingPointModel>? = viewState.value.data?.toList()?.map {
                        ChargingPointModel(
                            title = it.AddressInfo.Title,
                            uuid = it.UUID,
                            isRecentlyVerified = it.IsRecentlyVerified
                            )
                    }

                    data?.let { ChargingPoints(chargingPoints = data) }

                    AnimatedVisibility(
                        visible = viewState.value.isLoading,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Surface {
                            Box(modifier = Modifier.fillMaxSize()){
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
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
        android.R.id.content) as ViewGroup
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


