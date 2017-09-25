package com.nunez.androidcodetestpaulnunez.views

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import java.util.*

/**
 * Created by paulnunez on 9/25/17.
 */
class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    var listener: ((String) -> Unit)? = null

    fun setDateSelecterListener(listener: (String) -> Unit){
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
       listener?.invoke("$month - $dayOfMonth - $year")
    }
}