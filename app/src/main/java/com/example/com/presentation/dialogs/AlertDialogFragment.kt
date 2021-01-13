package com.example.com.presentation.dialogs

import androidx.fragment.app.DialogFragment

class AlertDialogFragment : DialogFragment() {

    // private var listener: AlertDialogClickListener? = null
    // private var code = DEFAULT_CODE
    private var title = ""
    private var message = ""
    private var positiveButtonText = ""
    private var negativeButtonText = ""

    /*override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        arguments?.let { unpackArguments(it) }
        return MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme).apply {
            if (title.isNotEmpty()) setTitle(title)
            if (message.isNotEmpty()) setMessage(message)
            if (positiveButtonText.isNotEmpty()) {
                setPositiveButton(positiveButtonText) { _, _ -> listener?.onConfirm(code) }
            }
            if (negativeButtonText.isNotEmpty()) {
                setNegativeButton(negativeButtonText) { _, _ -> listener?.onDecline(code) }
            }
        }.show()
    }

    private fun unpackArguments(arguments: Bundle) {
        title = arguments.getString(TITLE_KEY, "")
        message = arguments.getString(MESSAGE_KEY, "")
        positiveButtonText = arguments.getString(CONFIRM_KEY, "")
        negativeButtonText = arguments.getString(DECLINE_KEY, "")
        code = arguments.getInt(DIALOG_CODE_KEY, DEFAULT_CODE)
        listener = arguments.getSerializable(LISTENER_KEY) as? AlertDialogClickListener
    }

    companion object {

        private const val DIALOG_CODE_KEY = "DIALOG_CODE"
        private const val TITLE_KEY = "TITLE_KEY"
        private const val CONFIRM_KEY = "CONFIRM_KEY"
        private const val DECLINE_KEY = "DECLINE_KEY"
        private const val MESSAGE_KEY = "MESSAGE_KEY"
        private const val LISTENER_KEY = "LISTENER_KEY"
        private const val DEFAULT_CODE = 0
    }

    class Builder(private val context: Context, code: Int) {

        private val bundle = Bundle()

        init {
            bundle.putInt(DIALOG_CODE_KEY, code)
        }

        fun withTitle(@StringRes resource: Int): Builder =
            this.apply { bundle.putString(TITLE_KEY, context.getString(resource)) }


        fun withTitle(text: String): Builder =
            this.apply { bundle.putString(TITLE_KEY, text) }

        fun withMessage(@StringRes resource: Int): Builder =
            this.apply { bundle.putString(MESSAGE_KEY, context.getString(resource)) }

        fun withMessage(text: String): Builder =
            this.apply { bundle.putString(MESSAGE_KEY, text) }

        fun withDecline(@StringRes resource: Int): Builder =
            this.apply { bundle.putString(DECLINE_KEY, context.getString(resource)) }

        fun withConfirm(@StringRes resource: Int): Builder =
            this.apply { bundle.putString(CONFIRM_KEY, context.getString(resource)) }

        fun withListener(listener: AlertDialogClickListener): Builder =
            this.apply { bundle.putSerializable(LISTENER_KEY, listener) }

        fun build(): AlertDialogFragment = AlertDialogFragment()
            .apply { arguments = bundle }
    }

    interface AlertDialogClickListener : Serializable {

        fun onConfirm(code: Int)

        fun onDecline(code: Int)
    }

    interface ConfirmAlertDialogClickListener :
        AlertDialogClickListener {

        override fun onDecline(code: Int) = Unit
    }

    interface DeclineAlertDialogClickListener :
        AlertDialogClickListener {

        override fun onConfirm(code: Int) = Unit
    }*/
}
