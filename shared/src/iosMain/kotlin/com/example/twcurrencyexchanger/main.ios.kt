import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import com.example.twcurrencyexchanger.naviagtion.navigationGraph
import com.example.twcurrencyexchanger.ui.themes.AppTheme
import com.example.twcurrencyexchanger.ui.themes.Theme
import platform.UIKit.UIViewController
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent

fun MainViewController(): UIViewController = ComposeUIViewController {

    AppTheme {
        val backgroundColor = Theme.colors.primaryBackground
        val odysseyConfiguration = OdysseyConfiguration(backgroundColor = backgroundColor)

        CompositionLocalProvider {
            Column {
                Box(modifier = Modifier.fillMaxWidth().height(30.dp).background(backgroundColor))
                setNavigationContent(odysseyConfiguration) { navigationGraph() }
                Box(modifier = Modifier.fillMaxWidth().height(30.dp).background(backgroundColor))
            }
        }
    }
}