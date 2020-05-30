package com.example.com.presentation.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.com.BR
import com.example.com.presentation.router.Router
import com.example.com.data.utils.doBackgroundObserveMain
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment<VIEW_MODEL : BaseViewModel, BINDING : ViewDataBinding> : Fragment() {

    @Inject
    protected lateinit var viewModel: VIEW_MODEL
    @Inject
    protected lateinit var router: Router

    protected lateinit var binding: BINDING
    protected var disposable = CompositeDisposable()
    private var toast: Toast? = null

    protected abstract val layoutId: Int
    protected abstract val viewModelClass: Class<VIEW_MODEL>

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
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
            add(viewModel.routes.subscribe { router.pushRoute(it) })

            //Subscribe on errors, this must call toast or alert dialog
            add(viewModel.errors.doBackgroundObserveMain().subscribe {
                if (it.isCritical) showAlert(it.message) else showToast(it.message)
            })
        }
    }

    /**
     * Method for clear disposable
     */
    @CallSuper
    protected open fun unsubscribeFromViewModel() = disposable.clear()

    /**
     * Pass onBackPressed event to router
     */
    @CallSuper
    open fun onBackPressed() = router.back()

    protected fun showToast(error: String, length: Int = Toast.LENGTH_LONG) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), error, length)
        toast?.show()
    }

    protected fun showAlert(error: String) {
        //Implement to show dialog alert
    }
}