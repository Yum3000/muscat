package ru.byum.muscat.ui.RatingBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

const val maxRating = 10

@Composable
fun RatingBar(
    id: Int,
    rating: Int,
    onRatingChang: (id: Int, newRating: Int) -> Unit,
    starsColor: Color = Color.Magenta,
) {
    var currentRating by remember { mutableStateOf(rating) }

    Row {
        for (i in 1..maxRating) {
            Icon(
                imageVector = if (i <= currentRating) Icons.Filled.Star else Icons.Outlined.Star,
                tint = if (i <= currentRating) starsColor else Color.Unspecified,
                contentDescription = "rating ${i}",
                modifier = Modifier
                    .clickable {
                        onRatingChang(id, i)
                        currentRating = i
                    }
                    .padding(2.dp)
            )
        }
    }
}