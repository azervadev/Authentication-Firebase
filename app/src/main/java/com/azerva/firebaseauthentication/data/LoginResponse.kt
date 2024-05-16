package com.azerva.firebaseauthentication.data

sealed class LoginResponse {
    /**
     * Subtipo de [LoginResponse] que indica un error durante el proceso de inicio de sesión.
     * Se utiliza cuando el inicio de sesión no fue exitoso.
     */
    data object Error : LoginResponse()

    /**
     * Subtipo de [LoginResponse] que indica un inicio de sesión exitoso.
     *
     * @property verified Indica si la cuenta asociada al inicio de sesión está verificada.
     */
    data class Success(val verified: Boolean) : LoginResponse()
}