package ui.main.horizontalPager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import getPlatform
import kotlinx.coroutines.launch
import ui.DriversList
import ui.DriversListScreen
import ui.TeamsListScreen
import ui.sharedComponents.Header

@Composable
fun HorizontalPagerScreen(
    navigateToDriverDetailScreen: (Int) -> Unit,
    navigateToTeamDetailScreen: (String) -> Unit
) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0) { 2 }
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }

    HorizontalPagerScreenContent(
        pagerState = pagerState,
        selectedTabIndex = selectedTabIndex,
        selectedTabIndexOnChange = { index ->
            selectedTabIndex = index
            scope.launch { pagerState.animateScrollToPage(index) }
        },
        navigateToDriverDetailScreen = navigateToDriverDetailScreen,
        navigateToTeamDetailScreen = navigateToTeamDetailScreen
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerScreenContent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    selectedTabIndex: Int,
    selectedTabIndexOnChange: (Int) -> Unit,
    navigateToDriverDetailScreen: (Int) -> Unit,
    navigateToTeamDetailScreen: (String) -> Unit
) {

    Scaffold { paddingValues ->

        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(top = if (getPlatform().type == Type.NON_MOBILE) 24.dp else 0.dp)
                .fillMaxSize()
        ) {

            when(selectedTabIndex){
                0 -> {
                    Header(
                        leadingText = "Drivers",
                        leadingTextStyle = TextStyle(
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        trailingText = "2024",
                        trailingTextStyle = TextStyle(
                            color = Color(0xffe80404),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                1 -> {
                    Header(
                        leadingText = "Teams",
                        leadingTextStyle = TextStyle(
                            color = Color(0xffe80404),
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        trailingText = "2024",
                        trailingTextStyle = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    0 -> { DriversList()
                        DriversListScreen(navigateToDriverDetailScreen = navigateToDriverDetailScreen)
                    }

                    1 -> {
                        TeamsListScreen(navigateToTeamDetailScreen = navigateToTeamDetailScreen)
                    }
                }
            }

            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = modifier
                    .fillMaxWidth(),
                indicator = {}
            ) {
                screens.forEach { item ->
                    Tab(
                        selected = item.index == selectedTabIndex,
                        onClick = { selectedTabIndexOnChange(item.index) },
                        text = {
                            Text(
                                text = item.label,
                                style = TextStyle(
                                    color = if (item.index == selectedTabIndex) Color.White else Color.Black,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        },
                        modifier = if (item.index == selectedTabIndex) {
                            modifier
                                .height(70.dp)
                                .background(
                                    color = Color(0xffe80404),
                                    shape = RoundedCornerShape(
                                        topStart = 24.dp,
                                        topEnd = 24.dp
                                    )
                                )
                        } else {
                            modifier
                                .height(70.dp)
                                .background(
                                    color = Color.Transparent
                                )
                        }
                    )
                }
            }

        }
    }

}

private sealed class TabItem(
    val index: Int,
    val label: String,
) {
    data object DriversListScreen : TabItem(
        index = 0,
        label = "Drivers",
    )

    data object TeamsListScreen : TabItem(
        index = 1,
        label = "Teams",
    )
}

private val screens = listOf(
    TabItem.DriversListScreen,
    TabItem.TeamsListScreen
)