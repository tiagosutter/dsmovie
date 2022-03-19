package com.tiagoexemplo.dsmovie.common.networking


data class MovieDetailsResponse(
    val id: Long,
    val title: String,
    val score: Double,
    val count: Int,
    val image: String,
    val description: String
)