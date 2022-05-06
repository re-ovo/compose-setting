package me.rerere.composesetting

import android.app.Application
import com.tencent.mmkv.MMKV
import me.rerere.compose_setting.preference.initComposeSetting
import kotlin.system.exitProcess

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        initComposeSetting()

        MMKV.defaultMMKV().encode("sts", emptySet())
        println("value = ${MMKV.defaultMMKV().decodeStringSet("sts")}")
    }
}