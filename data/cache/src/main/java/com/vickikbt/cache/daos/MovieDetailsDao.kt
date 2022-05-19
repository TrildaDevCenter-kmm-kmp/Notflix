package com.vickikbt.cache.daos

/*@Dao
interface MovieDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieDetails(movieDetails: MovieDetailsEntity)

    @Query("SELECT * FROM `Movie Details Table` WHERE id=:movieId")
    fun getMovieDetails(movieId: Int): Flow<MovieDetailsEntity>

    // TODO: setup WorkManager to delete movie details after 30 days
    @Query("DELETE FROM `Movie Details Table`")
    suspend fun deleteAllMovieDetails()

    @Query("SELECT COUNT(*) FROM `Movie Details Table` WHERE id=:movieId")
    suspend fun isMovieDetailsAvailable(movieId: Int): Int
}*/
