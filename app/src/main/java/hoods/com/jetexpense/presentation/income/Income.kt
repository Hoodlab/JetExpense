package hoods.com.jetexpense.presentation.income

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hoods.com.jetexpense.data.local.models.Income
import hoods.com.jetexpense.presentation.components.IncomeRow
import hoods.com.jetexpense.presentation.components.TransactionStatement
import hoods.com.jetexpense.util.Util
import hoods.com.jetexpense.util.getColor
import hoods.com.jetexpense.util.incomeList
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun IncomeScreen(
    modifier: Modifier = Modifier,
    incomes: List<Income>,
    onIncomeItemClick: (id: Int) -> Unit,
    onIncomeItemDelete: (Int) -> Unit,
) {
    TransactionStatement(
        items = incomes,
        colors = { getColor(it.incomeAmount.toFloat(),Util.expenseColor) },
        amounts = { it.incomeAmount.toFloat() },
        amountsTotal = incomes.sumOf { it.incomeAmount }.toFloat(),
        circleLabel = "Receive",
        onItemSwiped = {
            onIncomeItemDelete.invoke(it.id)
        },
        key = { it.id },
        modifier = modifier
    ) {
        IncomeRow(
            name = it.title,
            description = "Receive ${formatDetailDate(date = it.date)}",
            amount = it.incomeAmount.toFloat(),
            color = getColor(it.incomeAmount.toFloat(),Util.expenseColor),
            modifier = Modifier.clickable {
                onIncomeItemClick.invoke(it.id)
            }
        )

    }

}

fun formatDetailDate(date: Date): String =
    SimpleDateFormat("MM dd", Locale.getDefault()).format(date)

@Preview(showSystemUi = true)
@Composable
fun PrevIncomeDetail() {
    IncomeScreen(
        incomes = incomeList,
        onIncomeItemClick = {}
    ) {

    }
}