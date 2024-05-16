package com.azerva.firebaseauthentication.data

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseConnection @Inject constructor(){

    // Inicializa Firebase Authentication
    val mAuht = FirebaseAuth.getInstance()

}