package hoods.com.jetexpense.presentation.income

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoods.com.jetexpense.data.local.models.mapIncome
import hoods.com.jetexpense.data.repository.Repository
import hoods.com.jetexpense.presentation.models.IncomeP
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {
    var incomeState by mutableStateOf(IncomeState())

    init {
        getAllExpense()
    }

    fun getAllExpense() = viewModelScope.launch {
        repository.income.collectLatest {
            incomeState = incomeState.copy(
                incomes = it
                    .map { income -> income.mapIncome() }
            )
        }
    }

    fun deleteIncome(id: Int) = viewModelScope.launch {
        repository.deleteIncome(id)
    }


}

data class IncomeState(
    val incomes: List<IncomeP> = emptyList(),
)