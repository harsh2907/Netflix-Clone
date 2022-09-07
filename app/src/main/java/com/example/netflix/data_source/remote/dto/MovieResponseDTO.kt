package com.example.netflix.data_source.remote.dto

data class MovieResponseDTO(
    val page: Int,
    val results: List<MovieResultDTO>,
    val total_pages: Int,
    val total_results: Int
)


