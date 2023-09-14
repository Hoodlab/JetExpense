package hoods.com.jetexpense.presentation.models

import androidx.compose.ui.graphics.Color
import java.util.*

data class IncomeP(
    val id: Int = 0,
    val title: String,
    val description: String,
    val incomeAmount: Float,
    val entryDate: String,
    val color: Color,
    val date: Date,
) {
    constructor(
        incomeId: Int = 0,
        incomeTitle: String = "",
        description: String = "",
        incomeAmount: Float = 0f,
        color: Color = Color.White,
        entryDate: String = "",
        date: Date = Date(),
    ) : this(
        incomeId, incomeTitle, description, incomeAmount, entryDate, color, date
    )
}


data class ExpenseP(
    val id: Int = 0,
    val title: String,
    val description: String,
    val expenseAmount: Float,
    val category: String,
    val entryDate: String,
    val color: Color,
    val date: Date,
) {
    constructor(
        expenseId: Int = 0,
        expenseTitle: String = "",
        expenseAmount: Float = 0f,
        description: String = "",
        category: String = "",
        entryDate: String = "",
        color: Color = Color.White,
        date: Date = Date(),
    ) : this(expenseId, expenseTitle, description, expenseAmount, category, entryDate, color, date)
}

