package com.tiagoexemplo.dsmovie.common.networking

import com.tiagoexemplo.dsmovie.common.networking.MovieDetailsResponse
import com.tiagoexemplo.dsmovie.common.networking.MovieResponse
import com.tiagoexemplo.dsmovie.common.networking.MoviesResponse
import com.tiagoexemplo.dsmovie.common.networking.ScoreRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path


interface MoviesService {
    @GET("movies?size=12&page=0&sort=id")
    fun getMovies(): Call<MoviesResponse>

    @GET("movies/{movieId}")
    fun getMovie(@Path("movieId") movieId: Long): Call<MovieDetailsResponse>

    @PUT("scores")
    fun saveRating(@Body score: ScoreRequest): Call<MovieResponse>
}