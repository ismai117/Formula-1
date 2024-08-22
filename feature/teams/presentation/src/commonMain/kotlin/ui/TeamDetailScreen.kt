package ui

import Type
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import getPlatform
import org.koin.compose.koinInject
import teams.Team
import ui.sharedComponents.parallaxLayoutModifier
import ui.sharedComponents.ImageLoader

@Composable
fun TeamDetailScreen(
    teamName: String,
    navigateBack: () -> Unit
) {

    val teamsViewModel = koinInject<TeamsViewModel>()
    val team by teamsViewModel.team.collectAsState()

    LaunchedEffect(Unit) {
        teamsViewModel.getTeamByTeamName(teamName)
    }

    TeamDetailScreenContent(
        team = team,
        navigateBack = navigateBack
    )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun TeamDetailScreenContent(
    modifier: Modifier = Modifier,
    platformType: Type = getPlatform().type,
    team: Team?,
    navigateBack: () -> Unit
) {

    val windowSizeClass = calculateWindowSizeClass()
    val isCompacted = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = team?.name.orEmpty(),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigateBack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = null,
                            modifier = modifier.size(32.dp)
                        )
                    }
                },
                actions = {
                    ImageLoader(
                        url = team?.logoUrl.orEmpty(),
                        description = team?.name.orEmpty(),
                        modifier = modifier
                            .padding(end = 16.dp)
                            .size(33.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->

        Box(
            modifier = modifier
                .padding(paddingValues)
                .padding(top = if (getPlatform().type == Type.NON_MOBILE) 24.dp else 0.dp)
                .fillMaxSize()
        ) {

            if (platformType == Type.MOBILE) {

                Mobile(team = team)

            } else {

               if (isCompacted){

                   Mobile(team = team)

               } else {

                   NonMobile(team = team)

               }

            }


        }

    }


}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Mobile(
    modifier: Modifier = Modifier,
    team: Team?
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {

        FlowRow(
            modifier = modifier
                .parallaxLayoutModifier(scrollState, 2),
            maxItemsInEachRow = 2
        ) {

            team?.drivers?.forEach { driver ->
                Column(
                    modifier = modifier
                        .height(340.dp)
                        .weight(1f)
//                    .border(width = 1.dp, color = Color.Black)
                ) {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(0.70f)
//                        .border(width = 1.dp, color = Color.Black)
                    ) {

                        ImageLoader(
                            url = driver.profileImageUrl,
                            description = driver.fullName,
                            modifier = modifier.fillMaxSize()
                        )

                    }
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(0.30f)
                    ) {

                        Column(
                            modifier = modifier
                                .padding(start = 12.dp)
                                .align(Alignment.CenterStart),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Text(
                                text = "${driver.driverNumber}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            Text(
                                text = driver.fullName,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )

                        }

                    }
                    HorizontalDivider(
                        modifier = modifier.fillMaxWidth()
                    )
                }
            }

        }

        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {

            Column(
                modifier = modifier
                    .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 40.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Full Team Name",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = team?.fullName.orEmpty(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Base",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = team?.base.orEmpty(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Team Chief",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = team?.teamChief.orEmpty(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Technical Chief",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = team?.technicalChief.orEmpty(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Chassis",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = team?.chassis.orEmpty(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Power Unit",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = "${team?.powerUnit}",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }


                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "First Team Entry",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = "${team?.firstTeamEntry}",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "World Championships",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = "${team?.worldChampionships}",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Highest Race Finish",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = team?.highestRaceFinish.orEmpty(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Pole Positions",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = "${team?.polePositions}",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Fastest Laps",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = "${team?.fastestLaps}",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

            }

        }

    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NonMobile(
    modifier: Modifier = Modifier,
    team: Team?
) {

    val scrollState = rememberScrollState()

    Row(
        modifier = modifier.fillMaxSize()
    ) {

        FlowRow(
            modifier = modifier.width(340.dp),
            maxItemsInEachRow = 2
        ) {

            team?.drivers?.forEach { driver ->
                Column(
                    modifier = modifier
                        .height(340.dp)
                        .weight(1f)
//                    .border(width = 1.dp, color = Color.Black)
                ) {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(0.70f)
//                        .border(width = 1.dp, color = Color.Black)
                    ) {

                        ImageLoader(
                            url = driver.profileImageUrl,
                            description = driver.fullName,
                            modifier = modifier.fillMaxSize()
                        )

                    }
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(0.30f)
                    ) {

                        Column(
                            modifier = modifier
                                .padding(start = 12.dp)
                                .align(Alignment.CenterStart),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Text(
                                text = "${driver.driverNumber}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            Text(
                                text = driver.fullName,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )

                        }

                    }
                    HorizontalDivider(
                        modifier = modifier.fillMaxWidth()
                    )
                }
            }

        }

        Box(
            modifier = modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Column(
                modifier = modifier
                    .padding(24.dp)
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Full Team Name",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = team?.fullName.orEmpty(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Base",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = team?.base.orEmpty(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Team Chief",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = team?.teamChief.orEmpty(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Technical Chief",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = team?.technicalChief.orEmpty(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Chassis",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = team?.chassis.orEmpty(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Power Unit",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = "${team?.powerUnit}",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }


                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "First Team Entry",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = "${team?.firstTeamEntry}",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "World Championships",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = "${team?.worldChampionships}",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Highest Race Finish",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = team?.highestRaceFinish.orEmpty(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Pole Positions",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = "${team?.polePositions}",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Fastest Laps",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = "${team?.fastestLaps}",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                }

            }
        }

    }

}


