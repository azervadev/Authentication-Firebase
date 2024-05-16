package com.azerva.examplefirebaseauthmvc.ui.launcher

import android.content.Context
import android.content.Intent
import com.azerva.firebaseauthentication.core.ext.TransactionActivity
import com.azerva.firebaseauthentication.ui.launcher.LauncherActivity

class LauncherTransaction : TransactionActivity {
    override fun intent(activity: Context): Intent = Intent(activity, LauncherActivity::class.java)
    override fun launch(activity: Context) {
        activity.startActivity(intent(activity))
    }
}