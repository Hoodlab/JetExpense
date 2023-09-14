package hoods.com.jetexpense.presentation.transaction

import hoods.com.jetexpense.presentation.navigation.JetExpenseDestination
import hoods.com.jetexpense.util.Category

interface TransactionCallBack {
    val isFieldsNotEmpty: Boolean
    fun onTitleChange(newValue: String)

    fun onAmountChange(newValue: String)

    fun onDescriptionChange(newValue: String)

    fun onCategoryChange(newValue: Category)

    fun onTransactionTypeChange(newValue: String)

    fun onDateChange(newValue: Long?)

    fun onScreenTypeChange(newValue: JetExpenseDestination)

    fun onOpenDialog(newValue: Boolean)

    fun addIncome()

    fun addExpense()

    fun getIncome(id: Int)

    fun getExpense(id: Int)

    fun updateIncome()
    fun updateExpense()

}