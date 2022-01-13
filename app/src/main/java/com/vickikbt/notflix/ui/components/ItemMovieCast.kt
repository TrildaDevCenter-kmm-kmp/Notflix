package com.vickikbt.notflix.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.vickikbt.domain.models.Cast
import com.vickikbt.notflix.ui.theme.DarkTextSecondary
import com.vickikbt.notflix.ui.theme.Gray
import com.vickikbt.notflix.ui.theme.TextSecondary

@Composable
fun CastSection(modifier: Modifier, cast: Cast?) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (textCast, actors) = createRefs()

        Text(
            text = "Cast",
            style = MaterialTheme.typography.h6,
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(textCast) {
                top.linkTo(parent.top)
                start.linkTo(parent.start, margin = 10.dp)
            }
        )

        LazyRow(
            modifier = Modifier
                .constrainAs(actors) {
                    top.linkTo(textCast.bottom)
                    start.linkTo(parent.start, margin = 10.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                }
                .placeholder(
                    visible = cast == null,
                    color = Gray,
                    highlight = PlaceholderHighlight.shimmer(highlightColor = TextSecondary)
                ),
            contentPadding = PaddingValues(all = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (cast?.actor != null) {
                items(cast.actor!!) { actor ->
                    ItemMovieCast(
                        imageUrl = actor.profilePath!!,
                        name = actor.name!!,
                        role = actor.character!!,
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@Composable
fun ItemMovieCast(imageUrl: String?, name: String, role: String, modifier: Modifier) {
    ConstraintLayout(
        modifier = modifier
            .wrapContentHeight()
            .width(80.dp)
    ) {
        val (actorImage, actorName, actorRole) = createRefs()

        val painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w500$imageUrl") {
            crossfade(true)
            transformations(CircleCropTransformation())
        }

        Image(
            painter = painter, contentDescription = "actor image",
            modifier = Modifier
                .size(80.dp)
                .constrainAs(actorImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Text(
            text = name, style = MaterialTheme.typography.h6.copy(fontSize = 16.sp),
            modifier = Modifier.constrainAs(actorName) {
                top.linkTo(actorImage.bottom, margin = 10.dp)
            },
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            maxLines = 1
        )

        Text(
            text = role, style = MaterialTheme.typography.h4.copy(fontSize = 12.sp, color = TextSecondary),
            modifier = Modifier.constrainAs(actorRole) {
                top.linkTo(actorName.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            maxLines = 1,
            color = DarkTextSecondary
        )
    }
}
