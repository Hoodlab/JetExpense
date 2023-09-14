package hoods.com.jetexpense.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import hoods.com.jetexpense.R
sealed class JetExpenseDestination {
    abstract val iconResId: Int
    abstract val routePath: String
    abstract val pageTitle: String
}

object HomeDestination : JetExpenseDestination() {
    override val iconResId: Int = R.drawable.ic_home
    override val routePath: String = "home"
    override val pageTitle: String = "Home"
}

object IncomeDestination : JetExpenseDestination() {
    override val iconResId: Int = R.drawable.ic_income_dollar
    override val routePath: String = "income"
    override val pageTitle: String = "Income"
}

object ExpenseDestination : JetExpenseDestination() {
    override val iconResId: Int = R.drawable.ic_expense_dollar
    override val routePath: String = "expense"
    override val pageTitle: String = "Expense"
}

object TransactionDestination : JetExpenseDestination() {
    override val iconResId: Int = R.drawable.add_entry
    override val routePath: String = "transaction"
    override val pageTitle: String = "Add Transaction"
    const val transactionTypeArg = "type"
    const val idTypeArg = "id"

    val arguments = listOf(
        navArgument(transactionTypeArg) {
            type = NavType.StringType
            defaultValue = ""
        },
        navArgument(idTypeArg) {
            type = NavType.IntType
            defaultValue = -1
        }
    )

    val routeWithArgs =
        "$routePath?$transactionTypeArg={$transactionTypeArg}&$idTypeArg={$idTypeArg}"
}
