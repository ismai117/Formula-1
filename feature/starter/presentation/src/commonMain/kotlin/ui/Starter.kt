package ui

import Type
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slack.circuit.runtime.ui.Ui
import formula1kmp.feature.starter.presentation.generated.resources.Res
import formula1kmp.feature.starter.presentation.generated.resources.bg
import formula1kmp.feature.starter.presentation.generated.resources.logo
import getPlatform
import org.jetbrains.compose.resources.painterResource

class Starter : Ui<StarterState> {

    @Composable
    override fun Content(state: StarterState, modifier: Modifier) {

        val hostState = remember { SnackbarHostState() }

        val platformType = getPlatform().type

        LaunchedEffect(state.status) {
            if (state.status) {
                state.eventSink(StarterEvent.NavigateToMainScreen)
            }
        }

        LaunchedEffect(state.message) {
            if (state.message.isNotBlank()){
                hostState.showSnackbar(state.message)
            }
        }

        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = hostState
                ) { data ->
                    Snackbar(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .testTag("snackBar")
                    ) {
                        Text(
                            text = data.visuals.message,
                            modifier = Modifier.testTag("snackBar_text")
                        )
                    }
                }
            }
        ) {

            if (platformType == Type.MOBILE) {

                Mobile(
                    state = state,
                    getStarted = { state.eventSink(StarterEvent.LoadData) }
                )

            } else {

                NonMobile(
                    state = state,
                    getStarted = { state.eventSink(StarterEvent.LoadData) }
                )

            }

        }

    }


    @Composable
    private fun Mobile(
        modifier: Modifier = Modifier,
        state: StarterState,
        getStarted: () -> Unit
    ) {

        Box(
            modifier = modifier
                .fillMaxSize()
                .testTag("mobile_layout")
        ) {

            BackgroundImage()

            HeaderContent()

            if (state.isLoading) {

                LoadingContent()

            } else {

                GetStartedButton(getStarted = getStarted)

            }

        }

    }

    @Composable
    private fun NonMobile(
        modifier: Modifier = Modifier,
        state: StarterState,
        getStarted: () -> Unit
    ) {

        Row(
            modifier = modifier
                .fillMaxSize()
                .testTag("nonMobile_layout"),
        ) {

            Box(
                modifier = modifier
                    .weight(0.35f)
                    .fillMaxHeight()
            ) {

                BackgroundImage()

            }

            Box(
                modifier = modifier
                    .weight(0.65f)
                    .fillMaxHeight()
            ) {

                HeaderContent()

                if (state.isLoading) {

                    LoadingContent()

                } else {

                    GetStartedButton(getStarted = getStarted)

                }

            }

        }

    }

    @Composable
    private fun BackgroundImage(
        modifier: Modifier = Modifier,
    ) {

        Image(
            painter = painterResource(Res.drawable.bg),
            contentDescription = "",
            modifier = modifier
                .fillMaxSize()
                .testTag("bgImage"),
            contentScale = ContentScale.Crop
        )

    }

    @Composable
    private fun BoxScope.HeaderContent(
        modifier: Modifier = Modifier,
    ) {

        Column(
            modifier = modifier
                .padding(top = 100.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = null,
                modifier = modifier
                    .width(160.dp)
                    .height(40.dp)
                    .testTag("logo")
            )
            Text(
                text = "2024",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = modifier.testTag("year")
            )
        }

    }

    @Composable
    private fun BoxScope.LoadingContent(
        modifier: Modifier = Modifier,
    ) {

        Box(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 40.dp)
                .fillMaxWidth()
                .height(55.dp)
                .align(Alignment.BottomCenter)
                .background(Color(0xffe80404)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Data Loading...",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                modifier = modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterStart)
                    .testTag("loading_text")
            )
            CircularProgressIndicator(
                color = Color.White,
                modifier = modifier
                    .padding(end = 16.dp)
                    .size(24.dp)
                    .align(Alignment.CenterEnd)
                    .testTag("loading_indicator")
            )
        }

    }

    @Composable
    private fun BoxScope.GetStartedButton(
        modifier: Modifier = Modifier,
        getStarted: () -> Unit,
    ) {

        Box(
            modifier = modifier
                .padding(start = 24.dp, end = 24.dp, bottom = 140.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = getStarted,
                modifier = modifier
                    .width(324.dp)
                    .height(55.dp)
                    .testTag("getStarted_btn"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffe80404)
                )
            ) {
                Text(
                    text = "Get Started",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
            }
        }

    }

}