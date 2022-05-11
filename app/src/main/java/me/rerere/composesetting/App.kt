package me.rerere.composesetting

import android.app.Application
import com.tencent.mmkv.MMKV
import me.rerere.compose_setting.preference.initComposeSetting
import kotlin.system.exitProcess

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        initComposeSetting()
    }
}