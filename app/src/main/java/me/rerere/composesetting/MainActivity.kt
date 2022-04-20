package me.rerere.composesetting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import me.rerere.compose_setting.components.SettingItemCategory

import me.rerere.compose_setting.components.types.SettingBooleanItem
import me.rerere.compose_setting.components.types.SettingStringItem
import me.rerere.compose_setting.preference.rememberBooleanPreference
import me.rerere.compose_setting.preference.rememberStringPreference
import me.rerere.composesetting.ui.theme.ComposesettingTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposesettingTheme {
                Scaffold(
                    topBar = {
                        LargeTopAppBar(
                            title = { Text("Compose Setting") },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(Icons.Outlined.Close, null)
                                }
                            }
                        )
                    }
                ) {
                    SettingItemCategory(
                        title = { Text("Category Title") }
                    ) {
                        SettingBooleanItem(
                            state = rememberBooleanPreference(key = "test", defaultValue = true),
                            title = {
                                Text("移动通信")
                            },
                            icon = {
                                Icon(Icons.Outlined.Call, null)
                            }
                        )
                        SettingBooleanItem(
                            state = rememberBooleanPreference(key = "test2", defaultValue = true),
                            title = {
                                Text("互联网")
                            },
                            text = {
                                Text("This is the description")
                            },
                            icon = {
                                Icon(Icons.Outlined.Notifications, null)
                            }
                        )
                        SettingStringItem(
                            state = rememberStringPreference(key = "test3", defaultValue = "XiaoMi"),
                            title = {
                                Text("Set Phone Brand")
                            },
                            text = {
                                Text("Select your phone brand")
                            },
                            icon = {
                                Icon(Icons.Outlined.Phone, null)
                            },
                            stateRange = setOf(
                                "Xiaomi", "Google", "Oppo"
                            )
                        )
                        SettingBooleanItem(
                            state = rememberBooleanPreference(key = "test4", defaultValue = true),
                            title = {
                                Text("Menu Title")
                            },
                            text = {
                                Text("This is the description")
                            }
                        )
                    }
                }
            }
        }
    }
}