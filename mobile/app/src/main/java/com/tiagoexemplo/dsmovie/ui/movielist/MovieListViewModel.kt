package com.tiagoexemplo.dsmovie.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tiagoexemplo.dsmovie.movielist.GetMoviesUseCase
import com.tiagoexemplo.dsmovie.movielist.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) :
    ViewModel(), GetMoviesUseCase.Listener {

    private val _somethingWentWrongLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val somethingWentWrongLiveData: LiveData<Boolean> = _somethingWentWrongLiveData
    private val _moviesLiveData = MutableLiveData<List<Movie>>(listOf())
    val moviesLiveData: LiveData<List<Movie>> = _moviesLiveData

    init {
        getMoviesUseCase.registerListener(this)
        getMovies()
    }

    private fun getMovies() {
        getMoviesUseCase.getMovies()
    }

    override fun onGetMoviesResult(result: GetMoviesUseCase.Result) {
        when (result) {
            is GetMoviesUseCase.Result.Success -> _moviesLiveData.value = result.movies
            GetMoviesUseCase.Result.Error -> _somethingWentWrongLiveData.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        getMoviesUseCase.unregisterListener(this)
    }
}