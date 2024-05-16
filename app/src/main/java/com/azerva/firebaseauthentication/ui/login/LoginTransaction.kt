package com.azerva.firebaseauthentication.ui.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.azerva.firebaseauthentication.core.ext.TransactionFragment

class LoginTransaction : TransactionFragment {

    private var instance : LoginFragment? = null
    override fun fragment(): Fragment {
        if (instance == null){
            instance = LoginFragment.newInstance()
        }

        return instance!!
    }

    override fun add(manager: FragmentManager, containerId: Int, tag: String): Int {
        return super.replace(manager, containerId)
    }

    override fun replace(manager: FragmentManager, containerId: Int): Int = manager.beginTransaction().replace(containerId, fragment()).commit()

    override fun show(manager: FragmentManager): Int {
        return super.show(manager)
    }

    override fun hide(manager: FragmentManager): Int {
        return super.hide(manager)
    }

    override fun remove(manager: FragmentManager): Int {
        return super.remove(manager)
    }
}