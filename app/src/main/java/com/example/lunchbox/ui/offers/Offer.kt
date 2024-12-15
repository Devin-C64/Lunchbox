package com.example.lunchbox.ui.offers

data class Offer (
    val sendinguser: String,
    val recievinguser: String,
    val message: String,
    val itemname : String,
    val quantity: Double,
    val distance: Double, // Distance in kilometers or miles
    val closestSchoolBuilding: String,
    val status: String
)


/*
  {
    "sendinguser" : "",
    "recievinguser" : "",
    "message" : "",
    "itemname ": "",
    "quantity": "",
    "distance":"",
    "closestSchoolBuilding": "",
    "status": ""
    }
 */
