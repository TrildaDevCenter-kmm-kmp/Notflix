package com.vickikbt.shared.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vickikbt.shared.presentation.ui.screens.home.HomeViewModel
import com.vickikbt.shared.ui.components.MovieCardLandscape
import com.vickikbt.shared.ui.components.MovieCardPager
import com.vickikbt.shared.ui.components.MovieCardPagerIndicator
import com.vickikbt.shared.ui.components.MovieCardPortraitCompact
import com.vickikbt.shared.ui.components.SectionSeparator
import com.vickikbt.shared.ui.theme.DarkPrimaryColor
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navigator: Navigator, viewModel: HomeViewModel = koinInject()) {
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = true) {
        viewModel.fetchNowPlayingMovies()
        viewModel.fetchTrendingMovies()
        viewModel.fetchUpcomingMovies()
        viewModel.fetchPopularMovies()
    }

    val homeUiState = viewModel.homeUiState.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        if (homeUiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (!homeUiState.error.isNullOrEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Error:\n${homeUiState.error}",
                textAlign = TextAlign.Center
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //region Now Playing Movies
                homeUiState.nowPlayingMovies?.let { nowPlayingMovies ->
                    val pagerState = rememberPagerState(pageCount = { nowPlayingMovies.size })

                    HorizontalPager(state = pagerState) { currentPage ->
                        MovieCardPager(
                            modifier = Modifier.fillMaxWidth().height(360.dp),
                            movie = nowPlayingMovies[currentPage]
                        ) { movie ->
                            navigator.navigate("/details/${movie.id}")
                        }
                    }

                    MovieCardPagerIndicator(
                        modifier = Modifier.padding(vertical = 6.dp),
                        pagerState = pagerState,
                        indicatorSize = 6.dp,
                        inactiveColor = Color.Gray,
                        activeColor = DarkPrimaryColor
                    )
                }
                //endregion

                //region Trending Movies
                homeUiState.trendingMovies?.let {
                    SectionSeparator(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        sectionTitle = "Trending Movies"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(items = it) { item ->
                            MovieCardPortraitCompact(
                                movie = item,
                                onItemClick = { movie ->
                                    navigator.navigate("/details/${movie.id}")
                                }
                            )
                        }
                    }
                }
                //endregion

                //region Upcoming Movies
                homeUiState.upcomingMovies?.let {
                    SectionSeparator(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 12.dp)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        sectionTitle = "Upcoming Movies"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.wrapContentHeight()
                    ) {
                        items(items = it) { item ->
                            MovieCardLandscape(
                                modifier = Modifier
                                    .width(300.dp)
                                    .height(245.dp),
                                movie = item,
                                onClickItem = { movie ->
                                    navigator.navigate("/details/${movie.id}")
                                }
                            )
                        }
                    }
                }
                //endregion

                //region Popular Movies
                homeUiState.popularMovies?.let {
                    Column(modifier = Modifier.padding(bottom = 90.dp)) {
                        SectionSeparator(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp, top = 12.dp)
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            sectionTitle = "Popular Movies"
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyRow(
                            modifier = Modifier.wrapContentHeight(),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            items(items = it) { item ->
                                MovieCardPortraitCompact(
                                    movie = item,
                                    onItemClick = { movie ->
                                        navigator.navigate("/details/${movie.id}")
                                    }
                                )
                            }
                        }
                    }
                }
                //endregion
            }
        }
    }
}