package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import starter.StarterRepository

class SplashPresenter(
    private val navigator: Navigator,
    private val starterRepository: StarterRepository
) : Presenter<SplashState> {

    @Composable
    override fun present(): SplashState {

        var isStarted by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(Unit){
            starterRepository.getStartedState()
                .collect { result ->
                    isStarted = result ?: false
                }
        }

        return SplashState(
            isStarted = isStarted
        ){ event ->
            when(event){
                SplashEvent.NavigateToStarterScreen -> {

                }
                SplashEvent.NavigateToMainScreen -> {

                }
            }
        }

    }

}