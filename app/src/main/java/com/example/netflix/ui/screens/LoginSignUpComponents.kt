package com.example.netflix.ui.screens

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpBox(
    onClick: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
    navController:NavHostController
) {

    Box(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .background(color = BlackTrans, RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {

        val context = LocalContext.current
        var isCheck by remember { mutableStateOf(false) }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val keyBoard = LocalSoftwareKeyboardController.current


        Column(Modifier.padding(horizontal = 40.dp, vertical = 30.dp)) {
            Text(
                text = "Sign Up",
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.Start),
                color = Color.White,
                fontSize = 36.sp,
                fontFamily = QuickSand,
                fontWeight = FontWeight.Bold
            )

            TextField(
                value = email,
                onValueChange = {
                    email = it
                }, label = {
                    Text(
                        text = "Email",
                        color = Color.White,
                        fontFamily = Roboto
                    )
                },
                modifier = Modifier
                    .padding(12.dp)
                    .background(TextFieldBack, RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = TextFieldBack,
                    cursorColor = Color.White,
                    placeholderColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.White
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyBoard?.hide()
                }),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
                maxLines = 2
            )
            TextField(
                value = password,
                onValueChange = {
                    password = it
                }, label = {
                    Text(
                        text = "Password",
                        color = Color.White,
                        fontFamily = Roboto
                    )
                },
                modifier = Modifier
                    .padding(12.dp)
                    .background(TextFieldBack, RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = TextFieldBack,
                    cursorColor = Color.White,
                    placeholderColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.White
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyBoard?.hide()
                }),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                maxLines = 1,
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = {
                    viewModel.signUp(email,password).addOnCompleteListener { task->
                        if(task.isSuccessful){

                            val sharedPref = context.getSharedPreferences("Checkbox",Context.MODE_PRIVATE)
                                with (sharedPref.edit()) {
                                    putBoolean("remember", isCheck)
                                    apply()
                                }


                            navController.navigate(Screens.MainScreen.route){
                                popUpTo(0)
                            }
                        }else{
                            Log.e("Signup","Exception : ${task.exception?.message}")
                            Toast.makeText(context, "${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                enabled = email.isNotBlank()
                        && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        && password.length > 5

            ) {
                Text(
                    "Sign up",
                    color = Color.White,
                    fontFamily = Roboto,
                    fontSize = 16.sp
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isCheck, onCheckedChange = {
                        isCheck = it
                    },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = Color.Red,
                        uncheckedColor = Color.White,
                        checkedColor = Color.White,
                    )
                )
                Text(
                    text = "Remember me",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontFamily = Roboto
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 12.dp)
            ) {

                Text(
                    text = "Already have a account?",
                    color = Color.Gray,
                    fontFamily = Roboto,
                    fontSize = 12.sp
                )
                TextButton(onClick = onClick) {
                    Text(
                        "Sign In",
                        fontFamily = Roboto,
                        color = Color.Red,
                        fontSize = 16.sp
                    )
                }

            }

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginBox(
    onClick: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .background(color = BlackTrans, RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {
        var isCheck by remember { mutableStateOf(false) }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val keyBoard = LocalSoftwareKeyboardController.current

        Column(Modifier.padding(horizontal = 40.dp, vertical = 30.dp)) {
            Text(
                text = "Sign In",
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.Start),
                color = Color.White,
                fontSize = 36.sp,
                fontFamily = QuickSand,
                fontWeight = FontWeight.Bold
            )



            TextField(
                value = email,
                onValueChange = {
                    email = it
                }, label = {
                    Text(text = "Email", color = Color.White, fontFamily = Roboto)
                },
                modifier = Modifier
                    .padding(12.dp)
                    .background(TextFieldBack, RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = TextFieldBack,
                    cursorColor = Color.White,
                    placeholderColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.White
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyBoard?.hide()
                }),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
                maxLines = 2
            )


            TextField(
                value = password,
                onValueChange = {
                    password = it
                }, label = {
                    Text(
                        text = "Password",
                        color = Color.White,
                        fontFamily = Roboto
                    )
                },
                modifier = Modifier
                    .padding(12.dp)
                    .background(TextFieldBack, RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = TextFieldBack,
                    cursorColor = Color.White,
                    placeholderColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.White
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyBoard?.hide()
                }),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                maxLines = 1,
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = {
                    viewModel.signIn(email,password).addOnCompleteListener { task->
                        if(task.isSuccessful){

                            val sharedPref = context.getSharedPreferences("Checkbox",Context.MODE_PRIVATE)
                            with (sharedPref.edit()) {
                                putBoolean("remember", isCheck)
                                apply()
                            }


                            navController.navigate(Screens.MainScreen.route){
                                popUpTo(0)
                            }
                        }else{

                            Log.e("Signup","Exception : ${task.exception?.message}")
                            Toast.makeText(context, "${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                enabled = email.isNotBlank()
                        && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        && password.length > 5
            ) {
                Text(
                    "Sign In",
                    color = Color.White,
                    fontFamily = Roboto,
                    fontSize = 16.sp
                )
            }

            //Remember Me Row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isCheck, onCheckedChange = {
                        isCheck = it
                    },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = Color.Red,
                        uncheckedColor = Color.White,
                        checkedColor = Color.White,
                    )
                )
                Text(
                    text = "Remember me",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontFamily = Roboto
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(12.dp)
            ) {

                Text(
                    text = "New to netflix ?",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontFamily = Roboto
                )
                TextButton(onClick = onClick) {
                    Text(
                        "Sign Up",
                        fontFamily = Roboto,
                        fontSize = 16.sp,
                        color = Color.Red
                    )
                }

            }
        }
    }
}

