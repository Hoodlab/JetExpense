package hoods.com.jetexpense.presentation.transaction

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import hoods.com.jetexpense.R
import hoods.com.jetexpense.presentation.navigation.ExpenseDestination
import hoods.com.jetexpense.presentation.navigation.IncomeDestination
import hoods.com.jetexpense.presentation.navigation.JetExpenseDestination
import hoods.com.jetexpense.util.Category
import hoods.com.jetexpense.util.formatDate
import java.util.*


@Composable
fun TransactionScreen(
    modifier: Modifier = Modifier,
    transactionId: Int,
    assistedFactory: TransactionAssistedFactory,
    transactionType: String?,
    navigateUp: () -> Unit,
) {
    val viewModel = viewModel(
        modelClass = TransactionViewModel::class.java,
        factory = TransactionViewModelFactory(
            id = transactionId,
            assistedFactory = assistedFactory,
            transactionType = transactionType
        ),
    )
    TransactionScreen(
        modifier = modifier,
        state = viewModel.state,
        transactionCallBack = viewModel,
        navigateUp = navigateUp
    )
}

@Composable
private fun TransactionScreen(
    modifier: Modifier = Modifier,
    state: TransactionState,
    transactionCallBack: TransactionCallBack,
    navigateUp: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val transactionScreenList = listOf(IncomeDestination, ExpenseDestination)
    val isExpenseTransaction = state.transactionScreen == ExpenseDestination
    val icon = when {
        isExpenseTransaction -> R.drawable.ic_expense_dollar
        else -> R.drawable.ic_income_dollar
    }
    Column(
        modifier
            .scrollable(
                state = scrollState,
                orientation = Orientation.Vertical,
            )
    ) {
        TransactionTitle(icon, state, transactionCallBack, transactionScreenList)
        Spacer(modifier = Modifier.size(12.dp))
        TransactionDetails(state, transactionCallBack, isExpenseTransaction)
        Spacer(modifier = Modifier.Companion.height(24.dp))
        TransactionButton(state, transactionCallBack, navigateUp)
    }

}

@Composable
private fun TransactionButton(
    state: TransactionState,
    transactionCallBack: TransactionCallBack,
    navigateUp: () -> Unit,
) {
    val buttonTitle = if (state.isUpdatingTransaction) "Update Transaction"
    else "Add Transaction"
    Button(
        onClick = {
            when (state.isUpdatingTransaction) {
                true -> {
                    if (state.transactionScreen == IncomeDestination) {
                        transactionCallBack.updateIncome()
                    } else {
                        transactionCallBack.updateExpense()
                    }
                }

                false -> {
                    if (state.transactionScreen == IncomeDestination) {
                        transactionCallBack.addIncome()
                    } else {
                        transactionCallBack.addExpense()
                    }
                }
            }
            navigateUp.invoke()
        },
        modifier = Modifier.fillMaxWidth(),
        enabled = transactionCallBack.isFieldsNotEmpty
    ) {
        Text(text = buttonTitle)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TransactionDetails(
    state: TransactionState,
    transactionCallBack: TransactionCallBack,
    isExpenseTransaction: Boolean,
) {
    Column {
        TransactionTextField(
            value = state.title,
            onValueChange = transactionCallBack::onTitleChange,
            labelText = "Transaction Title",
        )
        Spacer(modifier = Modifier.size(12.dp))
        TransactionTextField(
            value = state.amount,
            onValueChange = transactionCallBack::onAmountChange,
            labelText = "Amount",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(12.dp))
        JetPetDate(state.date, onDateChange = transactionCallBack::onDateChange)
        TransactionTextField(
            value = state.description,
            onValueChange = transactionCallBack::onDescriptionChange,
            labelText = "Transaction Description",
        )
        Spacer(modifier = Modifier.height(12.dp))
        AnimatedVisibility(isExpenseTransaction) {
            LazyRow {
                items(Category.values()) { category ->
                    InputChip(
                        selected = category == state.category,
                        onClick = { transactionCallBack.onCategoryChange(category) },
                        label = { Text(text = category.title) },
                        modifier = Modifier.padding(horizontal = 8.dp),
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = category.iconRes),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )

                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun TransactionTextField(
    value:String,
    onValueChange:(String) -> Unit,
    labelText:String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = labelText) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = MaterialTheme.shapes.extraLarge
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JetPetDate(
    date: Date,
    onDateChange: (Long?) -> Unit,
) {
    val datePickerState = rememberDatePickerState()
    var openDateDialog by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = formatDate(date = date)
        )
        Spacer(modifier = Modifier.size(4.dp))
        if (openDateDialog) {
            DatePickerDialog(
                onDismissRequest = {
                    openDateDialog = false
                },
                confirmButton = {
                    Button(onClick = {
                        onDateChange.invoke(datePickerState.selectedDateMillis)
                        openDateDialog = false
                    }) {
                        Text("Submit")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { openDateDialog = false }) {
                        Text("Dismiss")
                    }
                },
                content = { DatePicker(datePickerState) },
            )
        }
        IconButton(onClick = {
            openDateDialog = true
        }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
            )
        }


    }
}

@Composable
private fun TransactionTitle(
    icon: Int,
    state: TransactionState,
    transactionCallBack: TransactionCallBack,
    transactionScreenList: List<JetExpenseDestination>,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(id = icon), contentDescription = null)
        Spacer(modifier = Modifier.size(6.dp))
        Text(text = state.transactionScreen.pageTitle)
        Spacer(modifier = Modifier.size(6.dp))
        IconButton(
            onClick = {
                transactionCallBack.onOpenDialog(!state.openDialog)
            }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
            )
        }
        if (!state.openDialog) {
            Popup(
                onDismissRequest = {
                    transactionCallBack
                        .onOpenDialog(!state.openDialog)
                },
            ) {
                Surface(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column {
                        transactionScreenList.forEach {
                            Text(
                                text = it.pageTitle,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        transactionCallBack.onScreenTypeChange(it)
                                        transactionCallBack
                                            .onOpenDialog(!state.openDialog)
                                    },
                            )

                        }

                    }
                }
            }
        }

    }
}


@Preview(showSystemUi = true)
@Composable
fun PrevTransactionScreen() {
    TransactionScreen(
        transactionCallBack = MockTransactionCallBAck(false),
        state = TransactionState(),
    ) {

    }
}