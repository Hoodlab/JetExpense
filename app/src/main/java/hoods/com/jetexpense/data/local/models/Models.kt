package hoods.com.jetexpense.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import hoods.com.jetexpense.util.Util
import hoods.com.jetexpense.util.getColor
import java.util.*

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