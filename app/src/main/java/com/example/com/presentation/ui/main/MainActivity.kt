package com.example.com.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.com.R
import com.example.com.databinding.ActivityMainBinding
import com.example.com.presentation.router.Router
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    private var binding: ActivityMainBinding? = null

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        router.bindNavController(Navigation.findNavController(this, R.id.nav_host_fragment))
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun androidInjector(): AndroidInjector<Any> = fragmentInjector

}