package com.vickikbt.notflix.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.vickikbt.notflix.R
import com.vickikbt.notflix.ui.theme.Gray
import com.vickikbt.notflix.ui.theme.Surface
import com.vickikbt.notflix.ui.theme.TextSecondary

@Composable
fun MovieRatingSection(popularity: String?, voteAverage: Float?, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(65.dp)
            .padding(bottom = 10.dp)
            .placeholder(
                visible = popularity.isNullOrEmpty(),
                color = Gray,
                highlight = PlaceholderHighlight.shimmer(highlightColor = TextSecondary)
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$popularity",
                style = MaterialTheme.typography.h6,
                fontSize = 32.sp,
            )
            Text(text = "Popularity", style = MaterialTheme.typography.h6, fontSize = 20.sp)
        }
        Spacer(modifier = modifier.width(15.dp))
        Divider(
            thickness = 2.dp,
            modifier = modifier
                .fillMaxHeight(0.7f)
                .width(2.dp),
            color = Surface
        )
        Spacer(modifier = modifier.width(15.dp))

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_rating_star),
                contentDescription = "rating star",
                modifier = modifier.size(40.dp)
            )
            Text(
                text = "$voteAverage/5.0",
                style = MaterialTheme.typography.h6,
                fontSize = 20.sp,
            )
        }
    }
}
