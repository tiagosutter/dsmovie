package com.tiagoexemplo.dsmovie.movierating

import com.tiagoexemplo.dsmovie.common.BaseObservable
import com.tiagoexemplo.dsmovie.common.networking.MovieDetailsResponse
import com.tiagoexemplo.dsmovie.common.networking.MoviesService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetMovieUseCase(val moviesService: MoviesService) :
    BaseObservable<GetMovieUseCase.Listener>() {

    sealed class Result {
        data class Success(val movieDetails: MovieDetails) : Result()
        object Error : Result()
    }

    interface Listener {
        fun onGetMovieResult(result: Result)
    }

    fun getMovie(id: Long) {
        val movieCall = moviesService.getMovie(id)
        movieCall.enqueue(object : Callback<MovieDetailsResponse> {
            override fun onResponse(
                call: Call<MovieDetailsResponse>,
                response: Response<MovieDetailsResponse>
            ) {
                val body = response.body()!!
                val result = Result.Success(MovieDetails.fromMovieDetailsResponse(body))
                listeners.forEach { it.onGetMovieResult(result) }
            }

            override fun onFailure(call: Call<MovieDetailsResponse>, t: Throwable) {
                listeners.forEach { it.onGetMovieResult(Result.Error) }
            }
        })
    }
}