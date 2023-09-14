package hoods.com.jetexpense.data.repository

import hoods.com.jetexpense.data.local.models.Expense
import hoods.com.jetexpense.data.local.models.Income
import hoods.com.jetexpense.data.local.models.WeeklyData
import kotlinx.coroutines.flow.Flow

interface Repository {
    val income: Flow<List<Income>>
    val expense: Flow<List<Expense>>

    suspend fun insertIncome(income: Income)

    suspend fun insertExpense(expense: Expense)

    fun getWeeklyData(): Flow<Pair<List<WeeklyData>, List<WeeklyData>>>

    fun getExpenseByDays(): Flow<List<WeeklyData>>

    fun getIncomeById(id: Int): Flow<Income>

    fun getExpenseById(id: Int): Flow<Expense>

    suspend fun updateIncome(income: Income)
    suspend fun updateExpense(expense: Expense)
    suspend fun deleteIncome(id: Int): Int
    suspend fun deleteExpense(id: Int): Int
}

