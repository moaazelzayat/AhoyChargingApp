package com.ahoy.ahoychargingapp.ui.nav

abstract class NavHostNode(
    hostId: String,
    parent: NavHostNode?,
) {
    val hostRoute: String = if (parent != null) {
        "${parent.hostRoute}/$hostId"
    } else {
        hostId
    }
}

interface NavNode {
    val id: String
    fun route(parent: NavHostNode): String = "${parent.hostRoute}/$id"
}