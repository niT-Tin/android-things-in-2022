package cn.edu.ncu.liuzehao.moviet.bridge

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

object NavigationHelper {
    private val navControllers = mutableMapOf<String, NavHostController>()

    var model = false

    fun add(key: String, controller: NavHostController) {
        if (!navControllers.containsKey(key)) {
            navControllers[key] = controller
        }
    }

    fun remove(key: String) {
        navControllers.remove(key)
    }

    fun getNavController(key: String): NavHostController? {
       return navControllers[key]
    }
}