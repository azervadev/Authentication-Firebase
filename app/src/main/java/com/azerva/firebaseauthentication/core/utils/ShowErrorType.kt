package com.azerva.firebaseauthentication.core.utils

import androidx.lifecycle.MutableLiveData


class ShowErrorType (showError: MutableLiveData<Boolean> = MutableLiveData(false)) {

    // Propiedad para controlar la visibilidad del diálogo de error
    private val _showError = showError
    private lateinit var errorType : ErrorType

    // Método para restablecer la propiedad _showErrorDialog después de mostrar el diálogo
    fun resetDialog() {
        _showError.value = false
    }

    fun showErrorDialog(errorType: ErrorType) {
        // Guarda el tipo de error
        this.errorType = errorType
        // Notifica al fragmento que debe mostrar el diálogo
        _showError.value = true
    }
}