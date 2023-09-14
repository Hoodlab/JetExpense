package hoods.com.jetexpense.data.local

import androidx.room.*
import hoods.com.jetexpense.data.local.models.Expense
import hoods.com.jetexpense.data.local.models.Income
import hoods.com.jetexpense.data.local.models.WeeklyData
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    @Query("DELETE FROM expense_table")
    suspend fun deleteAllExpense()

    @Query("DELETE FROM expense_table WHERE expense_id = :id")
    suspend fun deleteExpense(id: Int): Int


    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateExpense(expense: Expense)

    @Query("SELECT * FROM expense_table")
    fun getAllExpense(): Flow<List<Expense>>

    @Query("SELECT * FROM expense_table WHERE expense_id =:id")
    fun getExpenseById(id: Int): Flow<Expense>

    @Query(
        """
        SELECT SUM(expense_amount) AS amount,date AS date FROM expense_table 
        WHERE entry_date >= date('now','-7 day') GROUP BY entry_date 
    """
    )
    fun getLast7DaysExp(): Flow<List<WeeklyData>>


}

@Dao
interface IncomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncome(income: Income)

    @Query("DELETE FROM income_table")
    suspend fun deleteAllIncome()

    @Query("DELETE FROM income_table WHERE income_id = :id")
    suspend fun deleteIncome(id: Int): Int


    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateIncome(income: Income)

    @Query("SELECT * FROM income_table")
    fun getAllIncome(): Flow<List<Income>>

    @Query("SELECT * FROM income_table WHERE income_id=:id")
    fun getIncomeById(id: Int): Flow<Income>

    @Query(
        """
        SELECT SUM(income_Amount) AS amount,date AS date FROM income_table 
        WHERE entry_date >= date('now','-7 day') GROUP BY entry_date 
    """
    )
    fun getLast7DaysIncome(
    ): Flow<List<WeeklyData>>
}

