package com.example.netflix.domain.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netflix.domain.repository.MovieCategories
import com.example.netflix.domain.repository.MovieRepository
import com.example.netflix.domain.utils.Response
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val auth:FirebaseAuth,
    private val repository: MovieRepository
):ViewModel() {

    init {
        getMovies()
    }

   val movieCat by lazy { MutableStateFlow(MovieState(isLoading = true)) }


    fun signIn(email:String,pass:String) = auth.signInWithEmailAndPassword(email,pass)

    fun signUp(email: String,pass: String) = auth.createUserWithEmailAndPassword(email,pass)


    fun getMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTrending().collectLatest { res->
                when(res){
                   is Response.Success -> {
                       movieCat.value =  movieCat.value.copy(
                           data = res.data,
                           isLoading = false,
                           error = ""
                       )
                       Log.e("VM-data",res.data?.trending?.size.toString())
                   }
                    is Response.Loading -> {
                        movieCat.value =  movieCat.value.copy(
                            data = res.data,
                            isLoading = true,
                            error = ""
                        )
                    }
                    is Response.Error ->{
                        movieCat.value =  movieCat.value.copy(
                            data = res.data,
                            isLoading = false,
                            error = res.message
                        )
                        Log.e("VM-error",res.message)
                    }
                }
            }
        }
    }

}

sealed class UiState{
    data class ShowSnackBar(val message:String): UiState()
}

data class MovieState(
    val data : MovieCategories?=null,
    val isLoading: Boolean = false,
    val error:String = ""
)