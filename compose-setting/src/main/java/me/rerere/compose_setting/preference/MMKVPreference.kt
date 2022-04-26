package me.rerere.compose_setting.preference

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import com.tencent.mmkv.MMKV

/**
 * A pretty-fast preference implementation based on [MMKV](https://github.com/Tencent/MMKV).
 *
 * MMKV自带的Preference实现，比较快，但是不支持回调
 * 这里使用Kotlin委托重新实现了一个Preference，支持回调
 */
class MMKVPreference
internal constructor(private val mmkv: MMKV)
    : SharedPreferences by mmkv, Editor by mmkv {
    private val listeners = mutableSetOf<OnSharedPreferenceChangeListener>()

    override fun registerOnSharedPreferenceChangeListener(p0: SharedPreferences.OnSharedPreferenceChangeListener) {
        listeners += p0
    }

    override fun unregisterOnSharedPreferenceChangeListener(p0: SharedPreferences.OnSharedPreferenceChangeListener) {
        listeners -= p0
    }

    override fun putBoolean(p0: String?, p1: Boolean): Editor {
        mmkv.encode(p0, p1)
        listeners.forEach { it.onSharedPreferenceChanged(this, p0) }
        return this
    }

    override fun putFloat(p0: String?, p1: Float): Editor {
        mmkv.encode(p0, p1)
        listeners.forEach { it.onSharedPreferenceChanged(this, p0) }
        return this
    }

    override fun putInt(p0: String?, p1: Int): Editor {
        mmkv.encode(p0, p1)
        listeners.forEach { it.onSharedPreferenceChanged(this, p0) }
        return this
    }

    override fun putLong(p0: String?, p1: Long): Editor {
        mmkv.encode(p0, p1)
        listeners.forEach { it.onSharedPreferenceChanged(this, p0) }
        return this
    }

    override fun putString(p0: String?, p1: String?): Editor {
        mmkv.encode(p0, p1)
        listeners.forEach { it.onSharedPreferenceChanged(this, p0) }
        return this
    }

    override fun putStringSet(p0: String?, p1: Set<String>?): Editor {
        mmkv.encode(p0, p1)
        listeners.forEach { it.onSharedPreferenceChanged(this, p0) }
        return this
    }

    fun putDouble(key: String, value: Double) {
        mmkv.encode(key, value)
        listeners.forEach { it.onSharedPreferenceChanged(this, key) }
    }

    fun getDouble(key: String, defaultValue: Double): Double {
        return mmkv.decodeDouble(key, defaultValue)
    }

    override fun remove(p0: String?): Editor {
        mmkv.removeValueForKey(p0)
        listeners.forEach { it.onSharedPreferenceChanged(this, p0) }
        return this
    }

    override fun clear(): Editor {
        mmkv.clearAll()
        listeners.forEach { it.onSharedPreferenceChanged(this, null) }
        return this
    }
}