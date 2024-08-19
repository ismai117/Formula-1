package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.slack.circuit.runtime.ui.Ui
import formula1kmp.app.generated.resources.Res
import formula1kmp.app.generated.resources.logo
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

class Splash : Ui<SplashState> {

    @Composable
    override fun Content(state: SplashState, modifier: Modifier) {


        LaunchedEffect(state.isStarted){
            delay(1200)
            if (state.isStarted){
                state.eventSink(SplashEvent.NavigateToMainScreen)
            } else {
                state.eventSink(SplashEvent.NavigateToStarterScreen)
            }
        }

        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = null,
                modifier = modifier
                    .width(160.dp)
                    .height(40.dp)
                    .testTag("logo")
            )

        }

    }

}