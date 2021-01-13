package com.example.com.presentation.dialogs

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.io.Serializable
import java.util.Calendar

class DateTimePickerDialogFragment :
    DialogFragment(),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private var listener: DateTimePickerDialogSelectListener? = null
    private var calendar = Calendar.getInstance()
    private var isNeedTime = false
    private var isNeedDate = true
    private var code = DEFAULT_CODE

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        arguments?.let { unpackArguments(it) }

        return when {
            isNeedDate -> getDatePicker()
            isNeedTime -> getTimePicker()
            else -> throw IllegalArgumentException("params isNeedDate and isNeedTime is false, please set any of this as true")
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        if (isNeedTime) {
            calendar.set(Calendar.DAY_OF_MONTH, day)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.YEAR, year)
            getTimePicker().show()
        } else {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.HOUR_OF_DAY, 12)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.YEAR, year)
            listener?.onDateTimeSelected(calendar.timeInMillis)
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        listener?.onDateTimeSelected(calendar.timeInMillis)
    }

    private fun getTimePicker(): AlertDialog = TimePickerDialog(
        requireActivity(),
        this,
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        DateFormat.is24HourFormat(requireActivity())
    )

    private fun getDatePicker(): AlertDialog = DatePickerDialog(
        requireActivity(),
        this,
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
    }

    private fun unpackArguments(arguments: Bundle) {
        calendar.timeInMillis = arguments.getLong(TIMESTAMP_KEY, System.currentTimeMillis())
        isNeedDate = arguments.getBoolean(NEED_DATE_KEY, isNeedDate)
        isNeedTime = arguments.getBoolean(NEED_TIME_KEY, isNeedTime)
        code = arguments.getInt(DIALOG_CODE_KEY, DEFAULT_CODE)
        listener = arguments.getSerializable(LISTENER_KEY) as? DateTimePickerDialogSelectListener
    }

    companion object {

        private const val DIALOG_CODE_KEY = "DIALOG_CODE"
        private const val TIMESTAMP_KEY = "TIMESTAMP_KEY"
        private const val NEED_TIME_KEY = "NEED_TIME_KEY"
        private const val NEED_DATE_KEY = "NEED_DATE_KEY"
        private const val LISTENER_KEY = "LISTENER_KEY"
        private const val DEFAULT_CODE = 0
    }

    class Builder(code: Int) {

        private val bundle = Bundle()

        init {
            bundle.putInt(DIALOG_CODE_KEY, code)
        }

        fun withTimestamp(timestamp: Long): Builder =
            this.apply { bundle.putLong(TIMESTAMP_KEY, timestamp) }

        fun isNeedTime(isNeedTime: Boolean): Builder =
            this.apply { bundle.putBoolean(NEED_TIME_KEY, isNeedTime) }

        fun isNeedDate(isNeedDate: Boolean): Builder =
            this.apply { bundle.putBoolean(NEED_DATE_KEY, isNeedDate) }

        fun withListener(listener: DateTimePickerDialogSelectListener): Builder =
            this.apply { bundle.putSerializable(LISTENER_KEY, listener) }

        fun build(): DateTimePickerDialogFragment =
            DateTimePickerDialogFragment().apply { arguments = bundle }
    }

    interface DateTimePickerDialogSelectListener : Serializable {

        fun onDateTimeSelected(timestamp: Long)
    }
}
