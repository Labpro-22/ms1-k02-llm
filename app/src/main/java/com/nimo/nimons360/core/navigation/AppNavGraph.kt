package com.nimo.nimons360.core.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nimo.nimons360.core.navigation.Screen
//import com.nimo.nimons360.feature.home.HomeRoute

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
//        composable(Screen.Home.route) {
//            HomeRoute(
//                onNavigateToFamilyDetail = { familyId ->
//                    navController.navigate(Screen.FamilyDetail.createRoute(familyId))
//                }
//            )
//        }

        composable(
            route = Screen.FamilyDetail.route,
            arguments = listOf(
                navArgument("familyId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val familyId = backStackEntry.arguments?.getString("familyId") ?: ""
            Text(text = "Halaman Detail untuk ID: $familyId")
        }
    }
}