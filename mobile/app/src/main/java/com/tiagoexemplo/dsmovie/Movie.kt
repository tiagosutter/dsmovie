package com.tiagoexemplo.dsmovie


data class Movie(
    val id: Long,
    val title: String,
    val score: Double,
    val count: Int,
    val image: String,
)