package com.example.com.presentation.ui.base

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import com.example.com.data.entity.other.AppError
import com.example.com.presentation.App
import com.example.com.presentation.router.Router

abstract class BaseViewModel(application: Application) : AndroidViewModel(application),
    LifecycleObserver {

    protected val disposable = CompositeDisposable()

    val routes = PublishSubject.create<Router.Route>()
    val errors = PublishSubject.create<AppError>()

    //region Lifecycle callbacks
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() = Unit

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() = Unit
    //endregion

    /**
     * Clean subscribes list
     */
    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    protected fun getString(@StringRes stringID: Int) = getApplication<App>().getString(stringID)
}