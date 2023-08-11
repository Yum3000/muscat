package ru.byum.muscat.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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