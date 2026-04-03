package com.nimo.nimons360.core.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddFamily : Screen("add_family")
    object Profile : Screen("profile")

    object FamilyDetail : Screen("family_detail/{familyId}") {
        fun createRoute(familyId: String) = "family_detail/$familyId"
    }
}