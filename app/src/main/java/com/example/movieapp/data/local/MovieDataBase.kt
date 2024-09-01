package com.example.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MoviesEntity::class], version = 1)
abstract class MovieDataBase:RoomDatabase() {
    abstract fun moviesDao():MoviesDao
}