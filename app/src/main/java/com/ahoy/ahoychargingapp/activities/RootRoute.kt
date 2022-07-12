package com.ahoy.ahoychargingapp.activities

import com.ahoy.ahoychargingapp.ui.nav.NavHostNode
import com.ahoy.ahoychargingapp.ui.nav.NavNode

sealed class RootRoute(
    override val id: String
) : NavNode {
    companion object RootHost : NavHostNode("zb", null)
    object ChargingPoints: RootRoute("charging_points")
    object SinglePoint: RootRoute("single_point")
}