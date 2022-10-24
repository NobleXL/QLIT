package com.noble.qlit.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.noble.qlit.MyApp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * @author: noble
 * @desc: DataStore 本地存储数据
 */
// 初始化DataStroe
val Context.dataStore: DataStore<Preferences> by preferencesDataStore("DataStore")

object DataManager {

    private var dataStore: DataStore<Preferences> = MyApp.context.dataStore

    // 异步写入数据
    suspend fun <T> saveData(key: String, value: T) {
        when (value) {
            is Int -> dataStore.edit { it[intPreferencesKey(key)] = value }
            is Long -> dataStore.edit { it[longPreferencesKey(key)] = value }
            is String -> dataStore.edit { it[stringPreferencesKey(key)] = value }
            is Boolean -> dataStore.edit { it[booleanPreferencesKey(key)] = value }
            is Float -> dataStore.edit { it[floatPreferencesKey(key)] = value }
            else -> throw IllegalArgumentException("此类型无法保存到 DataStore")
        }
    }

    // 异步读取数据
    suspend fun <T> readData(key: String, default: T): T {
        val data = when (default) {
            is Int -> dataStore.data.map { it[intPreferencesKey(key)] ?: 0 }
            is Long -> dataStore.data.map { it[longPreferencesKey(key)] ?: 0L }
            is String -> dataStore.data.map { it[stringPreferencesKey(key)] ?: "" }
            is Boolean -> dataStore.data.map { it[booleanPreferencesKey(key)] ?: false }
            is Float -> dataStore.data.map { it[floatPreferencesKey(key)] ?: 0f }
            else -> throw IllegalArgumentException("此类型无法读取")
        }
        return data.first() as T
    }

    suspend fun clearData() {
        dataStore.edit {
            it.clear()
        }
    }
}