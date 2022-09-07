package com.example.netflix.data_source.remote.dto

import com.example.netflix.data_source.utils.Constants
import java.util.*

data class MovieResultDTO(
    val adult: Boolean,
    val backdrop_path: String?,
    val first_air_date: String?,
    val id: Int,
    val media_type: String?,
    val name: String?,
    val original_language: String?,
    val original_name: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
){
    fun toResult() = MovieResult(
            id.toString(),
            title = this.title ?: this.name?:"",
            overview = overview ?: "",
            Constants.imageLink(backdrop_path?:""),
            Constants.imageLink(poster_path?:""),
            release_date?:""
        )
}


data class MovieResult(
    val id:String,
    val title:String,
    val overview: String,
    val image:String,
    val poster:String,
    val releaseDate: String
)