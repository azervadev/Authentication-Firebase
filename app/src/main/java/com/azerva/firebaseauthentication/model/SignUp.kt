package com.azerva.firebaseauthentication.model

/**
 * The `Register` data class represents the information needed to register a new user.
 *
 * @param email The user's email address.
 * @param password The user's password.
 * @param passwordConfirmation The user's password confirmation.
 *
 * @constructor Creates an instance of [SignUp] with the information provided.
 */
data class SignUp (
    val id :String ?= null,
    val email: String,
    val password: String,
    val passwordConfirmation: String
){
    /**
     * Check if all fields of [SignUp] are not empty.
     *
     * @return `true` if all fields are not empty, `false` otherwise.
     */
    fun isNotEmpty() =
        email.isNotEmpty() && password.isNotEmpty() && passwordConfirmation.isNotEmpty()


}
