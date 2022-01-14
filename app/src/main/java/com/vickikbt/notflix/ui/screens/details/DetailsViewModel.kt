package com.vickikbt.notflix.ui.screens.details

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.vickikbt.domain.models.Cast
import com.vickikbt.domain.models.MovieDetails
import com.vickikbt.domain.models.MovieVideo
import com.vickikbt.domain.models.SimilarMovies

import com.vickikbt.repository.repository.movie_details_repository.MovieDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val movieDetailsRepository: MovieDetailsRepository
) : ViewModel() {

    private val _movieIsFavorite: MutableStateFlow<Boolean?> = MutableStateFlow(false)
    val movieIsFavorite: StateFlow<Boolean?> get() = _movieIsFavorite

    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails> get() = _movieDetails

    private val _movieCast = MutableLiveData<Cast>()
    val movieCast: LiveData<Cast> get() = _movieCast

    private val _movieVideo = MutableLiveData<MovieVideo>()
    val movieVideo: LiveData<MovieVideo> get() = _movieVideo

    private val _similarMovies = MutableLiveData<SimilarMovies>()
    val similarMovies: LiveData<SimilarMovies> get() = _similarMovies

    fun fetchMovieDetails(movieId: Int) = viewModelScope.launch {
        movieDetailsRepository.getMovieDetails(movieId).collect {
            _movieDetails.value = it
        }
    }

    fun fetchMovieCast(movieId: Int) = viewModelScope.launch {
        movieDetailsRepository.getMovieCast(movieId).collect {
            _movieCast.value = it
        }
    }

    fun fetchMovieVideo(movieId: Int) = viewModelScope.launch {
        movieDetailsRepository.getMovieVideos(movieId).collect {
            _movieVideo.value = it
        }
    }

    fun fetchSimilarMovies(movieId: Int) = viewModelScope.launch {
        movieDetailsRepository.fetchSimilarMovies(movieId).collect {
            _similarMovies.value = it
        }
    }

    fun getImagePalette(drawable: Drawable, onGenerated: (Palette.Swatch) -> Unit) {
        val bitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bitmap).generate { palette ->
            val vibrantSwatch = palette?.vibrantSwatch
            val dominantSwatch = palette?.vibrantSwatch

            if (vibrantSwatch != null) {
                onGenerated(vibrantSwatch)
            } else if (dominantSwatch != null) {
                onGenerated(dominantSwatch)
            }
        }
    }

    fun saveMovieDetails(movieDetails: MovieDetails, cast: Cast, movieVideo: MovieVideo?) =
        viewModelScope.launch {
            movieDetailsRepository.apply {
                saveMovieDetails(movieDetails)
                saveMovieCast(cast)
                if (movieVideo != null) {
                    saveMovieVideos(movieVideo)
                }
            }
        }

    fun updateFavorite(cacheId: Int, isFavorite: Boolean) = viewModelScope.launch {
        movieDetailsRepository.updateMovieIsFavorite(cacheId, isFavorite)
    }

    fun checkIfMovieIsFavorite(movieId: Int) = viewModelScope.launch {
        movieDetailsRepository.isMovieFavorite(movieId).collect {
            _movieIsFavorite.value = it
        }
    }

    fun logThis(something: Any) {
        Log.d("Det", "The value of favorite is $something")
    }
}
