package com.example.movieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.adapter.AllMoviesAdapter
import com.example.movieapp.databinding.MovieslistfragmentBinding
import com.example.movieapp.model.Result
import com.example.movieapp.utils.Resource
import com.example.movieapp.viewmodels.MoviesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesListFragment : Fragment(),AllMoviesAdapter.IMoviesListener {

  private lateinit var binding:MovieslistfragmentBinding
    private val productViewModel: MoviesViewModel by viewModels()

    private val moviesAdapter : AllMoviesAdapter by lazy {
        AllMoviesAdapter(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= MovieslistfragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         productRecylerview()
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
        productViewModel.allMoviesResponse.observe(
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
            }
        }

        productViewModel.getAllMovies()
    }

    override fun onItemClicked(result: Result) {
        val action=MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(result)
        findNavController().navigate(action)
    }
}