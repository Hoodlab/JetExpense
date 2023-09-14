package hoods.com.jetexpense.presentation.expense

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoods.com.jetexpense.data.local.models.mapExpense
import hoods.com.jetexpense.data.repository.Repository
import hoods.com.jetexpense.presentation.models.ExpenseP
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {
    var expenseState by mutableStateOf(ExpenseState())

    init {
        getAllExpense()
    }

    private fun getAllExpense() = viewModelScope.launch {
        repository.expense.collectLatest {
            expenseState = expenseState.copy(
                expenses = it
                    .map { expenses -> expenses.mapExpense() }
            )
        }
    }

    fun deleteExpense(id: Int) = viewModelScope.launch {
        repository.deleteExpense(id)
    }


}

data class ExpenseState(
    val expenses: List<ExpenseP> = emptyList(),
)