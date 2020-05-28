package com.example.com.presentation.di

import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import com.example.com.data.di.PerActivity
import com.example.com.data.di.PerFragment
import com.example.com.presentation.ui.main.MainActivity
import com.example.com.presentation.ui.main_fragment.MainFragment
import com.example.com.presentation.ui.main_fragment.di.MainFragmentModule

@Module(includes = [AndroidInjectionModule::class])
abstract class UiInjects {

    @PerActivity
    @ContributesAndroidInjector
    abstract fun contributeActivityAndroidInjector(): MainActivity

    @PerFragment
    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun bindMainFragment(): MainFragment
}