package ru.byum.muscat.ui.musicScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.byum.muscat.data.ReleaseSearchResults
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.res.painterResource
import ru.byum.muscat.R
import ru.byum.muscat.data.ArtistsSearchResults


@OptIn(ExperimentalMaterial3Api::class, InternalComposeApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MusicScreen(
    modifier: Modifier = Modifier,
    viewModel: MusicViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val searchString by viewModel.searchString.collectAsState()

    val released = state.year
    val title = state.title

    fun onGet() {
        viewModel.getRelease()
    }

    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    //val context = LocalContext.current
    Scaffold {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(36.dp)
            ) {
                IconButton(modifier = Modifier.width(96.dp),
                    onClick = { navController.navigate("main") }) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        modifier = Modifier.size(128.dp),
                        contentDescription = "Back"
                    )
                }

                SearchBar(
                    query = text,
                    onQueryChange = { text = it },
                    onSearch = {
                        viewModel.onSearchArtists(text)
                    },
                    active = false,
                    onActiveChange = { active = it },
                    placeholder = { Text(text = "Search HERE") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search!"
                        )
                    },
                    trailingIcon = {
                        if (text.isNotEmpty()) {
                            Icon(
                                modifier = Modifier.clickable { text = "" },
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Icon"
                            )
                        }
                    }) {}
            }

            //DisplayList(results = viewModel.listCurrentResults)
            DisplayListArtists(results = viewModel.listCurrentArtists)
        }
    }
}

@Composable
fun DisplayList(results: ReleaseSearchResults?) {
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }

    if (results != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .size(300.dp)
                .padding(horizontal = 8.dp)
                .verticalScroll(state),
            verticalArrangement = Arrangement.Center,
        ) {
            results.results?.forEach {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Transparent)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "id:${it?.id}, \n" + "title:${it?.title},\n" + "year:${it?.year}",
                        color = Color.Magenta,
                        fontSize = 30.sp
                    )

                    val coverImg: String = ""
                    if (it?.cover_image != "") {
                        AsyncImage(

                            model = ImageRequest.Builder(LocalContext.current).data(it?.cover_image)
                                .crossfade(true).build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,

                            modifier = Modifier
                                .height(100.dp)
                                .width(100.dp),
                            alignment = Alignment.BottomEnd
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.no_image),
                            contentDescription = ""
                        )
                    }

                    var rating by remember { mutableStateOf(3) }
                    RatingBar(
                        rating = rating,
                        onRatingChanged = { newRating ->
                            rating = newRating
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun DisplayListArtists(results: ArtistsSearchResults?) {
    val state = rememberScrollState()

    LaunchedEffect(Unit) { state.animateScrollTo(100) }

    if (results != null) {
        Column(
            modifier = Modifier
                .background(Color.Transparent)
//                .size(300.dp)
//                .padding(horizontal = 8.dp)
                .verticalScroll(state),
            verticalArrangement = Arrangement.Center,
        ) {
            results.results?.forEach {
                Row(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .padding(10.dp)
                ) {
                    if (it.thumb != "" && it.thumb != null) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current).data(it?.cover_image)
                                .crossfade(true).build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,

                            modifier = Modifier
                                .height(100.dp)
                                .width(100.dp),
                            alignment = Alignment.BottomEnd
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.no_image),
                            contentDescription = ""
                        )
                    }

                    Column(
                        modifier = Modifier
                            .background(Color.Transparent)
                        //.verticalScroll(state),
                        //verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "id:${it?.id}, \n" + "title:${it?.title},\n",// +"year:${it?.year}",
                            color = Color.Magenta, fontSize = 30.sp
                        )
                        var rating by remember { mutableStateOf(3) }
                        RatingBar(
                            rating = rating,
                            onRatingChanged = { newRating ->
                                rating = newRating
                            },
                        )

                    }

                }
            }
        }
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int = 0,
    maxRating: Int = 10,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
    onRatingChanged: (Int) -> Unit
) {
//    Row(modifier = modifier) {
//        Icon(
//            imageVector = Icons.Outlined.Star, contentDescription = null, tint = starsColor
//        )
//    }

    Row {
        for (i in 1..maxRating) {
            IconButton(onClick = { onRatingChanged(i) }) {
                Icon(Icons.Outlined.Star, contentDescription = null)
            }
        }
    }
}