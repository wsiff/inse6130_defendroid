package com.ozone.defendroid.admin

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

class DeviceAdminManager(private val ctx: Context) {
    private val dpm = ctx.getSystemService(DevicePolicyManager::class.java)
    private val deviceAdmin by lazy { ComponentName(ctx, DeviceAdminReceiver::class.java) }

    fun remove() = dpm?.removeActiveAdmin(deviceAdmin)
    fun getCurrentFailedPasswordAttempts() = dpm?.currentFailedPasswordAttempts ?: 0
    fun isDeviceOwner() = dpm?.isDeviceOwnerApp(ctx.packageName) ?: false
    fun addUserRestriction(key: String) = dpm?.addUserRestriction(deviceAdmin, key)
    fun clearUserRestriction(key: String) = dpm?.clearUserRestriction(deviceAdmin, key)

    @RequiresApi(Build.VERSION_CODES.N)
    fun getUserRestrictions() = dpm?.getUserRestrictions(deviceAdmin)

    fun setMaximumFailedPasswordsForWipe(num: Int) =
        dpm?.setMaximumFailedPasswordsForWipe(deviceAdmin, num)

    @RequiresApi(Build.VERSION_CODES.S)
    fun canUsbDataSignalingBeDisabled() = dpm?.canUsbDataSignalingBeDisabled() ?: false

    @RequiresApi(Build.VERSION_CODES.S)
    fun setUsbDataSignalingEnabled(enabled: Boolean) { dpm?.isUsbDataSignalingEnabled = enabled }

    @RequiresApi(Build.VERSION_CODES.S)
    fun isUsbDataSignalingEnabled() = dpm?.isUsbDataSignalingEnabled ?: true

    fun wipeData() {
        var flags = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            flags = flags.or(DevicePolicyManager.WIPE_SILENTLY)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            flags = flags.or(DevicePolicyManager.WIPE_EUICC)
        dpm?.wipeData(flags)
    }

    fun makeRequestIntent() =
        Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            .putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceAdmin)
}