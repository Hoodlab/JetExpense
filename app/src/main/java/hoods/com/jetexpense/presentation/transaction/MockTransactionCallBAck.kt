package hoods.com.jetexpense.presentation.transaction

import hoods.com.jetexpense.presentation.navigation.JetExpenseDestination
import hoods.com.jetexpense.util.Category

class MockTransactionCallBAck(override val isFieldsNotEmpty: Boolean) : TransactionCallBack {
    override fun onTitleChange(newValue: String) {

    }

    override fun onAmountChange(newValue: String) {

    }

    override fun onDescriptionChange(newValue: String) {

    }

    override fun onCategoryChange(newValue: Category) {

    }

    override fun onTransactionTypeChange(newValue: String) {

    }

    override fun onDateChange(newValue: Long?) {

    }

    override fun onScreenTypeChange(newValue: JetExpenseDestination) {

    }

    override fun onOpenDialog(newValue: Boolean) {

    }

    override fun addIncome() {

    }

    override fun addExpense() {

    }

    override fun getIncome(id: Int) {

    }

    override fun getExpense(id: Int) {

    }

    override fun updateIncome() {

    }


    override fun updateExpense() {

    }
}