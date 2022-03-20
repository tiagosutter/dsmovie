import com.tiagoexemplo.dsmovie.common.networking.MovieResponse
import com.tiagoexemplo.dsmovie.common.networking.MoviesResponse
import com.tiagoexemplo.dsmovie.common.networking.MoviesService
import com.tiagoexemplo.dsmovie.movielist.GetMoviesUseCase
import com.tiagoexemplo.dsmovie.movielist.Movie
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*
import org.mockito.Mockito
import org.mockito.kotlin.*
import org.mockito.stubbing.Answer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class GetMoviesUseCaseTest {

    // region constants
    companion object {
        private const val ID = 1L
        private const val TITLE = "title"
        private const val SCORE = 5.0
        private const val COUNT = 30
        private const val IMAGE = "IMAGE"
    }
    // endregion constants


    // region helper fields
    @Mock
    private lateinit var listener1: GetMoviesUseCase.Listener

    @Mock
    private lateinit var listener2: GetMoviesUseCase.Listener

    @Mock
    private lateinit var moviesServiceMock: MoviesService

    @Mock
    private lateinit var mockedCall: Call<MoviesResponse>

    private val fakeMovie = Movie(ID, TITLE, SCORE, COUNT, IMAGE)
    private val fakeMovieResponse = MovieResponse(ID, TITLE, SCORE, COUNT, IMAGE)

    private val fakeMoviesList: List<Movie> = listOf(fakeMovie)

    private val fakeMoviesResponse: MoviesResponse = MoviesResponse(listOf(fakeMovieResponse))
    // endregion helper fields


    lateinit var SUT: GetMoviesUseCase

    @Before
    @Throws(Exception::class)
    fun setup() {
        SUT = GetMoviesUseCase(moviesServiceMock)
        setupForSuccessResponse()
    }

    @Test
    fun getMovies_callsCorrectEndpoint() {
        // Arrange
        // Act
        SUT.getMovies()
        // Assert
        verify(moviesServiceMock).getMovies()
    }

    @Test
    fun getMovies_success_listenersNotifiedOfSuccessWithCorrectData() {
        // Arrange
        SUT.registerListener(listener1)
        SUT.registerListener(listener2)

        // Act
        SUT.getMovies()

        // Assert
        val expectedResult = GetMoviesUseCase.Result.Success(fakeMoviesList)
        verify(listener1).onGetMoviesResult(expectedResult)
        verify(listener2).onGetMoviesResult(expectedResult)
    }

    @Test
    fun getMovies_error_listenersNotifiedOfError() {
        // Arrange
        setupForError()
        SUT.registerListener(listener1)
        SUT.registerListener(listener2)

        // Act
        SUT.getMovies()

        // Assert
        verify(listener1).onGetMoviesResult(GetMoviesUseCase.Result.Error)
        verify(listener2).onGetMoviesResult(GetMoviesUseCase.Result.Error)
    }

    @Test
    fun getMovies_success_unregisteredListenersNotInteractedWith() {
        // Arrange
        SUT.unregisterListener(listener1)
        SUT.unregisterListener(listener2)

        // Act
        SUT.getMovies()

        // Assert
        verifyNoInteractions(listener1)
        verifyNoInteractions(listener2)
    }

    @Test
    fun getMovies_error_unregisteredListenersNotInteractedWith() {
        // Arrange
        setupForError()
        SUT.unregisterListener(listener1)
        SUT.unregisterListener(listener2)

        // Act
        SUT.getMovies()

        // Assert
        verifyNoInteractions(listener1)
        verifyNoInteractions(listener2)
    }

    // region helper methods
    private fun setupForError() {
        whenever(moviesServiceMock.getMovies()).thenReturn(mockedCall)
        whenever(mockedCall.enqueue(any())).doAnswer(Answer {
            val callback = it.getArgument(0) as Callback<MoviesResponse>
            callback.onFailure(mockedCall, Throwable())
            null
        })
    }

    private fun setupForSuccessResponse() {
        whenever(moviesServiceMock.getMovies()).thenReturn(mockedCall)
        whenever(mockedCall.enqueue(any())).doAnswer(Answer {
            val callback = it.getArgument(0) as Callback<MoviesResponse>
            callback.onResponse(mockedCall, Response.success(fakeMoviesResponse))
            null
        })
    }
    // endregion helper methods


    // region helper classes

    // endregion helper classes
}