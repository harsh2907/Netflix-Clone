package com.example.netflix.ui.util

sealed class Screens(val route:String){
    object Register:Screens("Register")
    object MainScreen:Screens("MainScreen")
    object SplashScreen:Screens("SplashScreen")
}
