package com.example.com.data.rxbus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxBus {
    private val bus = PublishSubject.create<Any>()

    fun post(event: Any) {
        this.bus.onNext(event)
    }

    fun <T> subscribe(eventType: Class<T>): Observable<T> = this.bus.ofType(eventType)
}
