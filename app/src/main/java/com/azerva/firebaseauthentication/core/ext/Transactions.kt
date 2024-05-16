package com.azerva.firebaseauthentication.core.ext

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Interface que se utilizará para la transacción entre actividades
 * Interface to be used for the transaction between activities
 */

interface TransactionActivity {

    // Activity

    fun intent(activity: Context): Intent

    fun launch(activity: Context) = activity.startActivity(intent(activity))

}

/**
 * Interface que se utilizará para la transacción entre fragmentos
 * Interface to be used for the transaction between fragments
 */
interface TransactionFragment {

    // Fragment

    fun fragment() : Fragment

    fun add(manager: FragmentManager, containerId: Int, tag: String) = manager.beginTransaction().add(containerId, fragment(), tag).commitAllowingStateLoss()

    fun replace(manager: FragmentManager, containerId: Int) = manager.beginTransaction().replace(containerId, fragment()).commit()

    fun show(manager: FragmentManager) = manager.beginTransaction().show(fragment()).commitAllowingStateLoss()

    fun hide(manager: FragmentManager) = manager.beginTransaction().hide(fragment()).commitAllowingStateLoss()

    fun remove(manager: FragmentManager) = manager.beginTransaction().remove(fragment()).commitAllowingStateLoss()

}