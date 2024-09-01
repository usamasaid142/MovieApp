package com.example.movieapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.GetMoviesResponse
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class MoviesViewModel @Inject constructor(private val repo: MoviesRepository):ViewModel() {

    val allMoviesResponse= MutableLiveData<Resource<GetMoviesResponse>>()


    fun getAllMovies()=viewModelScope.launch(Dispatchers.IO+handler) {
        allMoviesResponse.postValue(Resource.Loading())
        val response=repo.getAllMovies()
        allMoviesResponse.postValue(response?.let { handleGetAllProducts(it) })
    }

    private fun handleGetAllProducts(response: Response<GetMoviesResponse>): Resource<GetMoviesResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())
    }

    val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("exception","exception:${exception.message.toString()}")
    }
}