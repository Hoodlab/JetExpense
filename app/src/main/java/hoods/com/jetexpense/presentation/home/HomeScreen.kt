package hoods.com.jetexpense.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hoods.com.jetexpense.presentation.components.*
import hoods.com.jetexpense.presentation.ui.theme.JetExpenseTheme


@Composable
fun HomeScreen(
    state:HomeUiState,
    modifier: Modifier = Modifier,
    onIncomeClick: (Int) -> Unit,
    onExpenseClick: (Int) -> Unit,
    onClickSeeAllIncome: () -> Unit,
    onClickSeeAllExpense: () -> Unit,
    onInsertIncome:() ->Unit,
    onInsertExpense:() -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Column {
                Row {
                    Text(text = "Your total balance:")
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = "$" + formatAmount(state.totalIncome - state.totalExpense),
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    AccountCard(
                        cardTitle = "TOTAL INCOME",
                        amount = "+$" + formatAmount(state.totalIncome),
                        cardIcon = Icons.Default.ArrowDownward,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp, top = 8.dp, bottom = 8.dp)
                    )
                    AccountCard(
                        cardTitle = "TOTAL EXPENSE",
                        amount = "-$" + formatAmount(state.totalExpense),
                        cardIcon = Icons.Default.ArrowUpward,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 4.dp, top = 8.dp, bottom = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.size(12.dp))
        }

        item {
            IncomesCard(
                account = state,
                onClickSeeAll = onClickSeeAllIncome,
                onIncomeClick = {
                    onIncomeClick.invoke(it)
                }
            )
            Spacer(modifier = Modifier.Companion.size(12.dp))
        }
        item {
            ExpenseCard(account = state, onClickSeeAll = onClickSeeAllExpense) {
                onExpenseClick.invoke(it)
            }
        }


        item {
            Row {
                ElevatedButton(onClick = onInsertIncome) {
                    Text(text = "Insert Income")
                }
                Spacer(modifier = Modifier.size(16.dp))
                ElevatedButton(onClick = onInsertExpense) {
                    Text(text = "Insert Expense")
                }
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun PrevHome() {
    JetExpenseTheme(darkTheme = false) {
        HomeScreen(
            state = HomeUiState(),
            onExpenseClick = {},
            onIncomeClick = {},
            onClickSeeAllExpense = {},
            onClickSeeAllIncome = {},
            onInsertExpense = {},
            onInsertIncome = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevHomeDark() {
    JetExpenseTheme(darkTheme = true) {
        HomeScreen(
            state = HomeUiState(),
            onExpenseClick = {},
            onIncomeClick = {},
            onClickSeeAllExpense = {},
            onClickSeeAllIncome = {},
            onInsertExpense = {},
            onInsertIncome = {}
        )
    }
}
