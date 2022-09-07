package com.example.netflix.ui.screens


import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.netflix.R
import com.example.netflix.domain.viewmodels.RegisterViewModel
import com.example.netflix.ui.util.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.netflix_logo),
                contentDescription = "logo",
                modifier = Modifier.size(250.dp)
            )

            Spacer(modifier = Modifier.padding(30.dp))

            CircularProgressIndicator(color = Color.Red)
        }
    }

    LaunchedEffect(key1 = true) {
        val sharedPref = context.getSharedPreferences("Checkbox", Context.MODE_PRIVATE)
        val isNotFirstTime = sharedPref.getBoolean("remember", false)

        val route = if (isNotFirstTime) Screens.MainScreen.route else Screens.Register.route


        delay(1200L)

        navController.navigate(route) {
            popUpTo(0)
        }
    }
}