package com.azerva.firebaseauthentication.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.azerva.examplefirebaseauthmvc.ui.launcher.LauncherTransaction
import com.azerva.firebaseauthentication.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeBtnLogOut.setOnClickListener {
            LauncherTransaction().launch(this@HomeActivity)
        }

    }
}