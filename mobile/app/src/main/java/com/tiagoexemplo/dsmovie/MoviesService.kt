package com.tiagoexemplo.dsmovie

import retrofit2.Call
import retrofit2.http.GET


interface MoviesService {
    @GET("movies?size=12&page=0&sort=id")
    fun getMovies(): Call<MoviesResponse>
}