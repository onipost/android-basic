package com.example.com.presentation.ui.main_fragment

import com.example.com.R
import com.example.com.databinding.FragmentMainBinding
import com.example.com.presentation.ui.base.BaseFragment

class MainFragment: BaseFragment<MainViewModel, FragmentMainBinding>() {

    override val layoutId: Int get() = R.layout.fragment_main

    override val viewModelClass: Class<MainViewModel> get() = MainViewModel::class.java

}