package com.tiagoexemplo.dsmovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), MoviesAdapter.Interaction {

    val moviesRecyclerView: RecyclerView by lazy { findViewById(R.id.moviesRecyclerView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val moviesAdapter = MoviesAdapter(this)
        moviesRecyclerView.adapter = moviesAdapter
        moviesAdapter.submitList(listOf(
            Movie(1, "The Witcher", 1.0, 1, "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jBJWaqoSCiARWtfV0GlqHrcdidd.jpg"),
            Movie(1, "Venom: Tempo de Carnificina", 1.3, 3, "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/vIgyYkXkg6NC2whRbYjBD7eb3Er.jpg"),
            Movie(1, "O Espetacular Homem-Aranha 2: A Ameaça de Electro", 2.0, 2, "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/u7SeO6Y42P7VCTWLhpnL96cyOqd.jpg"),
            Movie(1, "Matrix Resurrections", 2.3, 45, "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/hv7o3VgfsairBoQFAawgaQ4cR1m.jpg"),
            Movie(1, "Shang-Chi e a Lenda dos Dez Anéis", 3.0, 100, "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/cinER0ESG0eJ49kXlExM0MEWGxW.jpg"),
            Movie(1, "Django Livre", 3.3, 2469, "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/2oZklIzUbvZXXzIFzv7Hi68d6xf.jpg"),
            Movie(1, "Titanic", 4.0, 1, "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/yDI6D5ZQh67YU4r2ms8qcSbAviZ.jpg"),
            Movie(1, "O Lobo de Wall Street", 4.3, 30, "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/cWUOv3H7YFwvKeaQhoAQTLLpo9Z.jpg"),
            Movie(1, "Aves de Rapina: Arlequina e sua Emancipação Fantabulosa", 5.0, 67, "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jiqD14fg7UTZOT6qgvzTmfRYpWI.jpg"),
        ))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onRateClicked(item: Movie) {
        TODO("Not yet implemented")
    }
}