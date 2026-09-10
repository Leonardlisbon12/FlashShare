package com.flashshare.app.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.flashshare.app.data.model.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "flashshare_settings")

class SettingsRepository(private val context: Context) {
    private val deviceNameKey = stringPreferencesKey("device_name")
    private val downloadFolderKey = stringPreferencesKey("download_folder")
    private val autoAcceptKey = booleanPreferencesKey("auto_accept")
    private val notificationsKey = booleanPreferencesKey("notifications")
    private val darkModeKey = booleanPreferencesKey("dark_mode")
    private val soundKey = booleanPreferencesKey("sound")

    val appSettings: Flow<AppSettings> = context.dataStore.data.map { preferences ->
        AppSettings(
            deviceName = preferences[deviceNameKey] ?: android.os.Build.MODEL,
            downloadFolder = preferences[downloadFolderKey] ?: "/FlashShare",
            autoAcceptTransfers = preferences[autoAcceptKey] ?: false,
            enableNotifications = preferences[notificationsKey] ?: true,
            darkMode = preferences[darkModeKey] ?: false,
            enableSound = preferences[soundKey] ?: true
        )
    }

    suspend fun updateDeviceName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[deviceNameKey] = name
        }
    }

    suspend fun updateDownloadFolder(folder: String) {
        context.dataStore.edit { preferences ->
            preferences[downloadFolderKey] = folder
        }
    }

    suspend fun updateAutoAccept(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[autoAcceptKey] = enabled
        }
    }

    suspend fun updateNotifications(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[notificationsKey] = enabled
        }
    }

    suspend fun updateDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[darkModeKey] = enabled
        }
    }

    suspend fun updateSound(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[soundKey] = enabled
        }
    }
}
