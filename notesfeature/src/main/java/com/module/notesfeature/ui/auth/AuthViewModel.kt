package com.module.notesfeature.ui.auth


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class AuthViewModel(app: Application) : AndroidViewModel(app) {

    fun getGoogleSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(
                com.google.android.gms.common.api.Scope("https://www.googleapis.com/auth/drive.file")
            )
            .requestIdToken("YOUR_WEB_CLIENT_ID") // from Google Cloud Console
            .build()
        return GoogleSignIn.getClient(getApplication(), gso)
    }

    fun getAccount(): GoogleSignInAccount? =
        GoogleSignIn.getLastSignedInAccount(getApplication())
}
