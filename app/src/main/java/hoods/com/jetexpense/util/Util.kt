package hoods.com.jetexpense.util

import androidx.compose.ui.graphics.Color
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

