package com.tiagoexemplo.dsmovie.ui.movierating

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.tiagoexemplo.dsmovie.*
import com.tiagoexemplo.dsmovie.common.networking.MovieDetailsResponse
import com.tiagoexemplo.dsmovie.common.networking.MovieResponse
import com.tiagoexemplo.dsmovie.common.networking.MoviesService
import com.tiagoexemplo.dsmovie.common.networking.ScoreRequest
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class RateMovieActivity : AppCompatActivity() {

    private val TAG = "RateMovieActivity"

    @Inject
    lateinit var moviesService: MoviesService
    private lateinit var movieResponse: MovieDetailsResponse

    private val rateMovieImageView: ImageView by lazy { findViewById(R.id.rateMovieImageView) }
    private val rateMovieTitle: TextView by lazy { findViewById(R.id.rateMovieTitle) }
    private val rateMovieEmailEditText: EditText by lazy { findViewById(R.id.rateMovieEmailEditText) }
    private val rateMovieRatingEditText: EditText by lazy { findViewById(R.id.rateMovieRatingEditText) }
    private val rateMovieSaveButton: Button by lazy { findViewById(R.id.rateMovieSaveButton) }
    private val rateMovieCancelButton: Button by lazy { findViewById(R.id.rateMovieCancelButton) }
    private val rateMovieMainCard: CardView by lazy { findViewById(R.id.rateMovieMainCard) }
    private val rateMovieProgressBar: ProgressBar by lazy { findViewById(R.id.rateMovieProgressBar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_movie)

        val movieId = intent.getLongExtra("movieId", 0L)

        val movieCall = moviesService.getMovie(movieId)
        rateMovieProgressBar.visibility = View.VISIBLE
        movieCall.enqueue(object : Callback<MovieDetailsResponse> {
            override fun onResponse(call: Call<MovieDetailsResponse>, response: Response<MovieDetailsResponse>) {
                val movieDetails = response.body()!!
                movieResponse = movieDetails
                rateMovieMainCard.visibility = View.VISIBLE
                bindMovieInfo(movieDetails)
            }

            override fun onFailure(call: Call<MovieDetailsResponse>, t: Throwable) {
                rateMovieProgressBar.visibility = View.GONE
                rateMovieMainCard.visibility = View.GONE
                showErrorMessage()
            }
        })

        rateMovieCancelButton.setOnClickListener { onBackPressed() }

        rateMovieSaveButton.setOnClickListener {
            saveRating()
        }
    }

    private fun bindMovieInfo(movieResponse: MovieDetailsResponse) {
        rateMovieTitle.text = movieResponse.title
        Glide.with(this).load(movieResponse.image).into(rateMovieImageView)
    }

    private fun saveRating() {
        val email = rateMovieEmailEditText.text.toString()

        val isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (email.isBlank() && !isValidEmail) {
            Toast.makeText(this, "Digite um email válido", Toast.LENGTH_SHORT).show()
            return
        }

        val ratingText = rateMovieRatingEditText.text.toString()
        val rating = if (ratingText.isBlank()) 0.0 else ratingText.toDouble()
        if (rating > 5 || rating == 0.0) {
            Toast.makeText(this, "O valor da nota deve ser entre 1 e 5", Toast.LENGTH_SHORT).show()
            return
        }

        val score = ScoreRequest(movieResponse.id, email, rating)
        val saveRatingApiCall = moviesService.saveRating(score)

        saveRatingApiCall.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                showSuccessMessage()
                finish()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e(TAG, "moviesService.getMovies", t)
                showErrorMessage()
            }
        })
    }

    private fun showSuccessMessage() {
        Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT).show()
    }

    private fun showErrorMessage() {
        Toast.makeText(this, "Ocorreu um erro", Toast.LENGTH_SHORT).show()
    }

}