package com.vickbt.composeApp.data.cache

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.vickbt.composeApp.data.cache.daos.FavoriteMovieDao
import com.vickbt.composeApp.data.cache.entities.MovieDetailsEntity

@Suppress("ACTUAL_WITHOUT_EXPECT")
internal expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase>

@Database(entities = [MovieDetailsEntity::class], version = 1, exportSchema = true)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}
