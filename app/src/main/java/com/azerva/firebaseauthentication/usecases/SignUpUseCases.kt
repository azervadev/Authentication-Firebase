package com.azerva.firebaseauthentication.usecases

import com.azerva.firebaseauthentication.data.FirebaseAuthService
import com.azerva.firebaseauthentication.model.SignUp
import javax.inject.Inject

class SignUpUseCases @Inject constructor(private val authService: FirebaseAuthService) {

    suspend operator fun invoke(signUp: SignUp) : Boolean = runCatching{
        return@runCatching authService.createAccount(signUp.email, signUp.password)
    }.isSuccess


}