package com.azerva.firebaseauthentication.core.utils

import javax.inject.Inject

class ValidateFields @Inject constructor() {

    fun isValidField(value : String): Boolean = value.isNotEmpty()

    /**
     * Checks if the email is valid.
     *
     * @param email The email address to validate.
     * @return `true` if the email is valid, `false` otherwise.
     */
    fun isEmailValid(email: String): Boolean {
        return isEmailPatternValid(email) || isValidField(email)
    }

    /**
     * Checks if the email pattern is valid.
     *
     * @param email The email address to validate.
     * @return `true` if the email pattern is valid, `false` otherwise.
     */
    private fun isEmailPatternValid(email: String): Boolean {
        val emailPattern = "^.+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"
        return email.matches(emailPattern.toRegex())
    }


    fun isPasswordValid(password : String) : Boolean = password.length >= 8

    fun isConfirmPasswordValid(password : String, confirmPassword : String) : Boolean =
        confirmPassword == password

}