package com.example.com.presentation.router

import androidx.navigation.NavController

class Router {

    var navController: NavController? = null

    fun bindNavController(controller: NavController) {
        navController = controller
    }

    fun pushRoute(route: Routes) {
        navController?.navigate(route.action)
    }

    fun back() {
        navController?.popBackStack()
    }
}