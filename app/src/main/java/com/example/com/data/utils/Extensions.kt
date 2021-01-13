package com.example.com.data.utils

import android.content.Context
import com.example.com.data.entity.other.AppError
import com.example.com.data.entity.other.InitializationStatus
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

fun <T> Flowable<T>.doBackgroundObserveMain(): Flowable<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.doBackgroundObserveMain(): Single<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.doBackgroundObserveMain(): Observable<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun BehaviorSubject<InitializationStatus>.error(message: String) {
    this.onNext(InitializationStatus(InitializationStatus.Status.ERROR, message))
}

fun BehaviorSubject<InitializationStatus>.error(context: Context, message: Int) {
    this.onNext(InitializationStatus(InitializationStatus.Status.ERROR, context.getString(message)))
}

fun BehaviorSubject<InitializationStatus>.success() {
    this.onNext(InitializationStatus(InitializationStatus.Status.INITIALIZED))
}

fun BehaviorSubject<InitializationStatus>.successNoSession() {
    this.onNext(InitializationStatus(InitializationStatus.Status.INITIALIZED_NO_SESSION))
}

fun BehaviorSubject<InitializationStatus>.inProgress() {
    this.onNext(InitializationStatus(InitializationStatus.Status.NOT_INITIALIZED))
}

fun PublishSubject<AppError>.showError(string: String, isCritical: Boolean = false) {
    this.onNext(AppError.buildError(string, isCritical))
}
