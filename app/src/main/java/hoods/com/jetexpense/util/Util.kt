package hoods.com.jetexpense.util

import androidx.compose.ui.graphics.Color
import hoods.com.jetexpense.data.local.models.Expense
import hoods.com.jetexpense.data.local.models.Income
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Util {
    val incomeColor = listOf(
        Color(0xFF37EFBA),
        Color(0xFF04B97F),
        Color(0xFF005D57),
        Color(0xFF29B82F),
        Color(0xFF008605)
    )
    val expenseColor = listOf(
        Color(0xFFFFD7D0),
        Color(0xFFFFDC78),
        Color(0xFFFFAC12),
        Color(0xFFFFAC12),
        Color(0xFFFF6951),
    )
}

private val date = Calendar.getInstance()

private fun formatDate(
    date: Calendar,
): String {
    val c = date
    val randomDay = 1..7
    c.set(Calendar.DAY_OF_WEEK, randomDay.random())
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        .format(c.time)
}

fun formatDate(date: Date): String =
    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        .format(date)

fun formatDays(days: Int) = when (days) {
    1 -> "Mon"
    2 -> "Tue"
    3 -> "Wed"
    4 -> "thus"
    5 -> "Fri"
    6 -> "Sat"
    7 -> "Sun"
    else -> "Unknown"
}

fun formatDays(date: Date): String {
    val sdf = SimpleDateFormat("EE", Locale.getDefault())
    return sdf.format(date)
}

val incomeList = listOf(
    Income(
        0,
        incomeAmount = 1000.0,
        title = "Freelancing",
        description = "Payment from upwork project",
        entryDate =formatDate(date),
        date = date.time
    ),
    Income(
        1,
        incomeAmount = 6000.0,
        title = "Salary",
        description = "Payment from parmanent job",
        entryDate =formatDate(date),
        date = date.time
    ),
    Income(
        2,
        incomeAmount = 3000.0,
        title = "Side project",
        description = "Payment from upwork project",
        entryDate =formatDate(date),
        date = date.time
    ),
    Income(
        3,
        incomeAmount = 1000.0,
        title = "Tutor Project",
        description = "Payment from students for coding session",
        entryDate =formatDate(date),
        date = date.time
    ),
    Income(
        4,
        incomeAmount = 1000.0,
        title = "Vending Machine",
        description = "Payment from selling soft drink",
        entryDate =formatDate(date),
        date = date.time
    )
)

val expenseList = listOf(
    Expense(
        entryDate =formatDate(date),
        expenseAmount = 50.0,
        category = "entertainment",
        title = "Netflix Subscription",

        description = "Payed Nexflix for monthly subscription",
        date = date.time
    ),
    Expense(
        entryDate =formatDate(date),
        expenseAmount = 100.0,
        category = "Food and Drinks",
        title = "Groceries",
        description = "Payed  for monthly groceries",
        date = date.time
    ),
    Expense(
        entryDate =formatDate(date),
        expenseAmount = 500.0,
        category = "Vehicle",
        title = "Car Maintenance",
        description = "Payed  for car tire, brake pad and oil change",
        date = date.time
    ),
    Expense(
        entryDate =formatDate(date),
        expenseAmount = 1000.0,
        category = "Housing",
        title = "Rent",
        description = "Payed for monthly rent for apartment",
        date = date.time
    ),
    Expense(
        entryDate = formatDate(date),
        expenseAmount = 100.0,
        category = "Tech",
        title = "Computer",
        description = "Purchased a computer for work",
        date = date.time
    ),

    )


fun getColor(amount: Float, colors: List<Color>): Color {
    return when {
        amount < 500 -> {
            colors[0]
        }
        amount < 1000 -> {
            colors[1]
        }
        amount < 5000 -> {
            colors[2]
        }
        amount < 10000 -> {
            colors[3]
        }
        else -> {
            colors[4]
        }
    }
}

