# ComposeSetting
This is a basic Compose setting library that provides a basic [Material3](https://m3.material.io) setting components

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
    implementation 'TODO'
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