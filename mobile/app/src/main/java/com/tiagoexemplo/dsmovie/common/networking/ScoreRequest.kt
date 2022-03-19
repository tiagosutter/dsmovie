package com.tiagoexemplo.dsmovie.common.networking

data class ScoreRequest(
    var movieId: Long,
    var email: String,
    var score: Double
)
