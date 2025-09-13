package com.module.notesfeature.ui.drive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.module.notesfeature.data.repository.DriveRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class DriveViewModel @Inject constructor(private val repo: DriveRepository) : ViewModel() {

    fun syncNotes() {
        viewModelScope.launch {
            try {
                val files = repo.listFiles()
                // handle files list
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun uploadNoteToDrive(noteContent: String, fileName: String) {
        viewModelScope.launch {
            try {
                val metadata = createMetadataJson(fileName)
                val filePart = createNoteFile(noteContent, fileName)

                val response = repo.uploadFile(metadata, filePart)
                println("Upload response: $response")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun createNoteFile(noteContent: String, fileName: String): MultipartBody.Part {
        // Create RequestBody for file content
        val requestBody = noteContent.toRequestBody("text/plain".toMediaType())

        // Create MultipartBody.Part for uploading
        return MultipartBody.Part.createFormData("file", "$fileName.txt", requestBody)
    }

    fun createMetadataJson(fileName: String): RequestBody {
        val json = """
        {
            "name": "$fileName",
            "mimeType": "text/plain"
        }
    """.trimIndent()

        return json.toRequestBody("application/json".toMediaType())
    }
}
