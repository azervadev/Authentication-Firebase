package com.azerva.firebaseauthentication.data

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthService @Inject constructor(private val connection: FirebaseConnection) {

    /**
     * Creates a new user account with the provided email and password.
     *
     * @param email The email for the new account.
     * @param password The password for the new account.
     * @return The authentication result as [AuthResult] or null in case of an error.
     */
    suspend fun createAccount(email: String, password: String): AuthResult? {
        return connection.mAuht.createUserWithEmailAndPassword(email, password).await()
    }

    /**
     * Verifies if the user's email is verified.
     *
     * @return True if the user's email is verified, false otherwise.
     */
    private suspend fun verifyEmailIsVerified(): Boolean {
        connection.mAuht.currentUser?.reload()?.await()
        return connection.mAuht.currentUser?.isEmailVerified ?: false
    }

    /**
     * Attempts to log in a user with the provided email and password.
     *
     * This is a suspend function, and it uses [FirebaseAuth.signInWithEmailAndPassword] for authentication.
     *
     * @param email The email associated with the user account.
     * @param password The password for the user account.
     * @return A [LoginResponse] indicating the result of the login operation.
     */
    suspend fun login(email: String, password: String): LoginResponse = runCatching {
        connection.mAuht.signInWithEmailAndPassword(email, password).await()
    }.toLoginResult()


    /**
     * Converts the result of an authentication operation to a [LoginResponse].
     *
     * @return The [LoginResponse] based on the authentication result.
     */
    private fun Result<AuthResult>.toLoginResult(): LoginResponse = when (val result = getOrNull()) {
        null -> LoginResponse.Error
        else -> LoginResponse.Success(result.user?.isEmailVerified ?: false)
    }



}