package com.example.movieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.adapter.AllMoviesAdapter
import com.example.movieapp.data.local.MoviesEntity
import com.example.movieapp.databinding.MovieslistfragmentBinding
import com.example.movieapp.model.MovieId
import com.example.movieapp.model.Result
import com.example.movieapp.utils.Resource
import com.example.movieapp.viewmodels.MoviesViewModel
import com.example.movieapp.viewmodels.ViewModelDataBase
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesListFragment : Fragment(), AllMoviesAdapter.IMoviesListener {

    private lateinit var binding: MovieslistfragmentBinding
    private val moviesViewModel: MoviesViewModel by viewModels()
    private val viewModels: ViewModelDataBase by viewModels()
    private var moviesId = mutableListOf<MovieId>()
    private val moviesAdapter: AllMoviesAdapter by lazy {
        AllMoviesAdapter(this,moviesId)
    }
    private var id = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieslistfragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productRecylerview()
        getAllMoviesFromLocalDatabase()
        getProductCallBack()
    }

    private fun productRecylerview() {
        binding.rvMovies.apply {
            adapter = moviesAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }
    }

    private fun getProductCallBack() {
        moviesViewModel.allMoviesResponse.observe(
            viewLifecycleOwner
        ) { response ->
            when (response) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.sucess -> {
                    binding.progressBar.visibility = View.GONE
                    response.let {
                        moviesAdapter.submitList(it.data?.results)
                    }
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(requireView(), "${response.message}", Snackbar.LENGTH_SHORT)
                        .show()
                }

                else -> {}
            }
        }

        moviesViewModel.getAllMovies()
    }

    override fun onItemClicked(result: Result) {
        val action =
            MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(result)
        findNavController().navigate(action)
    }

    override fun addToFavorite(result: Result) {
        id++
        val movieEntity = MoviesEntity(
            id,
            result.id,
            result.original_language,
            result.title,
            result.overview,
            result.popularity,
            result.poster_path,
            result.release_date,
            result.title,
            result.video,
            result.vote_average,
            result.vote_count
        )
        viewModels.insertMovies(movieEntity)
        Snackbar.make(requireView(),"data Sent Successfully",Snackbar.LENGTH_SHORT).show()
    }

    private fun getAllMoviesFromLocalDatabase() {
        viewModels.allMovies.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()){
                id= it[it.size-1].id!!
            }
            it.forEach {
                val movieId=MovieId(it.movieId)
                id= it.id!!
                moviesId.add(movieId)
            }

        })
    }

    override fun onPause() {
        super.onPause()
        moviesId.clear()
    }
}

