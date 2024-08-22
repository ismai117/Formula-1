import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.initKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Formula1KMP",
    ) {
//        window.minimumSize = Dimension(900, 800)
//        window.rootPane.putClientProperty("apple.awt.fullWindowContent", true)
//        window.rootPane.putClientProperty("apple.awt.transparentTitleBar", true)
//        window.rootPane.putClientProperty("apple.awt.windowTitleVisible", false)
        initKoin {}
        App()
    }
}