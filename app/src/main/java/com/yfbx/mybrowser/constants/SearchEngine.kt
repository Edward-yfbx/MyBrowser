package com.yfbx.mybrowser.constants

import com.yfbx.mybrowser.util.pref

/**
 * Author: Edward
 * Date: 2019/1/2
 * Description:
 */

object SearchEngine {

    enum class Engine(val code: Int, val homePage: String) {
        BAIDU(0, "file:///android_asset/baidu.html"),
        GOOGLE(1, "https://www.google.com")
    }

    private var engine = Engine.BAIDU

    private var enginePref: Int by pref(0)

    /**
     * 设置搜索引擎
     */
    fun setEngine(engine: Engine) {
        this.engine = engine
        enginePref = engine.code
    }


    fun getEngine(): Engine {
        return engine
    }

    /**
     * 搜索
     */
    fun search(key: String): String {
        if (key.startsWith("http")) {
            return key
        }
        if (key.contains("www") || key.contains(".com") || key.contains(".cn")) {
            return "http://$key"
        }
        return when (engine) {
            Engine.BAIDU -> "https://m.baidu.com/s?word=$key"
            Engine.GOOGLE -> "https://www.google.com/search?q=$key"
        }
    }

}