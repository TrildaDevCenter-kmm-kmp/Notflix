package com.vickikbt.notflix.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import com.vickikbt.domain.models.Movie
import com.vickikbt.domain.models.SimilarMovies
import com.vickikbt.notflix.ui.theme.Golden
import com.vickikbt.notflix.ui.theme.Gray
import com.vickikbt.notflix.ui.theme.TextSecondary
import com.vickikbt.notflix.util.getRating

@Composable
fun SimilarMoviesSection(similarMovies: SimilarMovies?, modifier: Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (textSimilarMovies, movies) = createRefs()

        Text(
            text = "Similar Movies",
            style = MaterialTheme.typography.h6,
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(textSimilarMovies) {
                top.linkTo(parent.top)
                start.linkTo(parent.start, margin = 10.dp)
            }
        )

        LazyRow(
            modifier = Modifier
                .constrainAs(movies) {
                    top.linkTo(textSimilarMovies.bottom)
                    start.linkTo(parent.start, margin = 10.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                }
                .placeholder(
                    visible = similarMovies == null,
                    color = Gray,
                    highlight = PlaceholderHighlight.shimmer(highlightColor = TextSecondary)
                ),
            contentPadding = PaddingValues(all = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (similarMovies?.movies != null) {
                items(similarMovies.movies!!) { movie ->
                    SimilarMovieItem(movie = movie)
                }
            }
        }
    }
}

@Composable
fun SimilarMovieItem(movie: Movie) {
    ConstraintLayout(
        modifier = Modifier
            .wrapContentWidth(align = Alignment.Start)
            .wrapContentHeight()
    ) {
        val painter =
            rememberImagePainter(data = "https://image.tmdb.org/t/p/w500${movie.backdropPath}")
        val (imageCard, movieName, rating) = createRefs()
        // movie image
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(imageCard) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            shape = RoundedCornerShape(4.dp)
        ) {
            Image(
                modifier = Modifier
                    .height(210.dp)
                    .width(150.dp)
                ,
                painter = painter,
                contentDescription = "similar movie image",
                contentScale = ContentScale.Crop
            )
        }

        // movie name
        Text(
            text = "${movie.title}", style = MaterialTheme.typography.h5.copy(fontSize = 14.sp),
            modifier = Modifier.constrainAs(movieName) {
                top.linkTo(imageCard.bottom)
                start.linkTo(parent.start)
                end.linkTo(imageCard.end)
                width = Dimension.fillToConstraints
            },
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            maxLines = 1,
        )

        // rating bar
        RatingBar(
            modifier = Modifier.constrainAs(rating) {
                top.linkTo(movieName.bottom)
                start.linkTo(parent.start)
                end.linkTo(imageCard.end)
                width = Dimension.fillToConstraints
            },
            value = movie.voteAverage?.getRating() ?: 0f,
            numStars = 5,
            size = 15.dp,
            stepSize = StepSize.HALF,
            isIndicator = true,
            ratingBarStyle = RatingBarStyle.Normal,
            activeColor = Golden,
            inactiveColor = Gray,
            onValueChange = {},
            onRatingChanged = {}
        )
    }
}
