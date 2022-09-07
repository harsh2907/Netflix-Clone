package com.example.netflix.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.netflix.R
import com.example.netflix.ui.theme.BlackTrans
import com.example.netflix.ui.theme.QuickSand
import com.example.netflix.ui.theme.Roboto
import com.example.netflix.ui.theme.TextFieldBack
import com.example.netflix.ui.util.Screens
import com.example.netflix.domain.viewmodels.RegisterViewModel
import com.example.netflix.domain.viewmodels.UiState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.Cookie


@Composable
fun RegisterScreen(
    navController: NavHostController
) {
    var isLogin by remember { mutableStateOf(true) }


    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {


        Image(
            painter = painterResource(id = R.drawable.net_bg),
            contentDescription = "netflix logo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )

        Image(
            painter = painterResource(id = R.drawable.netflix_logo),
            contentDescription = "netlogo",
            modifier = Modifier
                .size(100.dp)
                .padding(12.dp)
                .align(Alignment.TopStart)
        )



        AnimatedVisibility(
            visible = isLogin,
            enter = expandVertically()
        ) {
            LoginBox(
                navController = navController,
                onClick = { isLogin = !isLogin }
            )
        }
        AnimatedVisibility(
            visible = !isLogin,
            enter = expandVertically()
        ) {
            SignUpBox(
                navController = navController,
                onClick = { isLogin = !isLogin }
            )
        }

    }


}




