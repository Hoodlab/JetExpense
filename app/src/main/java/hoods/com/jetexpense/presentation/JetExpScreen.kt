package hoods.com.jetexpense.presentation

import androidx.annotation.DrawableRes
import hoods.com.jetexpense.R

enum class JetExpScreen(
    @DrawableRes val icon: Int,
) {
    IncomeScreen(icon = R.drawable.ic_income_dollar),
    HomeScreen(icon = R.drawable.ic_home),
    ExpenseScreen(icon = R.drawable.ic_expense_dollar),
    TransactionScreen(icon = R.drawable.add_entry)
}