package com.tiagoexemplo.dsmovie

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RateMovieActivity : AppCompatActivity() {

    private val TAG = "RateMovieActivity"

    private lateinit var moviesService: MoviesService
    private lateinit var movie: MovieDetails

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

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dsmovie-tiago.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        moviesService = retrofit.create(MoviesService::class.java)

        val movieId = intent.getLongExtra("movieId", 0L)

        val movieCall = moviesService.getMovie(movieId)
        rateMovieProgressBar.visibility = View.VISIBLE
        movieCall.enqueue(object : Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                val movieDetails = response.body()!!
                movie = movieDetails
                rateMovieMainCard.visibility = View.VISIBLE
                bindMovieInfo(movieDetails)
            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
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

    private fun bindMovieInfo(movie: MovieDetails) {
        rateMovieTitle.text = movie.title
        Glide.with(this).load(movie.image).into(rateMovieImageView)
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

        val score = Score(movie.id, email, rating)
        val saveRatingApiCall = moviesService.saveRating(score)

        saveRatingApiCall.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                showSuccessMessage()
                finish()
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
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