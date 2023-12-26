package hoods.com.jetexpense.presentation.expense

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hoods.com.jetexpense.data.local.models.Expense
import hoods.com.jetexpense.presentation.components.ExpenseRow
import hoods.com.jetexpense.presentation.components.TransactionStatement
import hoods.com.jetexpense.util.Util
import hoods.com.jetexpense.util.expenseList
import hoods.com.jetexpense.util.getColor

@Composable
fun ExpenseScreen(
    modifier: Modifier = Modifier,
    expense: List<Expense>,
    onExpenseItemClicked: (id: Int) -> Unit,
    onDeleteExpense: (Int) -> Unit,
) {
    TransactionStatement(
        items = expense,
        colors = { getColor(it.expenseAmount.toFloat(),Util.expenseColor)},
        amounts = {it.expenseAmount.toFloat() },
        amountsTotal = expense.sumOf { it.expenseAmount }.toFloat(),
        circleLabel = "Pay",
        onItemSwiped = {
            onDeleteExpense.invoke(it.id)
        },
        key = {
            it.id
        },
        modifier = modifier
    ) { expense ->
        expense.apply {
            ExpenseRow(
                name = title,
                description = description,
                amount = expenseAmount.toFloat(),
                color = getColor(expenseAmount.toFloat(),Util.expenseColor),
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
    ExpenseScreen(expense = expenseList,
        onExpenseItemClicked = {}
    ) {

    }
}