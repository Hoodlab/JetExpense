package hoods.com.jetexpense.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hoods.com.jetexpense.R
import hoods.com.jetexpense.presentation.home.HomeUiState
import hoods.com.jetexpense.presentation.navigation.ExpenseDestination
import hoods.com.jetexpense.presentation.navigation.HomeDestination
import hoods.com.jetexpense.presentation.navigation.IncomeDestination
import hoods.com.jetexpense.presentation.navigation.JetExpenseDestination
import hoods.com.jetexpense.util.Util
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetExpAppBar(
    @DrawableRes icon: Int = R.drawable.ic_switch_off,
    title: String,
    onSwitchClick: () -> Unit,
    onNavigateUp: () -> Unit,
) {
    TopAppBar(title = {
        Text(text = title)
    },
        navigationIcon = {
            AnimatedVisibility(
                visible = title != HomeDestination.pageTitle,
                enter = fadeIn() + slideInHorizontally(),
                exit = fadeOut() + slideOutHorizontally()
            ) {
                IconButton(onClick = { onNavigateUp.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                    )
                }
            }
        },
        actions = {
            AnimatedContent(targetState = icon, label = "") { iconRes ->
                IconButton(onClick = { onSwitchClick() }) {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = ""
                    )
                }
            }
        })
}



@Composable
fun JetExpBottomBar(
    allScreens: List<JetExpenseDestination>,
    onTabSelected: (JetExpenseDestination) -> Unit,
    selectedTab: JetExpenseDestination,
    onFabClick: () -> Unit,
) {
    BottomAppBar(
        floatingActionButton = {
            JetExpFab(onFabClick, selectedTab)
        },
        actions = {
            allScreens.forEach { screen ->
                JetExpRow(
                    text = screen.pageTitle,
                    icon = screen.iconResId,
                    onSelected = { onTabSelected(screen) },
                    selected = selectedTab == screen
                )
            }
        }
    )
}

@Composable
private fun JetExpFab(
    onFabClick: () -> Unit,
    selectedTab: JetExpenseDestination,
) {
    FloatingActionButton(onClick = { onFabClick() }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = selectedTab.routePath,
        )
    }
}

@Composable
fun Card(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 4.dp,
        color = color,
        contentColor = contentColor
    ) {
        Box(contentAlignment = Alignment.Center) {
            content.invoke()
        }
    }
}


@Composable
fun AccountCard(
    modifier: Modifier = Modifier,
    cardTitle: String,
    amount: String,
    cardIcon: ImageVector?,
) {
    Card(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 10.dp,
                alignment = Alignment.CenterVertically
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (cardIcon != null) {
                val iconColor =
                    if (cardTitle == "TOTAL EXPENSE") Util.expenseColor.last()
                    else Util.incomeColor.last()
                AccountIconItem(
                    modifier = Modifier.align(Alignment.End),
                    cardIcon,
                    iconColor
                )
            }
            Text(
                text = cardTitle,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .8f)
            )
            Text(
                text = amount,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun AccountIconItem(
    modifier: Modifier = Modifier,
    cardIcon: ImageVector,
    color: Color,
) {
    Surface(
        shape = CircleShape,
        color = color.copy(alpha = .1f),
        contentColor = color,
        modifier = modifier.size(36.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = cardIcon,
                contentDescription = null,
                modifier = Modifier.padding(4.dp),
            )
        }
    }
}

@Composable
fun IncomesCard(
    account: HomeUiState,
    onClickSeeAll: () -> Unit,
    onIncomeClick: (id: Int) -> Unit,
) {
    OverViewCard(
        title = "Income",
        amount = account.totalIncome,
        onClickSeeAll = onClickSeeAll,
        values = { it.incomeAmount },
        colors = { it.color },
        data = account.income
    ) { income ->
        IncomeRow(name = income.title,
            description = income.description,
            amount = income.incomeAmount,
            color = income.color,
            modifier = Modifier.clickable { onIncomeClick(income.id) }
        )

    }

}

@Composable
fun ExpenseCard(
    account: HomeUiState,
    onClickSeeAll: () -> Unit,
    onExpenseClick: (Int) -> Unit,
) {
    OverViewCard(
        title = "Expense",
        amount = account.totalExpense,
        onClickSeeAll = onClickSeeAll,
        values = { it.expenseAmount },
        colors = { it.color },
        data = account.expense
    ) {
        ExpenseRow(
            name = it.title,
            description = it.description,
            amount = it.expenseAmount,
            color = it.color,
            modifier = Modifier.clickable {
                onExpenseClick.invoke(it.id)
            }
        )
    }
}

@Composable
fun ExpenseRow(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    amount: Float,
    color: Color,
) {
    BaseRow(
        modifier = modifier,
        color = color,
        title = name,
        subtitle = description,
        amount = amount,
        negative = true,
    )
}

@Composable
fun IncomeRow(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    amount: Float,
    color: Color,
) {
    BaseRow(
        modifier = modifier,
        color = color,
        title = name,
        subtitle = description,
        amount = amount,
        negative = false
    )
}

@Composable
private fun BaseRow(
    modifier: Modifier = Modifier,
    color: Color,
    title: String,
    subtitle: String,
    amount: Float,
    negative: Boolean,
) {
    val dollarSign = if (negative) "-$" else "$"
    val formattedAmount = formatAmount(amount)
    Row(
        modifier = modifier
            .height(68.dp)
            .clearAndSetSemantics {
                contentDescription =
                    "$title account ending in ${subtitle.takeLast(4)}," +
                            " current balance $dollarSign$formattedAmount"
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        val typography = MaterialTheme.typography
        AccountIndicator(color = color, modifier = Modifier)
        Spacer(modifier = Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = title,
                style = typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = subtitle,
                style = typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = dollarSign,
                style = typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Text(
                text = formattedAmount,
                style = typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 12.dp)
                .size(24.dp)
        )

    }
    JetExpenseDivider()

}

@Composable
private fun AccountIndicator(color: Color, modifier: Modifier) {
    Spacer(
        modifier = modifier
            .size(4.dp, 36.dp)
            .background(color)
    )
}

@Composable
fun JetExpenseDivider(modifier: Modifier = Modifier) {
    Divider(color = MaterialTheme.colorScheme.background, thickness = 1.dp, modifier = modifier)
}

@Composable
private fun <T> OverViewCard(
    title: String,
    amount: Float,
    onClickSeeAll: () -> Unit,
    values: (T) -> Float,
    colors: (T) -> Color,
    data: List<T>,
    row: @Composable (T) -> Unit,
) {
    androidx.compose.material3.Card {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(12.dp)
            )
            OverViewDivider(data = data, values = values, colors = colors)
            Column(Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp)) {
                data.takeLast(SHOWN_ITEMS).forEach { row(it) }
                SeeAllButton(
                    modifier = Modifier.clearAndSetSemantics {
                        contentDescription = "All $title"
                    },
                    onClick = onClickSeeAll,
                )
            }
        }
    }
}

@Composable
fun <T> OverViewDivider(
    data: List<T>,
    values: (T) -> Float,
    colors: (T) -> Color,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        data.forEach { item: T ->
            Spacer(
                modifier = Modifier
                    .weight(values(item))
                    .height(1.dp)
                    .background(colors(item))
            )
        }
    }
}

@Composable
fun SeeAllButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .height(44.dp)
            .fillMaxWidth()
    ) {
        Text("SEE ALL")
    }
}

fun formatAmount(amount: Float): String {
    return AmountDecimalFormat.format(amount)
}

private val AccountDecimalFormat = DecimalFormat("####")
private val AmountDecimalFormat = DecimalFormat("#,###.##")


@Preview(showBackground = true)
@Composable
fun PrevHomeBar() {
    JetExpAppBar(onSwitchClick = {}, title = HomeDestination.pageTitle) {}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrevAccountCard() {
    Column {
        AccountCard(cardTitle = "TOTAL BALANCE", amount = "4500", cardIcon = null)
        Spacer(modifier = Modifier.size(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            AccountCard(
                cardTitle = "TOTAL INCOME",
                amount = "5000",
                cardIcon = Icons.Default.ArrowDownward,
                modifier = Modifier.weight(1f)
            )
            AccountCard(
                cardTitle = "TOTAL EXPENSE",
                amount = "-500",
                cardIcon = Icons.Default.ArrowUpward,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun OverviewScreenPref() {
    IncomesCard(account = HomeUiState(), onClickSeeAll = { /*TODO*/ }) {

    }
}

@Preview(showBackground = true)
@Composable
fun PrevBottomBar() {
    JetExpBottomBar(
        allScreens = listOf(IncomeDestination, HomeDestination, ExpenseDestination),
        onTabSelected = {},
        selectedTab = HomeDestination
    ) {

    }
}


private const val SHOWN_ITEMS = 3