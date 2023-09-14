package hoods.com.jetexpense.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import hoods.com.jetexpense.presentation.models.ExpenseP
import hoods.com.jetexpense.presentation.models.IncomeP
import hoods.com.jetexpense.util.Util
import hoods.com.jetexpense.util.getColor
import java.util.*

data class WeeklyData(
    @ColumnInfo(name = "date")
    val date: Date,
    @ColumnInfo(name = "amount")
    val amount: Double,
)

@Entity(tableName = "expense_table")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "expense_id")
    val id: Int = 0,
    val title: String,
    val description: String,
    @ColumnInfo(name = "expense_amount")
    val expenseAmount: Double,
    val category: String,
    @ColumnInfo(name = "entry_date")
    val entryDate: String,
    val date: Date,
)

@Entity(tableName = "income_table")
data class Income(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "income_id")
    val id: Int = 0,
    val title: String,
    val description: String,
    @ColumnInfo(name = "income_Amount")
    val incomeAmount: Double,
    @ColumnInfo(name = "entry_date")
    val entryDate: String,
    val date: Date,
)


data class ExpenseDto(
    @ColumnInfo(name = "expense_id")
    val id: Int,
    val title: String,
    val description: String,
    @ColumnInfo(name = "expense_amount")
    val expenseAmount: Double,
    val category: String,
    @ColumnInfo(name = "expense_total")
    val totalExpense: Double,
) {

}


data class IncomeDto(
    @ColumnInfo(name = "income_id")
    val id: Int,
    val title: String,
    val description: String,
    @ColumnInfo(name = "income_Amount")
    val incomeAmount: Double,
    @ColumnInfo(name = "income_total")
    val totalIncome: Double,
)

@Entity(tableName = "trans_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "trans_id")
    val id: Int,
    @ColumnInfo(name = "trans_type_id")
    val transactionTypeId: Int,
    @ColumnInfo(name = "trans_type")
    val transactionType: String,
)

data class Account(
    @ColumnInfo(name = "trans_type_id")
    val transactionTypeId: Int,
    @ColumnInfo(name = "trans_type")
    val transactionType: String,
    val title: String,
    val description: String,
    val amount: Double,
)

fun Expense.mapExpense() =
    ExpenseP(
        id = id,
        title = title,
        description = description,
        expenseAmount = expenseAmount.toFloat(),
        category = category,
        color = getColor(expenseAmount.toFloat(), Util.expenseColor),
        entryDate = entryDate,
        date = date
    )

fun Income.mapIncome() =
    IncomeP(
        id,
        title,
        description,
        incomeAmount.toFloat(),
        entryDate,
        color = getColor(incomeAmount.toFloat(), Util.incomeColor),
        date = date
    )