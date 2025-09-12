package com.app.notesfeature.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.app.notesfeature.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesFeatureViewModel @Inject constructor(var noteRepository: NoteRepository) : ViewModel(){
    // Use mutableStateListOf so Compose observes changes
    private val _notes = mutableStateListOf<String>().apply {
        addAll(noteRepository.getNotes())
    }
    val notes: List<String> get() = _notes

    // Function to add a new note
    fun addNote() {
        _notes.add("Note ${_notes.size + 1}")
    }

    fun addNote(note: String) {
        _notes.add(note)
    }
}