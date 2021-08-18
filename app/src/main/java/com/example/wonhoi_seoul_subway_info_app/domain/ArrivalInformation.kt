package com.example.wonhoi_seoul_subway_info_app.domain

data class ArrivalInformation(
    val subway: Subway,
    val direction : String,
    val message : String,
    val destination : String,
    val updatedAt : String
)