package ru.byum.muscat.ui.SearchScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.SearchBar
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.byum.muscat.data.network.NetworkReleaseSearchResults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.byum.muscat.R
import ru.byum.muscat.data.FolderType
import ru.byum.muscat.data.network.NetworkArtistsSearchResults
import ru.byum.muscat.ui.ListFoldersMenu
import ru.byum.muscat.ui.Loader


@OptIn(ExperimentalMaterial3Api::class, InternalComposeApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
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
                                contentDescription = "Search"
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

        val loading by viewModel.loading.collectAsState()

        if (loading) {
            Loader()
        }

        Box(modifier = Modifier.padding(padding)) {
            if (searchType == SearchType.ARTIST) {
                val searchList by viewModel.artistsSearchResult.collectAsState()
                ArtistsList(FolderType.ARTIST, results = searchList, navController = navController)
            } else if (searchType == SearchType.RELEASE) {
                val searchList by viewModel.releasesSearchResult.collectAsState()
                ReleasesList(FolderType.RELEASES, results = searchList)
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ReleasesList(
    folderType: FolderType,
    viewModel: SearchViewModel = hiltViewModel(),
    results: NetworkReleaseSearchResults?
) {
    val state = rememberScrollState()

    val listOfFolders by viewModel.listOfFolders.collectAsStateWithLifecycle()
    val foldersOfItem by viewModel.itemInFolders.collectAsStateWithLifecycle()

    if (results == null) {
        return
    }

    Column(
        modifier = Modifier.verticalScroll(state),
        verticalArrangement = Arrangement.Center,
    ) {
        var foldersMenuOpened by remember { mutableStateOf(false) }

        if (foldersMenuOpened) {
            ListFoldersMenu(
                folderType,
                listOfFolders,
                foldersOfItem,
                {folderID -> viewModel.toggleReleaseInFolder(folderID) },
                {
                    foldersMenuOpened = false
                    viewModel.clearItemFolders()
                })
        }

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

                Spacer(modifier = Modifier.width(20.dp))

                Column(
                    modifier = Modifier.background(Color.Transparent)
                ) {
                    Text(
                        text = "${it?.title},\n" + "${it?.year}",
                        color = Color(0, 12, 120), fontSize = 30.sp,
                        fontFamily = FontFamily.SansSerif
                    )

                    IconButton(onClick = {
                        foldersMenuOpened = true
                        viewModel.setAddToFolderRelease(it.id!!)

                    }) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ArtistsList(
    folderType: FolderType,
    viewModel: SearchViewModel = hiltViewModel(),
    results: NetworkArtistsSearchResults?,
    navController: NavHostController
) {
    val state = rememberScrollState()

    var foldersMenuOpened by remember {mutableStateOf(false)}

    val loading by viewModel.loading.collectAsState()
    val listOfFolders by viewModel.listOfFolders.collectAsStateWithLifecycle()
    val foldersOfItem by viewModel.itemInFolders.collectAsStateWithLifecycle()

    if (results == null) {
        return
    }

    if(loading)
    {Loader()}

    Column(
        modifier = Modifier.verticalScroll(state),
        verticalArrangement = Arrangement.Center,
    ) {
        if (foldersMenuOpened) {
            ListFoldersMenu(
                folderType,
                listOfFolders,
                foldersOfItem,
                {folderID -> viewModel.toggleArtistInFolder(folderID) },
                {
                    foldersMenuOpened = false
                    viewModel.clearItemFolders()
                })
        }

        results.results?.forEach {

            Row(modifier = Modifier.padding(10.dp)) {
                if (it.thumb != "" && it.thumb != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(it.cover_image)
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
                
                Spacer(modifier = Modifier.width(20.dp))

                Column {
                    Text(
                        text = "${it.title},\n",
                        color = Color(0, 12, 120), fontSize = 30.sp,
                        fontFamily = FontFamily.SansSerif
                    )

                    Row {
                        IconButton(
                            onClick = { navController.navigate("artistScreen/${it.id}") }
                        ) {
                            Icon(Icons.Default.List, contentDescription = "artist page")
                        }

                        IconButton(onClick = {
                            foldersMenuOpened = true
                            viewModel.setAddToFolderArtist(it.id!!)
                        }) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}