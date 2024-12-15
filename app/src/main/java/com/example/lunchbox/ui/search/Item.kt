package com.example.lunchbox.ui.search

data class Item(
    val name: String,
    val username: String,
    val distance: Double, // Distance in kilometers or miles
    val closestSchoolBuilding: String
)