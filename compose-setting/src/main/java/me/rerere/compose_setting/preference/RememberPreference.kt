package me.rerere.compose_setting.preference

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Composable
private inline fun <reified T> rememberPreference(
    key: Preferences.Key<T>,
    defaultValue: T
): MutableState<T> {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val preference = produceState(
        initialValue = defaultValue,
        key1 = context,
        producer = {
            context.dataStore.data.collect {
                value = it[key] ?: defaultValue
                if(!T::class.isInstance(value)){
                    value = defaultValue
                }
            }
        }
    )
    return object : MutableState<T> {
        override var value: T
            get() = preference.value
            set(value) {
                scope.launch {
                    context.dataStore.edit {
                        it[key] = value
                    }
                }
            }

        override fun component1(): T = value

        override fun component2(): (T) -> Unit = { value = it }
    }
}

@Composable
fun rememberBooleanPreference(
    key: String,
    defaultValue: Boolean
): MutableState<Boolean> {
    return rememberPreference(
        key = booleanPreferencesKey(key),
        defaultValue = defaultValue
    )
}

@Composable
fun rememberStringPreference(
    key: String,
    defaultValue: String
): MutableState<String> {
    return rememberPreference(
        key = stringPreferencesKey(key),
        defaultValue = defaultValue
    )
}

@Composable
fun rememberIntPreference(
    key: String,
    defaultValue: Int
): MutableState<Int> {
    return rememberPreference(
        key = intPreferencesKey(key),
        defaultValue = defaultValue
    )
}

@Composable
fun rememberDoublePreference(
    key: String,
    defaultValue: Double
): MutableState<Double> {
    return rememberPreference(
        key = doublePreferencesKey(key),
        defaultValue = defaultValue
    )
}

@Composable
fun rememberStringSetPreference(
    key: String,
    defaultValue: Set<String>
): MutableState<Set<String>> {
    return rememberPreference(
        key = stringSetPreferencesKey(key),
        defaultValue = defaultValue
    )
}