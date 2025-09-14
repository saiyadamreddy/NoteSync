package com.module.notesfeature.ui.notesdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteId: String?, // The ID passed from the list screen
    onNavigateUp: () -> Unit,
    // viewModel: NoteDetailViewModel = hiltViewModel() // If you have a ViewModel
) {
    // Example: Fetch note details using the noteId if you have a ViewModel
    // LaunchedEffect(noteId) {
    //     noteId?.let { viewModel.loadNote(it) }
    // }
    // val noteDetail by viewModel.noteDetail.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Note Detail") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (noteId != null) {
                Text("Displaying details for Note ID: $noteId", style = MaterialTheme.typography.headlineSmall)
                // Here you would display the actual content of the note
                // For example, if you fetched 'noteDetail' from a ViewModel:
                // noteDetail?.let { Text(it.content, style = MaterialTheme.typography.bodyLarge) }
            } else {
                Text("Note not found.", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
