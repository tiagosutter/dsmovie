package com.tiagoexemplo.dsmovie

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path


interface MoviesService {
    @GET("movies?size=12&page=0&sort=id")
    fun getMovies(): Call<MoviesResponse>

    @GET("movies/{movieId}")
    fun getMovie(@Path("movieId") movieId: Long): Call<MovieDetails>

    @PUT("scores")
    fun saveRating(@Body score: Score): Call<Movie>
}