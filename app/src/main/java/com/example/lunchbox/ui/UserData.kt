package com.example.lunchbox.ui
data class User(
    val username: String,
    val email: String,
    val password: String,
    val listings: List<Listing>,
    val offers: Offers
)

data class Listing(
    val name: String,
    val quantity: Double,
    val date: String,
    val description: String,
    val tags: List<String>,
    val images: List<String>
)

data class Offers(
    val completed: List<Offer>,
    val sent: List<Offer>,
    val received: List<Offer>
)

data class Offer(
    val name: String,
    val image: String,
    val distance: String
)
