package hoods.com.jetexpense.presentation.expense

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hoods.com.jetexpense.presentation.components.ExpenseRow
import hoods.com.jetexpense.presentation.components.TransactionStatement
import hoods.com.jetexpense.presentation.models.ExpenseP
import hoods.com.jetexpense.util.Util
import hoods.com.jetexpense.util.expenseList
import hoods.com.jetexpense.util.formatDate

@Composable
fun ExpenseScreen(
    modifier: Modifier = Modifier,
    expense: List<ExpenseP>,
    onExpenseItemClicked: (id: Int) -> Unit,
    onDeleteExpense: (Int) -> Unit,
) {
    TransactionStatement(
        items = expense,
        colors = { expenseP -> expenseP.color },
        amounts = { expenseP -> expenseP.expenseAmount },
        amountsTotal = expense.sumOf { it.expenseAmount.toDouble() }.toFloat(),
        circleLabel = "Pay",
        onItemSwiped = {
            onDeleteExpense.invoke(it.id)
        },
        key = {
            it.id
        },
        modifier = modifier
    ) { expenseP ->
        expenseP.apply {
            ExpenseRow(
                name = title,
                description = description,
                amount = expenseAmount,
                color = color,
                modifier = Modifier.clickable {
                    onExpenseItemClicked.invoke(id)
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevExpenseScreen() {
    ExpenseScreen(expense = expenseList.map {
        it.run {
            ExpenseP(
                title = title,
                description = description,
                expenseAmount = expenseAmount.toFloat(),
                entryDate = formatDate(date),
                date = date,
                category = category,
                color = Util.expenseColor.random()
            )
        }
    },
        onExpenseItemClicked = {}
    ) {

    }
}