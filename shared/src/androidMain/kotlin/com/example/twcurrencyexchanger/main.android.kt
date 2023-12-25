import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.twcurrencyexchanger.naviagtion.navigationGraph
import com.example.twcurrencyexchanger.ui.themes.AppTheme
import com.example.twcurrencyexchanger.ui.themes.Theme
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent

@Composable
fun MainView(activity: ComponentActivity) {

    AppTheme {
        val odysseyConfiguration = OdysseyConfiguration(
            canvas = activity,
            backgroundColor = Theme.colors.primaryBackground
        )

        CompositionLocalProvider {
            setNavigationContent(odysseyConfiguration, onApplicationFinish = { activity.finishAffinity() }) {
                navigationGraph()
            }
        }
    }
}
