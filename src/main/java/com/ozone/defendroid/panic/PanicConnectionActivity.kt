package com.ozone.defendroid.panic

import android.content.pm.PackageManager
import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import info.guardianproject.panic.PanicResponder
import com.ozone.defendroid.MainActivity
import com.ozone.defendroid.R

class PanicConnectionActivity : MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (PanicResponder.checkForDisconnectIntent(this)) {
            finish()
            return
        }
        val sender = PanicResponder.getConnectIntentSender(this)
        val packageName = PanicResponder.getTriggerPackageName(this)
        if (sender != "" && sender != packageName) showOptInDialog() else finish()
    }

    private fun showOptInDialog() {
        var app: CharSequence = getString(R.string.panic_app_unknown_app)
        val packageName = callingActivity?.packageName
        if (packageName != null) {
            try {
                app = packageManager
                    .getApplicationLabel(packageManager.getApplicationInfo(packageName, 0))
            } catch (_: PackageManager.NameNotFoundException) {}
        }
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.panic_app_dialog_title)
            .setMessage(getString(R.string.panic_app_dialog_message, app))
            .setNegativeButton(R.string.allow) { _, _ ->
                PanicResponder.setTriggerPackageName(this)
                setResult(RESULT_OK)
                finish()
            }
            .setPositiveButton(android.R.string.cancel) { _, _ ->
                setResult(RESULT_CANCELED)
                finish()
            }
            .show()
    }
}