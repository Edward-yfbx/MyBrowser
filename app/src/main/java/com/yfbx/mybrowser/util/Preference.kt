package com.yfbx.mybrowser.util

import android.content.Context
import com.yfbx.mybrowser.App
import kotlin.reflect.KProperty

/**
 * Author: Edward
 * Date: 2020-06-24
 * Description: SharedPreferences
 */

/**
 * @param defaultValue 默认值
 * eg. var version by pref("1.0.0"), 默认 key 即变量名 "version"
 */
fun <T> pref(defaultValue: T): Preference<T> = Preference(defaultValue)

/**
 * @param defaultValue 默认值
 * @param prefName SharedPreferences文件名
 * eg. var version by pref("Version","1.0.0","yxr_prefs")
 */
fun <T> pref(defaultValue: T, prefName: String): Preference<T> = Preference(defaultValue, "", prefName)

/**
 * SharedPreferences 代理
 */
class Preference<T>(
        private val defaultValue: T,
        private val key: String = "",
        private val prefName: String = "yxr_prefs",
        private val context: Context = App.app
) {

    private val prefs by lazy { context.getSharedPreferences(prefName, Context.MODE_PRIVATE) }

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val value = prefs.all[property.prefKey] ?: defaultValue
        return value as T
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        when (value) {
            is String -> prefs.edit().putString(property.prefKey, value).apply()
            is Boolean -> prefs.edit().putBoolean(property.prefKey, value).apply()
            is Int -> prefs.edit().putInt(property.prefKey, value).apply()
            is Float -> prefs.edit().putFloat(property.prefKey, value).apply()
            is Long -> prefs.edit().putLong(property.prefKey, value).apply()
            else -> throw Exception("Unsupported Type")
        }
    }

    private val KProperty<*>.prefKey
        get() = if (key.isEmpty()) name else key

}