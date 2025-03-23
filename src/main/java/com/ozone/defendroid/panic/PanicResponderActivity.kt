package com.ozone.defendroid.panic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import info.guardianproject.panic.Panic
import info.guardianproject.panic.PanicResponder
import com.ozone.defendroid.AppDatabase

class PanicResponderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Panic.isTriggerIntent(intent)) {
            finishAndRemoveTask()
            return
        }
        if (PanicResponder.receivedTriggerFromConnectedApp(this))
            AppDatabase.getInstance(this).packageDao().deleteAll()
        finishAndRemoveTask()
    }
}