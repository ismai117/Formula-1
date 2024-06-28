package main.teams.presentation


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.github.ajalt.colormath.extensions.android.composecolor.toComposeColor
import com.github.ajalt.colormath.parse
import getPlatform
import main.teams.domain.model.Team
import org.koin.compose.koinInject


@Composable
fun TeamsListScreen(
    navigateToTeamDetailScreen: (String) -> Unit
) {

    val teamsViewModel = koinInject<TeamsViewModel>()
    val state by teamsViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        teamsViewModel.getTeams()
    }

    TeamsListScreenContent(
        state = state,
        navigateToTeamDetailScreen = navigateToTeamDetailScreen
    )

}

@Composable
fun TeamsListScreenContent(
    modifier: Modifier = Modifier,
    state: TeamsState,
    navigateToTeamDetailScreen: (String) -> Unit
) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        Box(
            modifier = modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = "Teams",
                style = TextStyle(
                    color = Color(0xffe80404),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = modifier
                    .padding(start = 24.dp)
                    .align(Alignment.CenterStart)
            )

            Text(
                text = "2024",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = modifier
                    .padding(end = 24.dp)
                    .align(Alignment.CenterEnd)
            )

        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(324.dp),
            modifier = modifier
                .padding(top = 12.dp),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(
                items = state.teams,
                key = { team -> team.name.hashCode() }
            ) { item ->

                TeamItem(
                    team = item,
                    onClick = {
                        navigateToTeamDetailScreen(it)
                    }
                )

            }

        }

    }

}

@Composable
fun TeamItem(
    modifier: Modifier = Modifier,
    team: Team,
    onClick: (String) -> Unit
) {

    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .height(if (getPlatform().name == "desktop") 252.dp else 240.dp)
            .clickable {
                onClick(team.name)
            },
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.Transparent
        )
    ) {

        Column(
            modifier = modifier
                .fillMaxSize()
        ) {

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(0.50f)
//                    .border(width = 1.dp, color = Color.Black)
            ) {

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(0.60f)
//                        .border(width = 1.dp, color = Color.Black)
                ) {

                    Box(
                        modifier = modifier
                            .weight(1f)
                            .fillMaxHeight()
//                            .border(width = 1.dp, color = Color.Black)
                    ) {

                        Row(
                            modifier = modifier.align(Alignment.CenterStart),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            VerticalDivider(
                                modifier = modifier
                                    .padding(start = 12.dp)
                                    .height(45.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                                thickness = 4.dp,
                                color = com.github.ajalt.colormath.Color.parse("#${team.teamColour}")
                                    .toComposeColor()
                            )

                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = team.name,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                    }

                    Box(
                        modifier = modifier
                            .weight(1f)
                            .fillMaxHeight()
//                            .border(width = 1.dp, color = Color.Black)
                    ) {

                        Box(
                            modifier = modifier
                                .padding(end = 16.dp)
                                .size(33.dp)
                                .align(Alignment.CenterEnd),
//                                .border(width = 1.dp, color = Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = team.logoUrl,
                                contentDescription = team.name,
                                contentScale = ContentScale.Fit,
                                modifier = modifier.fillMaxSize()
                            )
                        }

                    }


                }

                HorizontalDivider(
                    modifier = modifier.fillMaxWidth()
                )

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(0.40f)
//                        .border(width = 1.dp, color = Color.Black)
                ) {

                    team.drivers.forEachIndexed { index, driver ->

                        Box(
                            modifier = modifier
                                .weight(1f)
                                .fillMaxHeight()
//                                .border(width = 1.dp, color = Color.Black)
                        ) {

                            if (team.drivers.size > 2) {
                                Text(
                                    text = driver.fullName,
                                    style = TextStyle(
                                        fontSize = 12.sp
                                    ),
                                    modifier = modifier
                                        .align(Alignment.Center)
//                                        .border(width = 1.dp, color = Color.Black)
                                )
                            } else {
                                Text(
                                    text = driver.fullName,
                                    style = TextStyle(
                                        fontSize = 14.sp
                                    ),
                                    modifier = modifier
                                        .padding(start = 12.dp)
                                        .align(Alignment.CenterStart)
//                                        .border(width = 1.dp, color = Color.Black)
                                )
                            }

                        }

                        if (team.drivers.size > 1) {
                            VerticalDivider(
                                modifier = Modifier.fillMaxHeight()
                            )
                        } else {
                            if (index < team.drivers.size - 1) {
                                VerticalDivider(
                                    modifier = Modifier.fillMaxHeight()
                                )
                            }
                        }

                    }

                }

            }

            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(0.50f),
//                    .border(width = 1.dp, color = Color.Black),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = team.carImageUrl,
                    contentDescription = team.name,
                    contentScale = ContentScale.Fit,
                    modifier = modifier
                        .padding(16.dp)
                        .fillMaxSize()
                )
            }

        }

    }

}

