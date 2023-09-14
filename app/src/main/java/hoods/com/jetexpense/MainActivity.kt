package hoods.com.jetexpense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hoods.com.jetexpense.presentation.components.JetExpAppBar
import hoods.com.jetexpense.presentation.components.JetExpBottomBar
import hoods.com.jetexpense.presentation.expense.ExpenseViewModel
import hoods.com.jetexpense.presentation.home.HomeViewModel
import hoods.com.jetexpense.presentation.income.IncomeViewModel
import hoods.com.jetexpense.presentation.navigation.ExpenseDestination
import hoods.com.jetexpense.presentation.navigation.HomeDestination
import hoods.com.jetexpense.presentation.navigation.IncomeDestination
import hoods.com.jetexpense.presentation.navigation.JetExpNavHost
import hoods.com.jetexpense.presentation.navigation.JetExpenseDestination
import hoods.com.jetexpense.presentation.navigation.TransactionDestination
import hoods.com.jetexpense.presentation.navigation.navigateToSingleTop
import hoods.com.jetexpense.presentation.transaction.TransactionAssistedFactory
import hoods.com.jetexpense.presentation.ui.theme.JetExpenseTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var assistedFactory: TransactionAssistedFactory
    private val allScreens = listOf(IncomeDestination, HomeDestination, ExpenseDestination)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            JetExpApp()
        }
    }

    @Composable
    private fun JetExpApp() {
        val homeViewModel: HomeViewModel = viewModel()
        val expenseViewModel: ExpenseViewModel = viewModel()
        val incomeViewModel: IncomeViewModel = viewModel()
        val systemTheme = isSystemInDarkTheme()
        var currentTheme by remember { mutableStateOf(if (systemTheme) Theme.SYSTEM else Theme.LIGHT) }
        CompositionLocalProvider(LocalAppTheme provides currentTheme) {
            JetExpenseTheme(currentTheme == Theme.DARK) {
                val navController = rememberNavController()
                val currentScreen = rememberCurrentScreen(navController)
                val icon = when (currentTheme) {
                    Theme.DARK -> R.drawable.ic_switch_on
                    else -> R.drawable.ic_switch_off
                }
                Surface(
                    modifier = Modifier.fillMaxSize().safeContentPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            JetExpAppBar(
                                onSwitchClick = {
                                    currentTheme =
                                        when (currentTheme) {
                                            Theme.DARK -> Theme.LIGHT
                                            else -> Theme.DARK
                                        }
                                },
                                icon = icon,
                                title = currentScreen.pageTitle
                            ) { navController.navigateUp() }
                        },
                        bottomBar = {
                            JetExpBottomBar(
                                allScreens = allScreens,
                                onTabSelected = { navController.navigateToSingleTop(it.routePath) },
                                selectedTab = currentScreen
                            ) { navController.navigateToSingleTop(TransactionDestination.routeWithArgs) }
                        },
                        floatingActionButtonPosition = FabPosition.End
                    ) { innerPadding ->
                        JetExpNavHost(
                            navController = navController,
                            assistedFactory = assistedFactory,
                            modifier = Modifier.padding(innerPadding).padding(horizontal = 16.dp),
                            homeViewModel = homeViewModel,
                            incomeViewModel = incomeViewModel,
                            expenseViewModel = expenseViewModel
                        )
                    }
                }

            }
        }
    }

    private val LocalAppTheme = staticCompositionLocalOf<Theme> { error("No theme provided") }

    @Composable
    private fun rememberCurrentScreen(navController: NavController): JetExpenseDestination {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry?.destination
        return allScreens.find { it.routePath == currentDestination?.route }
            ?: TransactionDestination
    }

}

enum class Theme { SYSTEM, DARK, LIGHT }

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetExpenseTheme {
        Greeting("Android")
    }
}