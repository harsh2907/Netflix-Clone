package com.example.netflix.data_source.remote.response

import com.example.netflix.data_source.remote.dto.MovieResponseDTO
import com.example.netflix.data_source.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieResponse {

    @GET("/3/trending/all/day")
  suspend fun getTrending(
        @Query("api_key")
        apiKey:String = Constants.API_KEY
    ):MovieResponseDTO

  @GET("/3/movie/top_rated")
  suspend fun getTopRated(
      @Query("api_key")
      apiKey:String = Constants.API_KEY,
      @Query("language")
      lang:String = "en-US"
  ):MovieResponseDTO

    @GET("/3/movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key")
        apiKey:String = Constants.API_KEY,
        @Query("language")
        lang:String = "en-US"
    ):MovieResponseDTO

}