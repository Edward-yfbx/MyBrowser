package com.yfbx.mybrowser.util

import android.content.Context
import android.content.SharedPreferences
import com.yfbx.mybrowser.App.Companion.app
import com.yfbx.mybrowser.constants.BAIDU

/**
 * Author: Edward
 * Date: 2018/12/20
 * Description:
 */
//lateinit 只用于变量 var，用于只能生命周期流程中进行获取或者初始化的变量
//lazy 只用于常量val,应用于单例模式(if-null-then-init-else-return)，而且仅当变量被第一次调用的时候，委托方法才会执行。
val prefs: SharedPreferences by lazy { app.getSharedPreferences("BROWSER_PREFS", Context.MODE_PRIVATE) }

/**
 * 默认搜索引擎
 */
fun saveEngine(engine: Int) {
    prefs.edit().putInt("Engine", engine).apply()
}

fun getEngine(): Int {
    return prefs.getInt("Engine", BAIDU)
}

/**
 * DownloadPath
 */
fun saveDownloadPath(path: String) {
    prefs.edit().putString("DownloadPath", path).apply()
}

fun getDownloadPath(): String {
    return prefs.getString("DownloadPath", "")
}