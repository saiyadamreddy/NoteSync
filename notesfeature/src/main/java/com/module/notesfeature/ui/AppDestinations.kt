package com.module.notesfeature.ui

// Example: In your MainActivity.kt or a similar entry point for Compose UI

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.module.notesfeature.ui.notesdetails.NoteDetailScreen // You'll create this
import com.module.notesfeature.ui.noteslist.NotesFeatureScreen

// Define route constants
object AppDestinations {
    const val NOTES_LIST_ROUTE = "notesList"
    const val NOTE_DETAIL_ROUTE = "noteDetail"
    const val NOTE_ID_ARG = "noteId" // Argument name for passing note ID
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.NOTES_LIST_ROUTE
    ) {
        composable(AppDestinations.NOTES_LIST_ROUTE) {
            NotesFeatureScreen(
                onNoteClick = { noteId -> // You'll pass this lambda down
                    // Navigate to detail screen, passing the noteId
                    navController.navigate("${AppDestinations.NOTE_DETAIL_ROUTE}/$noteId")
                }
                // ... other parameters for NotesFeatureScreen if any
            )
        }

        composable(
            route = "${AppDestinations.NOTE_DETAIL_ROUTE}/{${AppDestinations.NOTE_ID_ARG}}",
            arguments = listOf(navArgument(AppDestinations.NOTE_ID_ARG) {
                type = NavType.StringType // Or NavType.LongType if your ID is Long
                // nullable = true or defaultValue = "..." can also be set
            })
        ) { backStackEntry ->
            // Retrieve the noteId argument
            val noteId = backStackEntry.arguments?.getString(AppDestinations.NOTE_ID_ARG)
            NoteDetailScreen(
                noteId = noteId,
                onNavigateUp = { navController.navigateUp() } // For back navigation
            )
        }
        // ... other destinations
    }
}
