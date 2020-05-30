package com.example.com.presentation.ui.base

import android.app.Application
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

abstract class PaginatedViewModel(application: Application) : BaseViewModel(application) {

    protected val paginator = PublishSubject.create<Boolean>()
    protected var loadLock: Boolean = false

    var isEmpty = BehaviorSubject.createDefault(false)
    var isRefresh = BehaviorSubject.createDefault(false)
    val data = BehaviorSubject.create<List<Any>>()
    var start = 0

    init {
        disposable.add(paginator.filter { !loadLock }.subscribe { fetch() })
    }

    abstract fun fetch()

    abstract fun refresh()

    fun nextPage() {
        paginator.onNext(true)
    }
}