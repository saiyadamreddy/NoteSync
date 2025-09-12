package com.app.notesfeature.data.repository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(){
    fun getNotes(): List<String> {
        return listOf("Note 1", "Note 2", "Note 3")
    }
}