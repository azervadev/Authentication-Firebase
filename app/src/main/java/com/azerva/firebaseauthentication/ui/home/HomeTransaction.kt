package com.azerva.firebaseauthentication.ui.home

import android.content.Context
import android.content.Intent
import com.azerva.firebaseauthentication.core.ext.TransactionActivity

class HomeTransaction : TransactionActivity {
    override fun intent(activity: Context): Intent = Intent(activity, HomeActivity::class.java)
    override fun launch(activity: Context) {
        activity.startActivity(intent(activity))
    }
}