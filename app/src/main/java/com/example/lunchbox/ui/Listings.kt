package com.example.lunchbox.ui

data class Listing(
    val name: String,
    val quantity: Double,
    val date: String,
    val description: String,
    val tags: List<String>,
    val images: List<String>
)


