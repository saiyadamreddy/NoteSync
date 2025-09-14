package com.app.notesync


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule // Or createComposeRule if no Activity context needed directly
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


// @HiltAndroidTest // Uncomment if using Hilt for testing
@RunWith(AndroidJUnit4::class)
class NotesFeatureScreenTest {

    // Use createAndroidComposeRule if your @Composable is part of an Activity and needs Activity context
    // or if you need to launch an Activity that hosts your Composable.
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>() // Replace with your actual Activity

    private lateinit var mockGoogleSignInClient: GoogleSignInClient

    @Before
    fun setUp() {
        mockGoogleSignInClient = mockk(relaxed = true) // Relaxed mock for simplicity

    }

    @Test
    fun initialUIShouldBeDisplayed() {
        // Check for TopAppBar title (adjust "My Toolbar" if it's dynamic)
        composeTestRule.onNodeWithText("My Toolbar").assertIsDisplayed()
        // Check for "Sign In" button
        composeTestRule.onNodeWithText("Sign In").assertIsDisplayed()
        // Check for Sync IconButton (using content description)
        composeTestRule.onNodeWithContentDescription("Sync").assertIsDisplayed()
        composeTestRule.onNodeWithText("+").assertIsDisplayed() // Assuming the FAB text is "+"
    }

    @Test
    fun clickingFAB_shouldShowAddNoteDialog() {
        composeTestRule.onNodeWithText("+").performClick() // Click FAB
        // Check if dialog title is displayed
        composeTestRule.onNodeWithText("Add Note").assertIsDisplayed()
        // Check for the TextField placeholder
        composeTestRule.onNodeWithText("Enter your note").assertIsDisplayed()
        // Check for "Add" and "Cancel" buttons in the dialog
        composeTestRule.onNodeWithText("Add", useUnmergedTree = true).assertIsDisplayed() // useUnmergedTree for dialogs
        composeTestRule.onNodeWithText("Cancel", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun addNoteDialog_clickingCancel_shouldDismissDialog() {
        // First, open the dialog
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("Add Note").assertIsDisplayed() // Ensure dialog is open
        // Click "Cancel"
        composeTestRule.onNodeWithText("Cancel", useUnmergedTree = true).performClick()
        // Assert dialog is no longer displayed
        composeTestRule.onNodeWithText("Add Note").assertDoesNotExist()
    }

    @Test
    fun addNoteDialog_enteringTextAndClickingAdd_shouldDismissDialogAndAddNote() {

        val newNoteText = "Test note from UI test"
        // 1. Open the dialog
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("Enter your note") // Find by placeholder
            .performTextInput(newNoteText)

        // 3. Click "Add"
        composeTestRule.onNodeWithText("Add", useUnmergedTree = true).performClick()

        // 4. Assert dialog is dismissed
        composeTestRule.onNodeWithText("Add Note").assertDoesNotExist()

        // 5. Assert the new note is displayed in the list
        // This assumes your ViewModel updates the UI and the note appears.
        // You might need to scroll if the list is long.
        composeTestRule.onNodeWithText(newNoteText).assertIsDisplayed()
    }

}
