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
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.navigation.compose.rememberNavController
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
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    val searchType by viewModel.searchType.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.width(96.dp),
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            modifier = Modifier.size(128.dp),
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    SearchBar(
                        query = text,
                        onQueryChange = { text = it },
                        onSearch = {
                            when (searchType) {
                                SearchType.ARTIST -> viewModel.searchArtists(text)
                                SearchType.RELEASE -> viewModel.searchReleases(text)
                            }
                        },
                        active = false,
                        onActiveChange = { active = it },
                        // ????
                        placeholder = { Text(text = "Search HERE", fontSize = 20.sp) },
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
                },
                actions = {
                    IconButton(
                        onClick = { showMenu = !showMenu }
                    ) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "menu"
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "Artist") },
                            onClick = { viewModel.setSearchArtist() },
                            trailingIcon = {
                                if (searchType == SearchType.ARTIST) {
                                    Icon(Icons.Default.Done, contentDescription = "selected")
                                }
                            }

                        )
                        DropdownMenuItem(
                            text = { Text(text = "Release") },
                            onClick = { viewModel.setSearchRelease() },
                            trailingIcon = {
                                if (searchType == SearchType.RELEASE) {
                                    Icon(Icons.Default.Done, contentDescription = "selected")
                                }
                            }
                        )
                    }
                }
            )
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (searchType == SearchType.ARTIST) {
                val searchList by viewModel.ArtistsSearchResult.collectAsState()
                ArtistsList(results = searchList, navController = navController)
            } else if (searchType == SearchType.RELEASE) {
                val searchList by viewModel.ReleasesSearchResult.collectAsState()
                ReleasesList(results = searchList)
            }
        }
    }
}

@Composable
fun ReleasesList(
    viewModel: MusicViewModel = hiltViewModel(),
    results: ReleaseSearchResults?
) {
    val state = rememberScrollState()

    if (results == null) {
        return
    }

    Column(
        modifier = Modifier.verticalScroll(state),
        verticalArrangement = Arrangement.Center,
    ) {
        results.results?.forEach {
            Row(modifier = Modifier.padding(10.dp)) {
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
                    modifier = Modifier.background(Color.Transparent)
                ) {
                    Text(
                        text = "id:${it?.id}, \n" + "title:${it?.title},\n" + "year:${it?.year}",
                        color = Color.Magenta, fontSize = 30.sp,
                        fontFamily = FontFamily.SansSerif
                    )

                    var currentRating by remember { mutableStateOf(1) }
                    RatingBar(
                        rating = currentRating,
                        onRatingChanged = { newRating -> currentRating = newRating },
                    )
                }
            }
        }
    }
}

@Composable
fun ArtistsList(
    viewModel: MusicViewModel = hiltViewModel(),
    results: ArtistsSearchResults?,
    navController: NavHostController
) {
    val state = rememberScrollState()

    if (results == null) {
        return
    }

    Column(
        modifier = Modifier.verticalScroll(state),
        verticalArrangement = Arrangement.Center,
    ) {
        results.results?.forEach {
            Row(modifier = Modifier.padding(10.dp)) {
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
                        painter = painterResource(id = R.drawable.music_note),
                        contentDescription = ""
                    )
                }

                Column {
                    Text(
                        text = "id:${it?.id}, \n" + "title:${it?.title},\n",
                        color = Color.Magenta, fontSize = 30.sp,
                        fontFamily = FontFamily.SansSerif
                    )

                    IconButton(
                        onClick = { navController.navigate("artistScreen/${it?.id}") }
                    ) {
                        Icon(Icons.Default.List, contentDescription = "artist page")
                    }

                    var currentRating by remember { mutableStateOf(1) }

                    RatingBar(
                        rating = currentRating,
                        onRatingChanged = { newRating -> currentRating = newRating },
                    )
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
    starsColor: Color = Color.Magenta,
    onRatingChanged: (Int) -> Unit
) {
    Row {
        for (i in 1..maxRating) {
            Icon(
                imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                tint = if (i <= rating) starsColor else Color.Unspecified,
                contentDescription = "rating ${i}",
                modifier = Modifier
                    .clickable { onRatingChanged(i) }
                    .padding(2.dp)
            )
        }
    }
}