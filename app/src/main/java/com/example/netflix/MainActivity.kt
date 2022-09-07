package com.example.netflix

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.netflix.domain.viewmodels.RegisterViewModel
import com.example.netflix.ui.screens.MainScreen
import com.example.netflix.ui.screens.RegisterScreen
import com.example.netflix.ui.screens.SplashScreen
import com.example.netflix.ui.theme.NetflixTheme
import com.example.netflix.ui.theme.Roboto
import com.example.netflix.ui.util.Screens
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NetflixTheme {
                val viewModel: RegisterViewModel by viewModels()
                NavScreen(viewModel)
            }
        }
    }


    @Composable
    fun NavScreen(viewModel: RegisterViewModel) {
        val navController = rememberNavController()


        NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
            composable(route = Screens.SplashScreen.route){
                SplashScreen(navController = navController,viewModel)
            }
            composable(route = Screens.Register.route) {
                RegisterScreen(navController)
            }
            composable(route = Screens.MainScreen.route) {
                MainScreen(viewModel)
            }

        }
    }




}