package com.example.onlinecourses.ui.theme

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "settings")

class ThemePreferences(context: Context) {
    private val dataStore = context.dataStore

    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")

    suspend fun getDarkModeState(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[DARK_MODE_KEY] ?: false
    }

    suspend fun setDarkModeState(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = isDarkMode
        }
    }
}

@Composable
fun rememberDarkModeState(): Pair<Boolean, (Boolean) -> Unit> {
    val context = LocalContext.current
    val themePreferences = remember { ThemePreferences(context) }
    val (isDarkMode, setIsDarkMode) = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val darkModeState = themePreferences.getDarkModeState()
        setIsDarkMode(darkModeState)
    }

    val setDarkMode = { value: Boolean ->
        coroutineScope.launch {
            themePreferences.setDarkModeState(value)
        }
        setIsDarkMode(value)
    }

    return isDarkMode to setDarkMode
}

@Composable
fun rememberDarkModeStateSystem(): Boolean {
    val isDarkMode = isSystemInDarkTheme()
    return remember { mutableStateOf(isDarkMode) }.value
}