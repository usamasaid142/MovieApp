package com.example.movieapp.repository

import com.example.movieapp.common.ApiService
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getAllMovies() = apiService.getAllMovies()
}