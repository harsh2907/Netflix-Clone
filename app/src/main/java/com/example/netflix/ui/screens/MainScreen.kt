package com.example.netflix.ui.screens


import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.netflix.R
import com.example.netflix.data_source.remote.dto.MovieResult
import com.example.netflix.domain.viewmodels.MovieState
import com.example.netflix.domain.viewmodels.RegisterViewModel
import com.example.netflix.ui.theme.QuickSand
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun MainScreen(viewModel:RegisterViewModel) {

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MainTopBar()
        }
    ) {
        MainScreenLayout(viewModel = viewModel)
    }
}

@Composable
fun MainScreenLayout(viewModel: RegisterViewModel) {

    val movieState = viewModel.movieCat.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        item {

            RowMovieItems(state = movieState, title = "Trending Now")

            Spacer(modifier = Modifier.padding(24.dp))

            RowMovieItems(state = movieState, title = "Top Rated")

            Spacer(modifier = Modifier.padding(24.dp))


            RowMovieItems(state = movieState, title = "Upcoming")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowMovieItems(
    state: MovieState,
    title: String
) {
    val context = LocalContext.current
    val list = ArrayList<MovieResult>()
    when (title) {
        "Trending Now" -> list.addAll(state.data?.trending ?: emptyList())
        "Top Rated" -> list.addAll(state.data?.topRated ?: emptyList())
        "Upcoming" -> list.addAll(state.data?.upComing ?: emptyList())
    }

    Text(
        text = title,
        color = Color.White,
        fontSize = 24.sp,
        modifier = Modifier.padding(horizontal = 12.dp),
        fontFamily = QuickSand,
        fontWeight = FontWeight.SemiBold
    )

    if(state.error.isNotBlank()) {
        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
    }

    AnimatedVisibility(visible = state.isLoading ) {
        Column(Modifier.fillMaxWidth()) {
            CircularProgressIndicator(
                color = Color.Red, modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }

    AnimatedVisibility(visible = !state.isLoading ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(list.shuffled()) { data ->
                AnimatedVisibility(visible = data.title.isNotBlank(),
                enter = fadeIn()+ slideInHorizontally()) {
                    Log.e("List", data.poster)

                        Card(
                            modifier = Modifier
                                .padding(12.dp)
                                .width(130.dp)
                                .height(200.dp)
                                .background(Color.Transparent)
                                .clickable {
                                    Toast
                                        .makeText(context, data.title, Toast.LENGTH_SHORT)
                                        .show()
                                },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Image(
                                painter = rememberImagePainter(data = data.poster) {
                                    placeholder(R.drawable.loading)
                                    error(R.drawable.error_image)
                                },
                                contentDescription = "poster",
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.FillHeight
                            )
                        }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Black
) {
    TopAppBar(
        title = {
            Row {
                Image(
                    painter = painterResource(R.drawable.netflix_logo),
                    contentDescription = null
                )
            }
        },
        backgroundColor = backgroundColor,
        actions = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Surface(
                    onClick = {  }
                ) {
                    Text(text = "PRIVACY", fontWeight = FontWeight.ExtraBold)
                }
                IconButton(
                    onClick = {  }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = ""
                    )
                }
            }
        },
        modifier = modifier
    )
}

