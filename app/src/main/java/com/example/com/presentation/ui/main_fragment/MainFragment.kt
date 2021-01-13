package com.example.com.presentation.ui.main_fragment

import android.os.Bundle
import com.example.com.R
import com.example.com.databinding.FragmentMainBinding
import com.example.com.presentation.ui.base.BaseFragment

class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {

    private val a = 1

    override val layoutId: Int get() = R.layout.fragment_main

    private val b = 1

    override val viewModelClass: Class<MainViewModel> get() = MainViewModel::class.java

    private fun a() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
