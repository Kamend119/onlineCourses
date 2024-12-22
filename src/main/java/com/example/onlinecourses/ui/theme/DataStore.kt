package com.example.onlinecourses.ui.theme

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "settings")

class ThemePreferences(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("theme_preferences", Context.MODE_PRIVATE)

    // Ключ для сохранения состояния темы
    private val DARK_MODE_KEY = "dark_mode_key"

    // Считываем состояние темы
    fun getDarkModeState(): Boolean {
        return preferences.getBoolean(DARK_MODE_KEY, false) // По умолчанию светлая тема
    }

    // Сохраняем состояние темы
    fun setDarkModeState(isDarkMode: Boolean) {
        preferences.edit().putBoolean(DARK_MODE_KEY, isDarkMode).apply()
    }
}

@Composable
fun rememberDarkModeState(): Pair<Boolean, (Boolean) -> Unit> {
    val context = LocalContext.current
    val themePreferences = remember { ThemePreferences(context) }
    val isSystemDarkMode = isSystemInDarkTheme()
    val (isDarkMode, setIsDarkMode) = remember { mutableStateOf(isSystemDarkMode) }
    val coroutineScope = rememberCoroutineScope()

    // Загрузить сохраненное состояние темной темы при старте
    LaunchedEffect(Unit) {
        val darkModeState = themePreferences.getDarkModeState()
        setIsDarkMode(darkModeState)
    }

    // Функция для обновления состояния темы
    val setDarkMode: (Boolean) -> Unit = { value: Boolean ->
        setIsDarkMode(value)
        coroutineScope.launch {
            themePreferences.setDarkModeState(value)
        }
    }

    return isDarkMode to setDarkMode
}


@Composable
fun rememberDarkModeStateSystem(): Boolean {
    val isDarkMode = isSystemInDarkTheme()
    return remember { mutableStateOf(isDarkMode) }.value
}