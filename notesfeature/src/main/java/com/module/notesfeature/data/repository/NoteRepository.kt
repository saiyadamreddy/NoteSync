package com.module.notesfeature.data.repository

import com.module.notesfeature.data.db.Note
import com.module.notesfeature.data.db.NoteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor( private val noteDao: NoteDao){
    fun getNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun addNote(text: String) {
        noteDao.insertNote(Note(text = text))
    }
}