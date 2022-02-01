package com.tiagoexemplo.dsmovie

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RateMovieActivity : AppCompatActivity() {

    private val TAG = "RateMovieActivity"

    private lateinit var moviesService: MoviesService

    private val rateMovieImageView: ImageView by lazy { findViewById(R.id.rateMovieImageView) }
    private val rateMovieTitle: TextView by lazy { findViewById(R.id.rateMovieTitle) }
    private val rateMovieEmailEditText: EditText by lazy { findViewById(R.id.rateMovieEmailEditText) }
    private val rateMovieRatingEditText: EditText by lazy { findViewById(R.id.rateMovieRatingEditText) }
    private val rateMovieSaveButton: Button by lazy { findViewById(R.id.rateMovieSaveButton) }
    private val rateMovieCancelButton: Button by lazy { findViewById(R.id.rateMovieCancelButton) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_movie)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dsmovie-tiago.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        moviesService = retrofit.create(MoviesService::class.java)

        val movie = intent.getSerializableExtra("movie") as Movie

        rateMovieTitle.text = movie.title
        Glide.with(this).load(movie.image).into(rateMovieImageView)

        rateMovieCancelButton.setOnClickListener { onBackPressed() }

        rateMovieSaveButton.setOnClickListener {
            val email = rateMovieEmailEditText.text.toString()

            val isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
            if (email.isBlank() && !isValidEmail) {
                AlertDialog.Builder(this)
                    .setTitle("Erro de validação")
                    .setMessage("Digite um email válido")
                    .show()
                return@setOnClickListener
            }

            val ratingText = rateMovieRatingEditText.text.toString()
            val rating = if (ratingText.isBlank()) 0.0 else ratingText.toDouble()
            if (rating > 5 || rating == 0.0) {
                AlertDialog.Builder(this)
                    .setTitle("Erro de validação")
                    .setMessage("O valor da nota deve ser entre 1 e 5")
                    .show()
                return@setOnClickListener
            }

            val score = Score(movie.id, email, rating)
            val saveRatingApiCall = moviesService.saveRating(score)

            saveRatingApiCall.enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    showSuccessMessage()
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    Log.e(TAG, "moviesService.getMovies", t)
                    showErrorMessage()
                }
            })
        }
    }

    private fun showSuccessMessage() {
        AlertDialog.Builder(this)
            .setTitle("Salvo com sucesso")
            .setMessage("A avaliação foi salva com sucesso")
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .show()
    }

    private fun showErrorMessage() {
        Toast.makeText(this, "An error has occurred", Toast.LENGTH_SHORT).show()
        AlertDialog.Builder(this)
            .setTitle("Ocorreu um erro ao salvar")
            .show()
    }

}