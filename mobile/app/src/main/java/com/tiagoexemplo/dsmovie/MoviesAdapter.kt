package com.tiagoexemplo.dsmovie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class MoviesAdapter(private val interaction: Interaction) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MoviesViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Movie>) {
        differ.submitList(list)
    }

    class MoviesViewHolder(itemView: View, private val interaction: Interaction) :
        RecyclerView.ViewHolder(itemView) {

        private val movieItemImageView: ImageView = itemView.findViewById(R.id.movieItemImageView)
        private val movieItemTitle: TextView = itemView.findViewById(R.id.movieItemTitle)
        private val movieItemScore: TextView = itemView.findViewById(R.id.movieItemScore)
        private val movieItemRatingCount: TextView = itemView.findViewById(R.id.movieItemRatingCount)
        private val movieItemRateButton: Button = itemView.findViewById(R.id.movieItemRateButton)

        private val movieItemStar1: ImageView = itemView.findViewById(R.id.movieItemStar1)
        private val movieItemStar2: ImageView = itemView.findViewById(R.id.movieItemStar2)
        private val movieItemStar3: ImageView = itemView.findViewById(R.id.movieItemStar3)
        private val movieItemStar4: ImageView = itemView.findViewById(R.id.movieItemStar4)
        private val movieItemStar5: ImageView = itemView.findViewById(R.id.movieItemStar5)

        private val startViews = listOf(movieItemStar1, movieItemStar2, movieItemStar3,
            movieItemStar4, movieItemStar5)

        // TODO: Deal with I18n
        @SuppressLint("SetTextI18n")
        fun bind(movie: Movie) {
            movieItemTitle.text = movie.title
            movieItemScore.text = "%.2f".format(movie.score)
            movieItemRatingCount.text = "${movie.count} Avaliações"

            val scoreIntPart = movie.score.toInt()
            val scoreDecimalPart = movie.score - scoreIntPart

            for (i in 0 until scoreIntPart) {
                startViews[i].setImageResource(R.drawable.star_full)
            }

            if (scoreDecimalPart > 0 && scoreIntPart != 5) {
                startViews[scoreIntPart].setImageResource(R.drawable.star_half)
            }

            Glide.with(itemView)
                .load(movie.image)
                .into(movieItemImageView)

            movieItemRateButton.setOnClickListener { interaction.onRateMovieClicked(movie) }
        }
    }

    interface Interaction {
        fun onRateMovieClicked(movie: Movie)
    }
}