package com.azerva.firebaseauthentication.usecases

import com.azerva.firebaseauthentication.data.FirebaseAuthService
import com.azerva.firebaseauthentication.data.LoginResponse
import javax.inject.Inject

class LoginUseCases @Inject constructor(private val authService: FirebaseAuthService) {

    suspend operator fun invoke(email :String, password :String) : LoginResponse = authService.login(email = email, password = password)
}