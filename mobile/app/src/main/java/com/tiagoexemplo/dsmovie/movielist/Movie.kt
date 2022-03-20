package com.tiagoexemplo.dsmovie.movielist

import com.tiagoexemplo.dsmovie.common.networking.MovieResponse

data class Movie(
    val id: Long,
    val title: String,
    val score: Double,
    val count: Int,
    val image: String,
) {
    companion object {
        fun fromMovieResponse(movieResponse: MovieResponse): Movie {
            return Movie(
                id = movieResponse.id,
                title = movieResponse.title,
                score = movieResponse.score,
                count = movieResponse.count,
                image = movieResponse.image
            )
        }
    }
}