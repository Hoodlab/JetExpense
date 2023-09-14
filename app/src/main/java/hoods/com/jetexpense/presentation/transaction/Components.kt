package hoods.com.jetexpense.presentation.transaction

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.util.*

@Composable
fun datePickerDialog(context: Context, onDateSelected: (Date) -> Unit): DatePickerDialog {
    // Initializing a Calendar
    val calendar = Calendar.getInstance()
    // Fetching current year, month and day
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()
    // Declaring a string value to
    // store date in string format
    val selectedDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            val calender = Calendar.getInstance()
            calender.set(mYear, mMonth, mDayOfMonth)
            onDateSelected.invoke(calender.time)
        }, year, month, day
    )
    return mDatePickerDialog
}

