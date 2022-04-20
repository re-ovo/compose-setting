# ComposeSetting
![](https://img.shields.io/github/issues/re-ovo/compose-setting?style=flat-square)
![](https://img.shields.io/github/forks/re-ovo/compose-setting?style=flat-square)
![](https://img.shields.io/github/stars/re-ovo/compose-setting?style=flat-square)
![](https://img.shields.io/github/pulls/re-ovo/compose-setting?style=flat-square)

This is a basic Compose setting library that provides a basic [Material3](https://m3.material.io) setting components

## Screenshot
![](art/screenshot.png)

## Import to your project
1. Import jitpack to your repository
```groovy
repositories {
    maven {
        url 'https://jitpack.io'
    }
}
```
2. Import the library
```groovy
implementation 'com.github.re-ovo:compose-setting:1.0.0'
```

## Remember Preference
Before using settings, please let me introduce a `rememberXXXPreference` function, which can **persist** remember a certain value in Compose

```kotlin
val booleanPreference by rememberBooleanPreference(
key = "boolean_preference",
defaultValue = false
)
```

Other types of preference can be used as well, such as
* `rememberStringPreference`
* `rememberIntPreference`
* `rememberDoublePreference`
* `rememberStringPreference`
* `rememberStringSetPreference`

Note: The preference was based on DataStore API

## Setting Components
This library provides several out-of-the-box setting item components

### SettingBooleanItem
This component is used to display a setting item with a boolean value

```kotlin
val booleanPref = rememberBooleanPreference(
    key = "boolean_preference",
    defaultValue = false
)
SettingBooleanItem(
    state = booleanPref,
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
```
![](art/boolean_component.png)

### SettingStringItem
This component is used to display a setting item with a string value

```kotlin
val stringPref = rememberStringPreference(
    key = "string_preference",
    defaultValue = "default"
)
SettingStringItem(
    state = stringPref,
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
```
![](art/string_component.png)

### SettingLinkItem
This component is used to display a basic setting item

```kotlin
SettingLinkItem(
    title = {
        Text("Network")
    },
    text = {
        Text("This is the description")
    },
    icon = {
        Icon(Icons.Outlined.Notifications, null)
    },
    onClick = {
        // do something by yourself
    }
)
```

### SettingItemCategory
This component is used to display a category of setting items

```kotlin
 SettingItemCategory(
    title = {
        Text(text = "Compose Yes")
    }
) {
    // Your Menu Items Here
}
```
![](art/category.png)