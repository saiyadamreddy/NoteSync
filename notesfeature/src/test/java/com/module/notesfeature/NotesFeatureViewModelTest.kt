package com.module.notesfeature

import com.module.notesfeature.data.repository.NoteRepository
import com.module.notesfeature.ui.noteslist.NotesFeatureViewModel
import io.mockk.coEvery
import io.mockk.coVerify // For verifying coroutine suspending functions
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.StandardTestDispatcher // For testing
import kotlinx.coroutines.test.resetMain // To reset the main dispatcher after the test
import kotlinx.coroutines.test.runTest // The main way to run tests with coroutines
import kotlinx.coroutines.test.setMain // To set the main dispatcher to a test dispatcher
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi // Required for using TestDispatchers
class NotesFeatureViewModelTest {

    // 1. Create a TestDispatcher
    private val testDispatcher = StandardTestDispatcher()

    // Mock the repository
    private lateinit var mockNoteRepository: NoteRepository
    private lateinit var viewModel: NotesFeatureViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockNoteRepository = mockk()

        // Stub for getNotes (called during ViewModel init)
        every { mockNoteRepository.getNotes() } returns emptyFlow()

        // **NEW: Stub for addNote**
        // If addNote is a suspend function that returns Unit, use coEvery { ... } returns Unit
        // Use `any()` for the string argument if you don't care about the specific value
        // during the stubbing phase (you'll verify the exact value later).
        // Or be specific if needed: coEvery { mockNoteRepository.addNote("some specific string") } returns Unit
        coEvery { mockNoteRepository.addNote(any()) } returns Unit // <--- ADD THIS

        viewModel = NotesFeatureViewModel(mockNoteRepository)

    }

    @Test
    fun `addNote_calls_repository_to_add_note`() = runTest(testDispatcher) {
        val noteText = "This is a test note"
        viewModel.addNote(noteText)
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify { mockNoteRepository.addNote(eq(noteText)) } // eq() is good for verification
    }

    @Test
    fun `addNote_with_empty_text_calls_repository_with_empty_text`() = runTest(testDispatcher) {
        val noteText = ""
        viewModel.addNote(noteText)
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify { mockNoteRepository.addNote(eq(noteText)) }

    }

    @After
    fun tearDown() {
        // 3. Reset the main dispatcher
        Dispatchers.resetMain()
    }
}

