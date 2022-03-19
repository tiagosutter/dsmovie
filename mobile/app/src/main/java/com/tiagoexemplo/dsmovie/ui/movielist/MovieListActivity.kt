package com.tiagoexemplo.dsmovie.ui.movielist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.tiagoexemplo.dsmovie.*
import com.tiagoexemplo.dsmovie.common.networking.MovieResponse
import com.tiagoexemplo.dsmovie.common.networking.MoviesResponse
import com.tiagoexemplo.dsmovie.common.networking.MoviesService
import com.tiagoexemplo.dsmovie.ui.common.dialogs.SimpleInfoDialog
import com.tiagoexemplo.dsmovie.ui.movierating.RateMovieActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieListActivity : AppCompatActivity(), MoviesAdapter.Interaction {

    private lateinit var moviesAdapter: MoviesAdapter
    private val TAG = "MainActivity"

    private val moviesRecyclerView: RecyclerView by lazy { findViewById(R.id.moviesRecyclerView) }
    private val moviesProgressIndicator: ProgressBar by lazy { findViewById(R.id.moviesProgressIndicator) }

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
        showProgressIndicator()
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
                hideProgressIndicator()
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.e(TAG, "moviesService.getMovies", t)
                showErrorToast()
                hideProgressIndicator()
            }
        })
    }

    private fun showProgressIndicator() {
        moviesProgressIndicator.visibility = View.VISIBLE
    }

    private fun hideProgressIndicator() {
        moviesProgressIndicator.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    private fun showErrorToast() {
        SimpleInfoDialog.newInstance("Erro de rede").show(supportFragmentManager, null)
    }

    override fun onRateMovieClicked(movieResponse: MovieResponse) {
        val intent = Intent(this, RateMovieActivity::class.java)
        intent.putExtra("movieId", movieResponse.id)
        startActivity(intent)
    }
}