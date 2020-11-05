package com.project.monopad.exception

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.material.snackbar.Snackbar
import com.project.monopad.R
import com.project.monopad.exception.NetworkStateBroadcastReceiver.Companion.IS_NETWORK_AVAILABLE
import com.project.monopad.exception.NetworkStateBroadcastReceiver.Companion.NETWORK_AVAILABLE_ACTION
import com.project.monopad.ui.view.MainActivity


class NetworkStateHelper(private val context: Context,
                         private val snackbarContainer: View,
                         lifecycleOwner: LifecycleOwner,
                         stringId: Int = R.string.network_false,
                         duration: Int = Snackbar.LENGTH_INDEFINITE,
                         private val callback: ((Boolean) -> Unit)? = null)
    : LifecycleObserver {

    private val networkChangeReceiver = NetworkStateBroadcastReceiver()

    private val snackbar by lazy {
        Snackbar.make(snackbarContainer, context.getString(stringId), duration)
    }

    init {
        lifecycleOwner.lifecycle.addObserver(this)
        registerNetworkListener()
    }

    private fun registerNetworkListener() {
        val appIntentFilter = IntentFilter().apply { addAction(ConnectivityManager.CONNECTIVITY_ACTION) }
        context.registerReceiver(networkChangeReceiver, appIntentFilter)
        val networkIntentFilter = IntentFilter(NETWORK_AVAILABLE_ACTION)

        //BR receive part
        context.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, intent: Intent) {
                val isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false)
                callback?.invoke(isNetworkAvailable)
                snackbar.apply {
                    if (isNetworkAvailable){
                        dismiss()
                    } else {
                        show()
                    }
                }
            }
        }, networkIntentFilter)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun disconnectListener() = context.unregisterReceiver(networkChangeReceiver)

}