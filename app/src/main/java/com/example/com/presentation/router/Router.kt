package com.example.com.presentation.router

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import io.reactivex.functions.Consumer

class Router {

    private lateinit var navController: NavController

    fun bindNavController(controller: NavController) {
        navController = controller
    }

    fun handle(fragment: Fragment): Consumer<Route> = Consumer {
        if (it.type == RouteDestination.POP_BACK_STACK) {
            if (!navController.popBackStack()) fragment.requireActivity().finish()
        } else {
            navController.navigate(it.destination, it.bundle)
        }
    }

    data class Route(val type: RouteDestination, val bundle: Bundle? = null) {

        val destination get() = type.action
    }

}

enum class RouteDestination(val action: Int) {
    POP_BACK_STACK(0),
}