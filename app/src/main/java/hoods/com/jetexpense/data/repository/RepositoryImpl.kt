package hoods.com.jetexpense.data.repository

import android.util.Log
import hoods.com.jetexpense.data.local.ExpenseDao
import hoods.com.jetexpense.data.local.IncomeDao
import hoods.com.jetexpense.data.local.models.Expense
import hoods.com.jetexpense.data.local.models.Income
import hoods.com.jetexpense.data.local.models.WeeklyData
import hoods.com.jetexpense.di.IoDispatcher
import hoods.com.jetexpense.presentation.transaction.TransactionViewModel.Companion.TAG
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val incomeDao: IncomeDao,
    private val expenseDao: ExpenseDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : Repository {
    override val income
        get() = incomeDao.getAllIncome()
    override val expense
        get() = expenseDao.getAllExpense()

    override suspend fun insertIncome(income: Income) = withContext(dispatcher) {
        incomeDao.insertIncome(income)
    }

    override suspend fun insertExpense(expense: Expense) = withContext(dispatcher) {
        expenseDao.insertExpense(expense)
    }

    override fun getWeeklyData(): Flow<Pair<List<WeeklyData>, List<WeeklyData>>> =
        combine(
            incomeDao.getLast7DaysIncome(),
            expenseDao.getLast7DaysExp()
        ) { income, expense ->
            Pair(income, expense)
        }

    override fun getExpenseByDays() = expenseDao.getLast7DaysExp()

    override fun getIncomeById(id: Int): Flow<Income> = incomeDao.getIncomeById(id)

    override fun getExpenseById(id: Int): Flow<Expense> = expenseDao.getExpenseById(id)

    override suspend fun updateIncome(income: Income) = withContext(dispatcher) {
        incomeDao.updateIncome(income)
    }

    override suspend fun updateExpense(expense: Expense) = withContext(dispatcher) {
        expenseDao.updateExpense(expense)
    }

    override suspend fun deleteIncome(id: Int) = withContext(dispatcher) {
        val status = incomeDao.deleteIncome(id)
        Log.i(TAG, "deleteIncome: $status")
    }

    override suspend fun deleteExpense(id: Int) = withContext(dispatcher) {
        val status = expenseDao.deleteExpense(id)
        Log.i(TAG, "deleteIncome: $status")
    }

}