package hoods.com.jetexpense.presentation.income

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hoods.com.jetexpense.presentation.components.IncomeRow
import hoods.com.jetexpense.presentation.components.TransactionStatement
import hoods.com.jetexpense.presentation.models.IncomeP
import hoods.com.jetexpense.util.Util
import hoods.com.jetexpense.util.formatDate
import hoods.com.jetexpense.util.incomeList
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun IncomeScreen(
    modifier: Modifier = Modifier,
    incomes: List<IncomeP>,
    onIncomeItemClick: (Id: Int) -> Unit,
    onIncomeItemDelete: (Int) -> Unit,
) {
    TransactionStatement(
        items = incomes,
        colors = { it.color },
        amounts = { it.incomeAmount },
        amountsTotal = incomes.sumOf { it.incomeAmount.toDouble() }.toFloat(),
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
            amount = it.incomeAmount,
            color = it.color,
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
        incomes = incomeList.mapIndexed { index, item ->
            item.run {
                IncomeP(
                    id = index,
                    title = title,
                    description = description,
                    incomeAmount = incomeAmount.toFloat(),
                    entryDate = formatDate(date),
                    date = date,
                    color = Util.incomeColor.random()
                )
            }
        },
        onIncomeItemClick = {}
    ) {

    }
}