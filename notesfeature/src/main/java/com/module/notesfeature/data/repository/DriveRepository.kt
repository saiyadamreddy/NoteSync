package com.module.notesfeature.data.repository

import com.module.notesfeature.data.db.NoteDao
import com.module.notesfeature.data.remote.api.DriveApiService
import javax.inject.Inject

class DriveRepository @Inject constructor(
    private val api: DriveApiService,
    private val dao: NoteDao
) {
    suspend fun uploadFile(metadata: okhttp3.RequestBody, file: okhttp3.MultipartBody.Part) =
        api.uploadFile(metadata, file)

    suspend fun listFiles(query: String? = null) =
        api.listFiles(query)


}