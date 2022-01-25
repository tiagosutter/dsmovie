package com.tiagoexemplo.dsmovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class RateMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_movie)

        val movie = intent.getSerializableExtra("movie") as Movie

        val rateMovieImageView: ImageView = findViewById(R.id.rateMovieImageView)
        val rateMovieTitle: TextView = findViewById(R.id.rateMovieTitle)
        val rateMovieEmailEditText: EditText = findViewById(R.id.rateMovieEmailEditText)
        val rateMovieRatingEditText: EditText = findViewById(R.id.rateMovieRatingEditText)
        val rateMovieSaveButton: Button = findViewById(R.id.rateMovieSaveButton)
        val rateMovieCancelButton: Button = findViewById(R.id.rateMovieCancelButton)

        rateMovieTitle.text = movie.title
        Glide.with(this).load(movie.image).into(rateMovieImageView)
    }
}