package com.example.com.presentation.router

import android.os.Bundle
import io.reactivex.subjects.PublishSubject

fun PublishSubject<Route>.toDestination(destination: RouteDestination, bundle: Bundle? = null) {
    onNext(Route(destination, bundle))
}

fun PublishSubject<Route>.popBackStack() {
    onNext(Route(RouteDestination.POP_BACK_STACK))
}