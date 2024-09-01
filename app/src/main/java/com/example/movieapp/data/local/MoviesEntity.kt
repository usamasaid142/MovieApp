package com.example.movieapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
@Entity(tableName = "movie_table")
data class MoviesEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int?,
    val movieId: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)
