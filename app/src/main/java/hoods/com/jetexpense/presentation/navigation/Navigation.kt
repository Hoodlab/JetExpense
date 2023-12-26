package hoods.com.jetexpense.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hoods.com.jetexpense.presentation.expense.ExpenseScreen
import hoods.com.jetexpense.presentation.expense.ExpenseViewModel
import hoods.com.jetexpense.presentation.home.HomeScreen
import hoods.com.jetexpense.presentation.home.HomeViewModel
import hoods.com.jetexpense.presentation.income.IncomeScreen
import hoods.com.jetexpense.presentation.income.IncomeViewModel
import hoods.com.jetexpense.presentation.transaction.TransactionAssistedFactory
import hoods.com.jetexpense.presentation.transaction.TransactionScreen

@Composable
fun JetExpNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    assistedFactory: TransactionAssistedFactory,
    homeViewModel: HomeViewModel,
    incomeViewModel: IncomeViewModel,
    expenseViewModel: ExpenseViewModel,
) {
    val expenseState = expenseViewModel.expenseState
    val incomeState = incomeViewModel.incomeState

    NavHost(navController = navController, startDestination = HomeDestination.routePath) {
        composable(route = HomeDestination.routePath) {
            HomeScreen(
                state = homeViewModel.homeUiState,
                modifier = modifier,
                onIncomeClick = {
                    navController.navigateToTransactionScreen(
                        id = it,
                        transType = IncomeDestination.routePath
                    )
                },
                onExpenseClick = {
                    navController.navigateToTransactionScreen(
                        id = it,
                        transType = ExpenseDestination.routePath
                    )
                },
                onClickSeeAllIncome = {
                    navController.navigateToSingleTop(IncomeDestination.routePath)
                },
                onClickSeeAllExpense = {
                    navController.navigateToSingleTop(ExpenseDestination.routePath)
                },
                onInsertIncome = homeViewModel::insertIncome,
                onInsertExpense = homeViewModel::insertExpense
            )
        }

        composable(route = ExpenseDestination.routePath) {
            ExpenseScreen(
                expense = expenseState.expenses,
                onDeleteExpense = { expenseViewModel.deleteExpense(it) },
                onExpenseItemClicked = { expenseId ->
                    navController.navigateToTransactionScreen(
                        id = expenseId,
                        transType = ExpenseDestination.routePath
                    )
                },
                modifier = modifier
            )
        }

        composable(route = IncomeDestination.routePath) {
            IncomeScreen(
                modifier = modifier,
                incomes = incomeState.incomes,
                onIncomeItemClick = { incomeId ->
                    navController.navigateToTransactionScreen(
                        id = incomeId,
                        transType = IncomeDestination.routePath
                    )
                },
                onIncomeItemDelete = incomeViewModel::deleteIncome
            )
        }

        composable(
            route = TransactionDestination.routeWithArgs,
            arguments = TransactionDestination.arguments
        ) { navBackStackEntry ->
            val transType =
                navBackStackEntry.arguments?.getString(TransactionDestination.transactionTypeArg)
                    ?: ""
            val transId =
                navBackStackEntry.arguments?.getInt(TransactionDestination.idTypeArg) ?: -1
            TransactionScreen(
                assistedFactory = assistedFactory,
                transactionId = transId,
                transactionType = transType,
                modifier = modifier
            ) {
                navController.navigateUp()
            }
        }
    }
}

fun NavHostController.navigateToSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavHostController.navigateToTransactionScreen(
    id: Int = -1,
    transType: String,
) {
    val route =
        "${TransactionDestination.routePath}?${TransactionDestination.transactionTypeArg}=$transType&${TransactionDestination.idTypeArg}=$id"
    navigateToSingleTop(route)
}
