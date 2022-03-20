package com.tiagoexemplo.dsmovie.movierating

import com.tiagoexemplo.dsmovie.common.networking.MovieDetailsResponse
import com.tiagoexemplo.dsmovie.common.networking.MoviesService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

import org.mockito.Mock
import org.mockito.kotlin.*
import org.mockito.stubbing.Answer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class GetMovieUseCaseTest {

    // region constants
    companion object {
        private const val ID = 1L
        private const val TITLE = "title"
        private const val SCORE = 5.0
        private const val COUNT = 30
        private const val IMAGE = "IMAGE"
        private const val DESCRIPTION = "DESCRIPTION"
    }
    // endregion constants


    // region helper fields
    private val fakeMovieDetail = MovieDetails(ID, TITLE, SCORE, COUNT, IMAGE, DESCRIPTION)

    private val fakeMovieDetailsResponse =
        MovieDetailsResponse(ID, TITLE, SCORE, COUNT, IMAGE, DESCRIPTION)

    @Mock
    private lateinit var listener1: GetMovieUseCase.Listener

    @Mock
    private lateinit var listener2: GetMovieUseCase.Listener

    @Mock
    private lateinit var moviesService: MoviesService

    @Mock
    private lateinit var mockedCall: Call<MovieDetailsResponse>
    // endregion helper fields


    private lateinit var SUT: GetMovieUseCase

    @Before
    @Throws(Exception::class)
    fun setup() {
        SUT = GetMovieUseCase(moviesService)
        setupForSuccessResponse()
    }

    @Test
    fun getMovie_correctDataPassedToEndPoint() {
        // Arrange
        // Act
        SUT.getMovie(ID)
        // Assert
        verify(moviesService).getMovie(ID)
    }

    @Test
    fun getMovie_success_listenersNotifiedWithCorrectData() {
        // Arrange
        SUT.registerListener(listener1)
        SUT.registerListener(listener2)
        // Act
        SUT.getMovie(ID)
        // Assert
        val expectedResult = GetMovieUseCase.Result.Success(fakeMovieDetail)
        verify(listener1).onGetMovieResult(expectedResult)
        verify(listener1).onGetMovieResult(expectedResult)
    }

    @Test
    fun getMovie_error_listenersNotifiedOfError() {
        // Arrange
        setupForError()
        SUT.registerListener(listener1)
        SUT.registerListener(listener2)
        // Act
        SUT.getMovie(ID)
        // Assert
        val expectedResult = GetMovieUseCase.Result.Error
        verify(listener1).onGetMovieResult(expectedResult)
        verify(listener2).onGetMovieResult(expectedResult)
    }

    // region helper methods
    private fun setupForError() {
        whenever(moviesService.getMovie(any())).thenReturn(mockedCall)
        whenever(mockedCall.enqueue(any())).doAnswer(Answer {
            val callback = it.getArgument(0) as Callback<MovieDetailsResponse>
            callback.onFailure(mockedCall, Throwable())
            null
        })
    }

    private fun setupForSuccessResponse() {
        whenever(moviesService.getMovie(any())).thenReturn(mockedCall)
        whenever(mockedCall.enqueue(any())).doAnswer(Answer {
            val callback = it.getArgument(0) as Callback<MovieDetailsResponse>
            callback.onResponse(mockedCall, Response.success(fakeMovieDetailsResponse))
            null
        })
    }
    // endregion helper methods


    // region helper classes

    // endregion helper classes
}