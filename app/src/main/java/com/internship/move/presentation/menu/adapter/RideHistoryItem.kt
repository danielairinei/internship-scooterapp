package com.internship.move.presentation.menu.adapter

data class RideHistoryItem(
    val rideId: Int,
    val startAddress: String = "Strada Lunii 2A",
    val endAddress: String = "Strada Ariesului 80",
    val distance: Double = 3.2,
    val time: String = "00:22 min"
)