import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import navigation.Navigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import theme.AppTheme
import ui.sharedComponents.setSingletonImageLoader

@Composable
@Preview
fun App(
    appComponent: AppComponent,
    disableDiskCache: Boolean = false
) = AppTheme {

    setSingletonImageLoader(disableDiskCache)

    val navController = rememberNavController()

    Box(modifier = Modifier.fillMaxSize()) {
        Navigation(
            appComponent = appComponent,
            navController = navController
        )
    }

}
