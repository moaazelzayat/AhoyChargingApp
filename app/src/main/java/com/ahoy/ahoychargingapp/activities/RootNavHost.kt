package com.ahoy.ahoychargingapp.activities

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.ahoy.ahoychargingapp.features.chargingpoints.ChargingPointsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@Composable
fun RootNavHost(
    navController: NavHostController
){
    AnimatedNavHost(
        modifier = Modifier.fillMaxSize(),
        route = RootRoute.hostRoute,
        navController = navController,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        startDestination = RootRoute.Loading.route(RootRoute)
    ) {

        composable(RootRoute.Loading.route(RootRoute)) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {}
            }
        }

        composable(RootRoute.ChargingPoints.route(RootRoute)) {
            ChargingPointsScreen(navController = navController)
        }

        composable(RootRoute.SinglePoint.route(RootRoute)) {
            Text(text = "Hi Single Point")
        }
    }
}