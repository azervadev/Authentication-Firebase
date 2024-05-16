package com.azerva.firebaseauthentication.ui.launcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.azerva.firebaseauthentication.ui.login.LoginTransaction
import com.azerva.firebaseauthentication.R
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LoginTransaction().replace(supportFragmentManager, R.id.launcher_container)

    }

}