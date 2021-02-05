package com.ivanmorgillo.corsoandroid.teamc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = RecipesAdapter()

        // dobbiamo mettere l'adapter in comunicazione con la recyclerview
        recipes_list.adapter = adapter
        //
        viewModel.states.observe(this, { state ->
            when (state) {
                is MainScreenStates.Content -> {
                    recipes_list_progressBar.gone()
                    adapter.setRecipes(state.recipes)
                }
                MainScreenStates.Error -> {
                    recipes_list_progressBar.gone()
                    Snackbar.make(recipes_list, getString(R.string.main_screen_error), Snackbar.LENGTH_LONG)
                        .show()
                }
                MainScreenStates.Loading -> {
                    recipes_list_progressBar.visible()
                }
            }
        })
        viewModel.send(MainScreenEvent.OnReady)
    }
}
