package com.example.com.presentation.ui.main_fragment.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.com.data.di.PerFragment
import com.example.com.presentation.ui.main_fragment.MainFragment
import com.example.com.presentation.ui.main_fragment.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class MainFragmentModule {

    @Provides
    @PerFragment
    fun provideVMFactory(application: Application): ViewModelFactory = ViewModelFactory(application)

    @Provides
    @PerFragment
    fun provideViewModel(fragment: MainFragment, factory: ViewModelFactory) =
        ViewModelProvider(fragment, factory).get(MainViewModel::class.java)
}

class ViewModelFactory @Inject constructor(
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <VIEW_MODEL : ViewModel> create(modelClass: Class<VIEW_MODEL>): VIEW_MODEL =
        MainViewModel(application) as VIEW_MODEL
}