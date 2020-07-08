package com.example.com.presentation.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.com.BR
import com.example.com.R
import com.example.com.presentation.router.Router
import com.example.com.data.utils.doBackgroundObserveMain
import com.example.com.presentation.dialogs.AlertDialogFragment
import com.example.com.presentation.router.popBackStack
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


abstract class BaseFragment<VIEW_MODEL : BaseViewModel, BINDING : ViewDataBinding> : Fragment() {

    @Inject
    protected lateinit var viewModel: VIEW_MODEL

    @Inject
    protected lateinit var router: Router
    protected lateinit var binding: BINDING

    protected abstract val layoutId: Int

    protected var disposable = CompositeDisposable()
    private var toast: Toast? = null

    private val backPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            onBackPressed()
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
        backPressedCallback.isEnabled = backPressedOverrided()
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
        subscribeOnViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        if (!binding.setVariable(BR.viewModel, viewModel)) {
            throw RuntimeException("Layout XML resource should contain data variable with name='viewModel'")
        }
        return binding.root
    }

    override fun onDestroy() {
        unsubscribeFromViewModel()
        backPressedCallback.isEnabled = false
        backPressedCallback.remove()
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }

    /**
     * All view model subscriptions must be in this method
     */
    @CallSuper
    protected open fun subscribeOnViewModel() {
        with(disposable) {

            //Subscribe on routes in view model
            add(viewModel.routes.subscribe(router.handle(this@BaseFragment)))

            //Subscribe on errors, this must call toast or alert dialog
            add(viewModel.errors.doBackgroundObserveMain().subscribe {
                if (it.isCritical) showErrorAlert(it.message) else showToast(it.message)
            })
        }
    }

    /**
     * Method for clear disposable
     */
    @CallSuper
    protected open fun unsubscribeFromViewModel() = disposable.dispose()

    /**
     * Pass onBackPressed event to router
     */
    open fun onBackPressed() {
        viewModel.routes.popBackStack()
    }

    private fun showToast(error: String, length: Int = Toast.LENGTH_LONG) {
        if (!isDetached) {
            toast?.cancel()
            toast = Toast.makeText(requireContext(), error, length)
            toast?.show()
        }
    }

    protected fun showErrorAlert(error: String) {
        if (!isDetached && !isStateSaved) {
            AlertDialogFragment.Builder(requireContext(), 200)
                .withTitle("Error")
                .withMessage(error)
                .withConfirm(R.string.app_name)
                .build()
                .show(childFragmentManager, DIALOG_ERROR_TAG)
        }
    }

    protected fun showAlert(
        @StringRes title: Int = 0,
        @StringRes message: Int = 0,
        @StringRes confirm: Int = 0,
        @StringRes decline: Int = 0,
        code: Int = 0,
        tag: String = this::class.java.name,
        listener: AlertDialogFragment.AlertDialogClickListener? = null
    ) {
        val messageString = if (message != 0) getString(message) else ""
        showAlert(title, messageString, confirm, decline, code, tag, listener)
    }

    protected fun showAlert(
        @StringRes title: Int = 0,
        message: String = "",
        @StringRes confirm: Int = 0,
        @StringRes decline: Int = 0,
        code: Int = 0,
        tag: String = this::class.java.name,
        listener: AlertDialogFragment.AlertDialogClickListener? = null
    ) {
        val builder = AlertDialogFragment.Builder(requireContext(), code)
        if (title != 0) builder.withTitle(title)
        if (message != "") builder.withMessage(message)
        if (confirm != 0) builder.withConfirm(confirm)
        if (decline != 0) builder.withDecline(decline)
        if (listener != null) builder.withListener(listener)
        builder.build().show(childFragmentManager, tag)
    }

    private fun backPressedOverrided() =
        this::class.java.declaredMethods.find { it.name == "onBackPressed" } != null

    companion object {
        const val DIALOG_ERROR_TAG = "DIALOG_ERROR_TAG"
    }
}