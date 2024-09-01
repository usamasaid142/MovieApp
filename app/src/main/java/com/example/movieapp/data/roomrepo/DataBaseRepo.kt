package com.example.movieapp.data.roomrepo


import com.example.movieapp.data.local.MoviesDao
import com.example.movieapp.model.Result
import javax.inject.Inject

class DataBaseRepo @Inject constructor(private val dao:MoviesDao) {
    suspend fun insertMovies(result: Result)=dao.insertMovies(result)
    fun getMovies()=dao.getMovies()
}
