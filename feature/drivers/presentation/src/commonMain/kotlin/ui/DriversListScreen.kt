package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.github.ajalt.colormath.extensions.android.composecolor.toComposeColor
import com.github.ajalt.colormath.parse
import drivers.Driver
import org.koin.compose.koinInject
import ui.sharedComponents.ImageLoader
import ui.sharedComponents.VerticalGrid

@Composable
fun DriversListScreen(
    navigateToDriverDetailScreen: (Int) -> Unit
) {

    val driversViewModel = koinInject<DriversViewModel>()
    val state by driversViewModel.state.collectAsState()

    LaunchedEffect(Unit){
        driversViewModel.getDrivers()
    }

    DriversListScreenContent(
        state = state,
        navigateToDriverDetailScreen = navigateToDriverDetailScreen
    )

}

@Composable
fun DriversListScreenContent(
    state: DriversState,
    navigateToDriverDetailScreen: (Int) -> Unit
) {

    VerticalGrid(
        items = state.drivers
    ) { driver ->

        DriverItem(
            driver = driver,
            onClick = { driverNumber -> navigateToDriverDetailScreen(driverNumber) }
        )

    }

}


@Composable
fun DriverItem(
    modifier: Modifier = Modifier,
    driver: Driver,
    onClick: (Int) -> Unit
) {

    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
            .clickable {
                onClick(driver.driverNumber)
            },
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.Transparent
        )
    ) {

        ConstraintLayout(
            modifier = modifier.fillMaxSize()
        ) {

            val (left, right) = createRefs()

            Column(
                modifier = modifier
                    .fillMaxHeight()
                    .constrainAs(left) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start, margin = 16.dp)
                        bottom.linkTo(parent.bottom)
                    },
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                ImageLoader(
                    url = driver.driverNumberImage,
                    description = driver.fullName,
                    modifier = modifier
                        .height(80.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    VerticalDivider(
                        modifier = modifier
                            .height(45.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        thickness = 4.dp,
                        color = com.github.ajalt.colormath.Color.parse("#${driver.teamColour}")
                            .toComposeColor()
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = driver.firstName,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = driver.lastName,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                Text(
                    text = driver.teamName
                )

            }

            Column(
                modifier = modifier
                    .fillMaxHeight()
                    .constrainAs(right) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end, margin = 16.dp)
                        bottom.linkTo(parent.bottom)
                    },
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                ImageLoader(
                    url = driver.countryImageUrl,
                    description = driver.fullName,
                    modifier = modifier
                        .align(Alignment.End)
                        .size(40.dp)
                )

                ImageLoader(
                    url = driver.headshotImageUrl,
                    description = driver.fullName,
                    modifier = modifier
                        .size(160.dp)
                )

            }

        }


    }

}

