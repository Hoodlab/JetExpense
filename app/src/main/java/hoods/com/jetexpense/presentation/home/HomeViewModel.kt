package hoods.com.jetexpense.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoods.com.jetexpense.data.local.models.Expense
import hoods.com.jetexpense.data.local.models.Income
import hoods.com.jetexpense.data.local.models.WeeklyData
import hoods.com.jetexpense.data.local.models.mapExpense
import hoods.com.jetexpense.data.local.models.mapIncome
import hoods.com.jetexpense.data.repository.Repository
import hoods.com.jetexpense.presentation.JetExpScreen
import hoods.com.jetexpense.presentation.models.ExpenseP
import hoods.com.jetexpense.presentation.models.IncomeP
import hoods.com.jetexpense.util.expenseList
import hoods.com.jetexpense.util.incomeList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    val income = repository.income
    val expense = repository.expense
    private val weeklyData = repository.getWeeklyData()

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    init {
        viewModelScope.launch {
            combine(income, expense, weeklyData) {
                    incomeList: List<Income>,
                    expenseList: List<Expense>,
                    weeklyTrans: Pair<List<WeeklyData>, List<WeeklyData>>,
                ->
                homeUiState.copy(
                    income = incomeList.map {
                        it.run {
                            mapIncome()
                        }
                    },
                    expense = expenseList.map {
                        it.run {
                            mapExpense()
                        }
                    },
                    totalExpense = expenseList.sumOf { it.expenseAmount }.toFloat(),
                    totalIncome = incomeList.sumOf { it.incomeAmount }.toFloat(),
                    incomeWeeklyData = weeklyTrans.first,
                    expenseWeeklyData = weeklyTrans.second
                )
            }.collectLatest {
                homeUiState = it
            }
        }
    }


    fun insertIncome() = viewModelScope.launch {
        repository.insertIncome(incomeList.random())
    }

    fun insertExpense() = viewModelScope.launch {
        repository.insertExpense(expenseList.random())
    }




    fun onThemeChange(isDarkTheme: Boolean) {
        homeUiState = homeUiState.copy(isDarkTheme = isDarkTheme)
    }

    fun onScreenChange(screen: JetExpScreen) {
        homeUiState = homeUiState.copy(selectedScreen = screen)
    }


}


data class HomeUiState(
    val income: List<IncomeP> = emptyList(),
    val expense: List<ExpenseP> = emptyList(),
    val incomeWeeklyData: List<WeeklyData> = emptyList(),
    val expenseWeeklyData: List<WeeklyData> = emptyList(),
    val totalExpense: Float = 0f,
    val totalIncome: Float = 0f,
    val isDarkTheme: Boolean = false,
    val selectedScreen: JetExpScreen = JetExpScreen.HomeScreen,
)



