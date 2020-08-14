package pro.onipost.libraries.base_ui.ui

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


abstract class BaseViewModel(application: Application) : AndroidViewModel(application),
    LifecycleObserver {

    protected val disposable = CompositeDisposable()

    //TODO возможно стоит переехать на LiveData
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

    //TODO придумать как заставить наследника предоставить фабрику для вью модели. Один из вариантоов инжектить фабрику и вью модель во фрагмент. Но это so-so.
    //Можно инжектить только фабрику, а вью модель доставать из фабрики. Если в ViewModelProvider всё окей, то будет только 1 инстанс вью модели
    abstract class Factory
}