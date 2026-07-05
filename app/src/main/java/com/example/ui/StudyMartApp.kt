package com.example.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.SignUpScreen
import com.example.ui.screens.CreateSellerScreen
import com.example.ui.screens.NoteDetailScreen
import com.example.ui.screens.PaymentVerificationScreen
import com.example.ui.screens.MyNotesScreen

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.viewmodel.AppViewModelProvider
import com.example.ui.viewmodel.StudyMartViewModel

@Composable
fun StudyMartApp(modifier: Modifier = Modifier) {
    val viewModel: StudyMartViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier.fillMaxSize()
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onSignUpClick = {
                    navController.navigate("signup")
                },
                onCreateSellerClick = {
                    navController.navigate("create_seller")
                }
            )
        }
        composable("signup") {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }
        composable("create_seller") {
            CreateSellerScreen(
                onCreateSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onNoteClick = { noteId ->
                    navController.navigate("note_detail/$noteId")
                },
                onMyNotesClick = {
                    navController.navigate("my_notes")
                }
            )
        }
        composable("my_notes") {
            MyNotesScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNoteClick = { noteId ->
                    navController.navigate("note_detail/$noteId")
                }
            )
        }
        composable(
            route = "note_detail/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.StringType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId") ?: ""
            NoteDetailScreen(
                viewModel = viewModel,
                noteId = noteId,
                onBack = { navController.popBackStack() },
                onBuyClick = { id ->
                    navController.navigate("payment/$id")
                }
            )
        }
        composable(
            route = "payment/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.StringType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId") ?: ""
            PaymentVerificationScreen(
                viewModel = viewModel,
                noteId = noteId,
                onBack = { navController.popBackStack() },
                onPaymentSubmitted = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
    }
}
