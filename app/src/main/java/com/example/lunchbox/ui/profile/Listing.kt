package com.example.lunchbox.ui.profile

data class Listing (
    val name : String,
    val quantity: Double,
    val date: String,
    val description: String,
    val tags: Array<String>,
    val images: Array<String>,

)
//Blank example of a listing item
/*
{
        "name": "",
        "quantity": ,
        "date": "",
        "description": "",
        "tags": [],
        "images": []
      }


 */