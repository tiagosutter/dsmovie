package com.tiagoexemplo.dsmovie.ui.movielist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tiagoexemplo.dsmovie.movielist.GetMoviesUseCase
import com.tiagoexemplo.dsmovie.movielist.Movie
import com.utils.getOrAwaitValue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*
import org.junit.Rule
import org.mockito.kotlin.*

@RunWith(MockitoJUnitRunner::class)
class MovieListViewModelTest {

    // region constants
    companion object {
        private const val ID = 1L
        private const val TITLE = "title"
        private const val SCORE = 5.0
        private const val COUNT = 30
        private const val IMAGE = "IMAGE"
    }
    // endregion constants


    @Mock
    private lateinit var getMoviesMock: GetMoviesUseCase

    private val movie = Movie(ID, TITLE, SCORE, COUNT, IMAGE)

    private val moviesList = listOf(movie)
    // region helper fields


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    // endregion helper fields


    lateinit var SUT: MovieListViewModel

    @Before
    @Throws(Exception::class)
    fun setup() {
        setupForSuccess()
        SUT = MovieListViewModel(getMoviesMock)
    }

    @Test
    fun init_success_liveDataNotifiesWithCorrectData() {
        // Arrange - Already arranged on setup
        // Act - getMovies is called on init

        // Assert
        val liveDataResult = SUT.moviesLiveData.getOrAwaitValue()
        assertThat(liveDataResult, `is`(moviesList))
    }

    @Test
    fun init_error_liveDataNotifiesOfError() {
        // Arrange
        setupForError()
        val SUT = MovieListViewModel(getMoviesMock)
        // Act - getMovies already called on init

        // Assert
        val liveDataResult = SUT.somethingWentWrongLiveData.getOrAwaitValue()
        assertThat(liveDataResult, `is`(true))
    }


    // region helper methods
    private fun setupForSuccess() {
        whenever(getMoviesMock.registerListener(any())).doAnswer { invocation ->
            val listener: GetMoviesUseCase.Listener = invocation.getArgument(0)

            whenever(getMoviesMock.getMovies()).doAnswer {
                listener.onGetMoviesResult(GetMoviesUseCase.Result.Success(moviesList))
                null
            }
            null
        }
    }

    private fun setupForError() {
        whenever(getMoviesMock.registerListener(any())).doAnswer { invocation ->
            val listener: GetMoviesUseCase.Listener = invocation.getArgument(0)

            whenever(getMoviesMock.getMovies()).doAnswer {
                listener.onGetMoviesResult(GetMoviesUseCase.Result.Error)
                null
            }
            null
        }
    }
    // endregion helper methods


    // region helper classes

    // endregion helper classes
}