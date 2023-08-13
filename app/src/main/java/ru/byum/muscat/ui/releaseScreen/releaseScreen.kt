package ru.byum.muscat.ui.releaseScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import ru.byum.muscat.ui.Loader
import ru.byum.muscat.ui.RatingBar.RatingBar


@OptIn(ExperimentalMaterial3Api::class, InternalComposeApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReleaseScreen(
    id: String,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ReleaseScreenViewModel = hiltViewModel()
) {

    viewModel.init(id.toInt())

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
        Column(
            modifier = Modifier.padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


                val release by viewModel.release.collectAsState()
                if (release != null) {
                    if (release?.image != "") {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current).data(release?.image)
                                .crossfade(true).build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,

                            modifier = Modifier
                                .height(250.dp)
                                .width(250.dp),
                            alignment = Alignment.TopCenter
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.no_image),
                            contentDescription = ""
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "${release?.artist}\n\n" +
                                    "${release?.title},\n" + "${release?.year}",
                            color = Color(0, 12, 120), fontSize = 30.sp,
                            fontFamily = FontFamily.SansSerif
                        )

                        //RatingBar(release!!.id)

                }

        }
    }
}