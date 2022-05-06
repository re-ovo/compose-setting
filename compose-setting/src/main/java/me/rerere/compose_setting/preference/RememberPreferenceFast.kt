package me.rerere.compose_setting.preference

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.*
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Initialize the MMKV
 *
 * You should call this function in your [Application.onCreate()]
 */
fun Application.initComposeSetting() {
    MMKV.initialize(this)
}

/**
 * Get the Preference wrapper of MMKV
 *
 * You can read/write without Compose context by using this
 */
val mmkvPreference by lazy {
    MMKVPreference(MMKV.mmkvWithID("compose_setting"))
}

@Composable
fun rememberBooleanPreference(
    key: String,
    default: Boolean
): MutableState<Boolean> {
    val state = remember {
        mutableStateOf(mmkvPreference.getBoolean(key, default))
    }
    DisposableEffect(Unit) {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, s ->
            if(s == key) {
                state.value = sharedPreferences.getBoolean(key, default)
            }
        }
        mmkvPreference.registerOnSharedPreferenceChangeListener(listener)
        onDispose {
            mmkvPreference.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }
    return object : MutableState<Boolean> {
        override var value: Boolean
            get() = state.value
            set(value) {
                state.value = value
                mmkvPreference.putBoolean(key, value)
            }

        override fun component1(): Boolean = value

        override fun component2(): (Boolean) -> Unit = {
            value = it
        }
    }
}

@Composable
fun rememberIntPreference(
    key: String,
    default: Int
): MutableState<Int> {
    val state = remember {
        mutableStateOf(mmkvPreference.getInt(key, default))
    }
    DisposableEffect(Unit) {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, s ->
            if(s == key) {
                state.value = sharedPreferences.getInt(key, default)
            }
        }
        mmkvPreference.registerOnSharedPreferenceChangeListener(listener)
        onDispose {
            mmkvPreference.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }
    return object : MutableState<Int> {
        override var value: Int
            get() = state.value
            set(value) {
                state.value = value
                mmkvPreference.putInt(key, value)
            }

        override fun component1(): Int = value

        override fun component2(): (Int) -> Unit = {
            value = it
        }
    }
}

@Composable
fun rememberFloatPreference(
    key: String,
    default: Float
): MutableState<Float> {
    val state = remember {
        mutableStateOf(mmkvPreference.getFloat(key, default))
    }
    DisposableEffect(Unit) {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, s ->
            if(s == key) {
                state.value = sharedPreferences.getFloat(key, default)
            }
        }
        mmkvPreference.registerOnSharedPreferenceChangeListener(listener)
        onDispose {
            mmkvPreference.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }
    return object : MutableState<Float> {
        override var value: Float
            get() = state.value
            set(value) {
                state.value = value
                mmkvPreference.putFloat(key, value)
            }

        override fun component1(): Float = value

        override fun component2(): (Float) -> Unit = {
            value = it
        }
    }
}

@Composable
fun rememberDoublePreference(
    key: String,
    default: Double
): MutableState<Double> {
    val state = remember {
        mutableStateOf(mmkvPreference.getDouble(key, default))
    }
    DisposableEffect(Unit) {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, s ->
            if(s == key) {
                state.value = mmkvPreference.getDouble(key, default)
            }
        }
        mmkvPreference.registerOnSharedPreferenceChangeListener(listener)
        onDispose {
            mmkvPreference.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }
    return object : MutableState<Double> {
        override var value: Double
            get() = state.value
            set(value) {
                state.value = value
                mmkvPreference.putDouble(key, value)
            }

        override fun component1(): Double = value

        override fun component2(): (Double) -> Unit = {
            value = it
        }
    }
}

@Composable
fun rememberLongPreference(
    key: String,
    default: Long
): MutableState<Long> {
    val state = remember {
        mutableStateOf(mmkvPreference.getLong(key, default))
    }
    DisposableEffect(Unit) {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, s ->
            if(s == key) {
                state.value = sharedPreferences.getLong(key, default)
            }
        }
        mmkvPreference.registerOnSharedPreferenceChangeListener(listener)
        onDispose {
            mmkvPreference.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }
    return object : MutableState<Long> {
        override var value: Long
            get() = state.value
            set(value) {
                state.value = value
                mmkvPreference.putLong(key, value)
            }

        override fun component1(): Long = value

        override fun component2(): (Long) -> Unit = {
            value = it
        }
    }
}

@Composable
fun rememberStringPreference(
    key: String,
    default: String
): MutableState<String> {
    val state = remember {
        mutableStateOf(mmkvPreference.getString(key, default) ?: default)
    }
    DisposableEffect(Unit) {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, s ->
            if(s == key) {
                state.value = sharedPreferences.getString(key, default) ?: default
            }
        }
        mmkvPreference.registerOnSharedPreferenceChangeListener(listener)
        onDispose {
            mmkvPreference.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }
    return object : MutableState<String> {
        override var value: String
            get() = state.value
            set(value) {
                state.value = value
                mmkvPreference.putString(key, value)
            }

        override fun component1(): String = value

        override fun component2(): (String) -> Unit = {
            value = it
        }
    }
}

// It might return the default value if you saved a empty string set
@Composable
fun rememberStringSetPreference(
    key: String,
    default: Set<String>
): MutableState<Set<String>> {
    val state = remember {
        mutableStateOf(mmkvPreference.getStringSet(key, default) ?: emptySet())
    }
    DisposableEffect(Unit) {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, s ->
            if(s == key) {
                state.value = sharedPreferences.getStringSet(key, default) ?: emptySet()
            }
        }
        mmkvPreference.registerOnSharedPreferenceChangeListener(listener)
        onDispose {
            mmkvPreference.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }
    return object : MutableState<Set<String>> {
        override var value: Set<String>
            get() = state.value
            set(value) {
                state.value = value
                mmkvPreference.putStringSet(key, value)
            }

        override fun component1(): Set<String> = value

        override fun component2(): (Set<String>) -> Unit = {
            value = it
        }
    }
}