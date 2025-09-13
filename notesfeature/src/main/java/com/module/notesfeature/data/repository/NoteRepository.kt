package com.module.notesfeature.data.repository

import com.module.notesfeature.data.db.NoteDao
import com.module.notesfeature.data.db.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor( private val noteDao: NoteDao){
    fun getNotes(): Flow<List<NoteEntity>> = noteDao.getAllNotes()

    suspend fun addNote(text: String) {
        noteDao.insert(NoteEntity(content = text))
    }
}