package hoods.com.jetexpense.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hoods.com.jetexpense.data.local.converters.DateConverter
import hoods.com.jetexpense.data.local.models.Expense
import hoods.com.jetexpense.data.local.models.Income

@TypeConverters(value = [DateConverter::class])
@Database(
    entities =
    [
        Income::class,
        Expense::class
    ],
    exportSchema = false,
    version = 1
)
abstract class JetExpDatabase : RoomDatabase() {
    abstract val expenseDao: ExpenseDao
    abstract val incomeDao: IncomeDao
}