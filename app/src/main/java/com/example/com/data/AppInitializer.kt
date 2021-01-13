package com.example.com.data

import android.content.Context
import com.example.com.data.entity.other.InitializationStatus
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Named

// TODO add to constructor some use cases for application initialization.
class AppInitializer @Inject constructor(
    @Named("app_context")
    private val context: Context,
    private val initStatus: BehaviorSubject<InitializationStatus>
) {
    private var initDisposable: Disposable? = null

    /**
     * Base method for application initialize.
     * This method good for download data of application or initialize some libraries.
     * After success need call [initStatus.success] or [initStatus.successNoSession]
     */
    fun init() {}
}
