package com.azerva.firebaseauthentication.viewstate

data class LoginViewState(

    val isLoading : Boolean = false,
    val isUserEmailValid : Boolean = false,
    val isUserPasswordValid : Boolean = false,
)
