package com.tiagoexemplo.dsmovie.ui.movielist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.tiagoexemplo.dsmovie.R
import com.tiagoexemplo.dsmovie.movielist.Movie
import com.tiagoexemplo.dsmovie.ui.common.dialogs.SimpleInfoDialog
import com.tiagoexemplo.dsmovie.ui.movierating.RateMovieActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListActivity : AppCompatActivity(), MoviesAdapter.Interaction {

    private lateinit var moviesAdapter: MoviesAdapter

    private val moviesRecyclerView: RecyclerView by lazy { findViewById(R.id.moviesRecyclerView) }
    private val moviesProgressIndicator: ProgressBar by lazy { findViewById(R.id.moviesProgressIndicator) }

    private val viewModel: MovieListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesAdapter = MoviesAdapter(this)
        moviesRecyclerView.adapter = moviesAdapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.moviesLiveData.observe(this) { movies ->
            moviesAdapter.submitList(movies)
        }

        viewModel.somethingWentWrongLiveData.observe(this) {
            if (it) {
                showErrorToast()
            }
        }
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

    override fun onRateMovieClicked(movie: Movie) {
        val intent = Intent(this, RateMovieActivity::class.java)
        intent.putExtra("movieId", movie.id)
        startActivity(intent)
    }
}