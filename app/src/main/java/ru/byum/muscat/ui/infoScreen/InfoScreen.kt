package ru.byum.muscat.ui.infoScreen

import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.time.format.TextStyle


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InfoScreen (modifier: Modifier = Modifier, navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Info Screen",
            color = Color.Green,
            style = androidx.compose.ui.text.TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier.padding(24.dp)//.clickable(onClick = {
                // this will navigate to second screen
                //navController.navigate("main")
            //})
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconButton(modifier = Modifier.width(96.dp), onClick = { navController.popBackStack()}) {
            Icon(Icons.Filled.ArrowBack, modifier = Modifier.size(128.dp), contentDescription = "Back")
        }
    }
}
