package com.tiagoexemplo.dsmovie

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT


interface MoviesService {
    @GET("movies?size=12&page=0&sort=id")
    fun getMovies(): Call<MoviesResponse>

    @PUT("scores")
    fun saveRating(@Body score: Score): Call<Movie>
}