# NoteSync

A modern Android note-taking app demonstrating **MVVM architecture**, **Hilt dependency injection**, **Room database**, **Jetpack Compose UI**, and **Google Drive integration** for cloud sync.  

---

## Features

- Add, edit, and delete notes locally using **Room database**
- Offline support: notes are stored locally and synced when online
- Sync notes to **Google Drive** using Retrofit + Coroutines
- **Google Sign-In** for authentication and secure Drive access
- Clean **Jetpack Compose UI** with Toolbar, FAB, and Material Design
- Hilt for dependency injection and modular architecture
- Modular project structure: `app` + `notesfeature` library module


## Architecture

- **MVVM (Model-View-ViewModel)**
- **Repository pattern** for data abstraction
- **Room** for local persistence
- **Retrofit** with **AuthInterceptor** for Google Drive API
- **Jetpack Compose** for UI
- **Hilt** for DI across app and feature modules

---

## Modules

- `app`: Hosts main activity and Compose screens
- `notesfeature`: Feature module containing:
  - Room database (`NoteDatabase`, `NoteDao`)
  - Repositories (`NoteRepository`, `GoogleDriveRepository`)
  - ViewModels (`NotesViewModel`, `GoogleDriveViewModel`)
  - Retrofit API (`GoogleDriveApi`)

---

## Dependencies

- Jetpack Compose: UI toolkit
- Room: Local database
- Hilt: Dependency injection
- Retrofit + OkHttp: Google Drive API
- Google Play Services Auth: Google Sign-In
