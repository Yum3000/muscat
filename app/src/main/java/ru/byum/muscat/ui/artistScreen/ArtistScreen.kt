package ru.byum.muscat.ui.artistScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.byum.muscat.R
import ru.byum.muscat.data.ArtistReleases
import ru.byum.muscat.data.ReleaseSearchResults
import ru.byum.muscat.ui.musicScreen.ArtistsList
import ru.byum.muscat.ui.musicScreen.MusicViewModel
import ru.byum.muscat.ui.musicScreen.RatingBar
import ru.byum.muscat.ui.musicScreen.ReleasesList
import ru.byum.muscat.ui.musicScreen.SearchType


@OptIn(ExperimentalMaterial3Api::class, InternalComposeApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArtistScreen(
    artistID: String,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ArtistScreenViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
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
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            viewModel.setNewArtistID(artistID)

            val rating by viewModel.rating.collectAsState()
            Text(text = "!!!! ${rating}")
            RatingBar(
                rating = rating,
                onRatingChanged = { newRating ->
                    viewModel.setRating(artistID, newRating)
                },
            )

            val results by viewModel.artistsReleases.collectAsState()
            if (results != null) {
                ArtistReleasesList(results = results)
            }
        }
    }
}


@Composable
fun ArtistReleasesList(results: ArtistReleases?) {
    val state = rememberScrollState()

    if (results == null) {
        return
    }

    Column(
        modifier = Modifier.verticalScroll(state),
        verticalArrangement = Arrangement.Center,
    ) {
        results.releases?.forEach {
            Row(modifier = Modifier.padding(10.dp)) {
                if (it.thumb != "" && it.thumb != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(it?.thumb)
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
                        text = "artist:${it?.artist}\n" + "id:${it?.id}, \n" +
                                "title:${it?.title},\n" + "year:${it?.year}",
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