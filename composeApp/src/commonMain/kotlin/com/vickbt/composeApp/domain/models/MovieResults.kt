package com.vickbt.composeApp.domain.models

data class MovieResults(

    val page: Int? = null,

    val movies: List<Movie>? = null,

    val totalPages: Int? = null,

    val totalResults: Int? = null
)