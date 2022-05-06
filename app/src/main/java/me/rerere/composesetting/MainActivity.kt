package me.rerere.composesetting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import me.rerere.compose_setting.components.SettingItemCategory
import me.rerere.compose_setting.components.types.SettingBooleanItem
import me.rerere.compose_setting.components.types.SettingStringInputDialogItem
import me.rerere.compose_setting.components.types.SettingStringPickerItem
import me.rerere.compose_setting.preference.rememberBooleanPreference
import me.rerere.compose_setting.preference.rememberStringPreference
import me.rerere.compose_setting.preference.rememberStringSetPreference
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
                    val set = rememberStringSetPreference(
                        key = "stringSet",
                        default = setOf("awa")
                    )
                    //set.value = emptySet()
                    println("set content = ${set.value.joinToString(",")}")

                    Column(
                        modifier = Modifier.padding(it)
                    ) {
                        SettingItemCategory(
                            title = { Text("个性化") }
                        ) {
                            SettingBooleanItem(
                                state = rememberBooleanPreference(
                                    key = "tc_a",
                                    default = true
                                ),
                                title = {
                                    Text("主题设置")
                                },
                                icon = {
                                    Icon(Icons.Outlined.Call, null)
                                },
                                text = {
                                    Text("自定义APP个性化设置")
                                }
                            )
                        }
                        SettingItemCategory(
                            title = { Text("Category Title") }
                        ) {
                            SettingBooleanItem(
                                state = rememberBooleanPreference(
                                    key = "test",
                                    default = true
                                ),
                                title = {
                                    Text("Mobile")
                                },
                                icon = {
                                    Icon(Icons.Outlined.Call, null)
                                }
                            )
                            SettingBooleanItem(
                                state = rememberBooleanPreference(
                                    key = "test2",
                                    default = true
                                ),
                                title = {
                                    Text("Network")
                                },
                                text = {
                                    Text("This is the description")
                                },
                                icon = {
                                    Icon(Icons.Outlined.Notifications, null)
                                }
                            )
                            SettingStringPickerItem(
                                state = rememberStringPreference(
                                    key = "test3",
                                    default = "Xiaomi"
                                ),
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
                            SettingStringInputDialogItem(
                                state = rememberStringPreference(
                                    key = "test5",
                                    default = "Xiaomi"
                                ),
                                title = {
                                    Text("Set Phone Brand")
                                },
                                icon = {
                                    Icon(Icons.Outlined.Phone, null)
                                },
                                validator = { value ->
                                    value.length >= 3
                                },
                                invalidMessage = {
                                    Text("坏起来了")
                                },
                                confirmText = {
                                    Text("确定")
                                },
                                dismissText = {
                                    Text("取消")
                                }
                            )
                        }

                        Divider()

                        SettingItemCategory(
                            title = {
                                Text(text = "Compose Yes")
                            }
                        ) {
                            SettingBooleanItem(
                                state = rememberBooleanPreference(
                                    key = "test4",
                                    default = true
                                ),
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
}