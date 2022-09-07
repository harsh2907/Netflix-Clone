package com.example.netflix.domain.repository

import com.example.netflix.data_source.remote.dto.MovieResult
import com.example.netflix.data_source.remote.response.MovieResponse
import com.example.netflix.domain.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MovieRepository(
    private val api:MovieResponse
) {
    suspend fun getTrending():Flow<Response<MovieCategories>> = flow {
         emit(Response.Loading())
         try{

             val trendRes = api.getTrending()
             val topRes = api.getTopRated()
             val upComingRes = api.getUpcoming()

             val trending = trendRes.results.map { it.toResult() }
             val topRated = topRes.results.map { it.toResult() }
             val upComing = upComingRes.results.map { it.toResult() }

             val movieCat = MovieCategories(
                 trending = trending,
                 topRated = topRated,
                 upComing = upComing
             )

             emit(Response.Success(movieCat))
         }catch (e: HttpException){
             emit(Response.Error(message = "Oops, something went wrong"))
         }
         catch (e: IOException){
             emit(Response.Error(message = "Couldn't reach server check your internet connection"))
         }

     }


}

data class MovieCategories(
    val trending:List<MovieResult> = emptyList(),
    val topRated:List<MovieResult> = emptyList(),
    val upComing:List<MovieResult> = emptyList(),
    val diff:List<MovieResult> = emptyList(),
)