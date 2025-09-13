package com.module.notesfeature.ui.noteslist

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.module.notesfeature.ui.auth.AuthViewModel
import com.module.notesfeature.ui.drive.DriveViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesFeatureScreen() {
    val driveViewModel: DriveViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val signInClient = authViewModel.getGoogleSignInClient()

    NotesScreenWithSignIn(signInClient = signInClient, onAccount = { token -> token?.let {
        driveViewModel.syncNotes()
    } })

}
    @Composable
    fun NoteCard(note: String) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(
                text = note,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreenWithSignIn(signInClient: GoogleSignInClient, onAccount: (String?) -> Unit) {
    val viewModel: NotesFeatureViewModel = hiltViewModel()
    val notes by viewModel.notes.collectAsState()
    val driveViewModel: DriveViewModel = hiltViewModel()
    var showDialog by remember { mutableStateOf(false) }
    var noteText by remember { mutableStateOf(TextFieldValue("")) }

    // Launcher for Google Sign-In
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(Exception::class.java)
                onAccount(account?.idToken) // return token to ViewModel or state
            } catch (e: Exception) {
                e.printStackTrace()
                onAccount(null)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "My Toolbar") },
                actions = {
                    Button (onClick = {
                        launcher.launch(signInClient.signInIntent)
                    }) {
                        Text("Sign In")
                    }
                    IconButton(onClick = {
                        val noteContent = "My note text"
                        val fileName = "Note_${System.currentTimeMillis()}"
                        driveViewModel.uploadNoteToDrive(noteContent, fileName)
                    }) {
                        Icon(imageVector = Icons.Default.Sync, contentDescription = "Sync")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Text("+")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(notes) { note ->
                NoteCard(note = note)
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Add Note") },
            text = {
                TextField(
                    value = noteText,
                    onValueChange = { noteText = it },
                    placeholder = { Text("Enter your note") }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (noteText.text.isNotBlank()) {
                        viewModel.addNote(noteText.text)
                        noteText = TextFieldValue("") // reset
                    }
                    showDialog = false
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}