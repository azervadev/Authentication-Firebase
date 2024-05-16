package com.azerva.firebaseauthentication.viewstate

data class SignUpViewState(

    val isLoading : Boolean = false,
    val isEmailValid : Boolean = false,
    val isPasswordValid : Boolean = false,
    val isConfirmPasswordValid : Boolean = false,

) {

    fun isValidFirebaseAccount() = isPasswordValid && isEmailValid && isConfirmPasswordValid

}