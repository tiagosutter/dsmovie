package com.tiagoexemplo.dsmovie

import java.io.Serializable


data class MovieDetails(
    val id: Long,
    val title: String,
    val score: Double,
    val count: Int,
    val image: String,
    val description: String
)