package com.tiagoexemplo.dsmovie.movielist

import com.tiagoexemplo.dsmovie.common.networking.MoviesResponse
import com.tiagoexemplo.dsmovie.common.networking.MoviesService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val moviesService: MoviesService) {

    private val listeners: MutableSet<Listener> = mutableSetOf()

    sealed class Result {
        data class Success(val movies: List<Movie>) : Result()
        object Error : Result()
    }

    interface Listener {
        fun onGetMoviesResult(result: Result)
    }

    fun getMovies() {
        val apiCall = moviesService.getMovies()
        apiCall.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                val responseBody = response.body()
                val movieResponse = responseBody?.content
                val movies = movieResponse!!.map { Movie.fromMovieResponse(it) }
                listeners.forEach { it.onGetMoviesResult(Result.Success(movies)) }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                listeners.forEach { it.onGetMoviesResult(Result.Error) }
            }
        })
    }

    fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: Listener) {
        listeners.remove(listener)
    }
}