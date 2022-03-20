package com.tiagoexemplo.dsmovie.movierating

import com.tiagoexemplo.dsmovie.common.networking.MovieDetailsResponse

data class MovieDetails(
    val id: Long,
    val title: String,
    val score: Double,
    val count: Int,
    val image: String,
    val description: String
) {
    companion object {
        fun fromMovieDetailsResponse(movieDetailsResponse: MovieDetailsResponse): MovieDetails {
            return MovieDetails(
                id = movieDetailsResponse.id,
                title = movieDetailsResponse.title,
                score = movieDetailsResponse.score,
                count = movieDetailsResponse.count,
                image = movieDetailsResponse.image,
                description = movieDetailsResponse.description,
            )
        }
    }
}
