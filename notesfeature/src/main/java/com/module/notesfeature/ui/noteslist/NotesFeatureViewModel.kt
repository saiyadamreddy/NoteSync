package com.module.notesfeature.ui.noteslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.module.notesfeature.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesFeatureViewModel @Inject constructor(var noteRepository: NoteRepository) : ViewModel(){
    // Use mutableStateListOf so Compose observes changes
    val notes: StateFlow<List<String>> = noteRepository.getNotes()
        .map { list -> list.map { it.content } }
        .stateIn(viewModelScope, SharingStarted.Companion.Lazily, emptyList())

    fun addNote(text: String) {
        viewModelScope.launch {
            noteRepository.addNote(text)
        }
    }

}