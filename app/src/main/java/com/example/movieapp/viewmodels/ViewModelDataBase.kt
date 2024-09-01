package com.example.movieapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.roomrepo.DataBaseRepo
import com.example.movieapp.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelDataBase @Inject constructor(private val repo:DataBaseRepo):ViewModel() {

    fun insertMovies(result: Result)=viewModelScope.launch(Dispatchers.IO) {
        repo.insertMovies(result)
    }
    val allMovies: LiveData<List<Result>> = repo.getMovies()

}