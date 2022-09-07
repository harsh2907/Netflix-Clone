package com.example.netflix.data_source.utils

object Constants {
    const val API_KEY = "d1ac5bd583c4a9a763726b8489ad2c9e"
    const val BASE_URL = "https://api.themoviedb.org"

    fun imageLink(link:String) = "https://image.tmdb.org/t/p/original$link"
}