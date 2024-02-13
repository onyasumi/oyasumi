import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import model.RequestModel
import ui.MainForm
import ui.MainFormVM

@Composable
@Preview
fun App() {
    MaterialTheme {

        val request = RequestModel()

        MainForm(MainFormVM(request))

    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, rememberWindowState(height = 900.dp, width = 1400.dp)) {
        App()
    }
}
