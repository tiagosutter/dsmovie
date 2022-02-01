package com.tiagoexemplo.dsmovie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity(), MoviesAdapter.Interaction {

    private lateinit var moviesAdapter: MoviesAdapter
    private val TAG = "MainActivity"

    val moviesRecyclerView: RecyclerView by lazy { findViewById(R.id.moviesRecyclerView) }

    private lateinit var moviesService: MoviesService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val retrofit = Retrofit.Builder()
            .baseUrl("https://dsmovie-tiago.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        moviesService = retrofit.create(MoviesService::class.java)


        moviesAdapter = MoviesAdapter(this)
        moviesRecyclerView.adapter = moviesAdapter
    }

    override fun onStart() {
        super.onStart()
        getMovies()
    }

    private fun getMovies() {
        val apiCall = moviesService.getMovies()
        apiCall.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                val responseBody = response.body()
                val movies = responseBody?.content
                if (movies != null) {
                    moviesAdapter.submitList(movies)
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.e(TAG, "moviesService.getMovies", t)
                showErrorDialog()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle("Ocorreu um erro")
            .setMessage("Ocorreu um erro de rede")
            .setCancelable(false)
            .setPositiveButton("Tentar novamente") { dialog, _ ->
                getMovies()
                dialog.dismiss()
            }
            .setNegativeButton("Sair") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .show()
    }

    override fun onRateMovieClicked(movie: Movie) {
        val intent = Intent(this, RateMovieActivity::class.java)
        intent.putExtra("movie", movie)
        startActivity(intent)
    }
}