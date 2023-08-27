package ru.byum.muscat.ui.artistScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.key
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.byum.muscat.R
import ru.byum.muscat.data.Folder
import ru.byum.muscat.data.FolderType
import ru.byum.muscat.data.Release
import ru.byum.muscat.ui.ListFoldersMenu
import ru.byum.muscat.ui.Loader
import ru.byum.muscat.ui.RatingBar.RatingBar


@OptIn(ExperimentalMaterial3Api::class, InternalComposeApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArtistScreen(
    artistID: String,
    navController: NavHostController,
    viewModel: ArtistScreenViewModel = hiltViewModel(),
) {
    viewModel.init(artistID)

    val artist by viewModel.artist.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val listOfFolders by viewModel.listOfFolders.collectAsStateWithLifecycle()
    val foldersOfItem by viewModel.itemInFolders.collectAsStateWithLifecycle()

    if (loading) {
        Loader()
    }

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

            if (loading) {
                Loader()
            }

            Box() {
                Column() {
                    if (artist != null) {
                        Text(
                            text = "${artist?.name}",
                            color = Color(0, 12, 120), fontSize = 30.sp,
                            fontFamily = FontFamily.SansSerif
                        )

                        RatingBar(id = artistID.toInt(), rating = artist!!.rating,
                            onRatingChang =
                            { artistID, rating -> viewModel.setArtistRating(artistID, rating) }
                        )
                    }
                }
            }

            val releases by viewModel.releases.collectAsState()

            if (loading) {
                Loader()
            }

            ArtistReleasesList(
                releases,
                listOfFolders,
                foldersOfItem,
                onRatingChange = { id, rating ->
                    viewModel.setReleaseRating(id, rating)
                },
            )
        }
    }
}


@ExperimentalMaterial3Api
@Composable
fun ArtistReleasesList(
    releases: List<Release>,
    listOfFolders: List<Folder>,
    folderOfItem: List<Int>,
    onRatingChange: (id: Int, rating: Int) -> Unit,
) {
    val scrollState = rememberScrollState()

    val viewModel: ArtistScreenViewModel = hiltViewModel()

    if (releases.isEmpty()) {
        return
    }

    Column(
        modifier = Modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
    ) {
        releases.forEach {
            key(it.id) {
                Row(modifier = Modifier.padding(10.dp)) {
                    if (it.image != "") {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current).data(it.image)
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

                    Spacer(modifier = Modifier.width(20.dp))

                    Column(
                        modifier = Modifier.background(Color.Transparent)
                    ) {
                        Text(
                            text = "${it.artist}:\n${it.id}\n${it.title},\n${it.year}",
                            color = Color(0, 12, 120), fontSize = 30.sp,
                            fontFamily = FontFamily.SansSerif
                        )

                        RatingBar(it.id, it.rating, onRatingChange)

                        var isClicked by remember { mutableStateOf(false) }

                        IconButton(onClick = {
                            isClicked = true
                            viewModel.setAddToFolderRelease(it.id)
                        }) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = null
                            )
                        }

                        if (isClicked) {
                            ListFoldersMenu(
                                FolderType.RELEASES,
                                listOfFolders,
                                folderOfItem,
                                { folderID -> viewModel.addItemToFolder(folderID) },
                                { isClicked = false }
                            )
                        }
                    }
                }
            }
        }
    }
}

