package com.app.notesfeature.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.app.notesfeature.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesFeatureViewModel @Inject constructor(var noteRepository: NoteRepository) : ViewModel(){
    fun getNotes() = noteRepository.getNotes()
}