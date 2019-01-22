package com.yfbx.mybrowser.constants

import com.yfbx.mybrowser.util.getEngine

/**
 * Author: Edward
 * Date: 2019/1/2
 * Description:
 */


val BAIDU = 0
val GOOGLE = 1


val HOME_BAIDU = "file:///android_asset/baidu.html"
val HOME_GOOGLE = "https://www.google.com"


fun getHomePage(): String {
    if (getEngine() == BAIDU) {
        return HOME_BAIDU
    }
    if (getEngine() == GOOGLE) {
        return HOME_GOOGLE
    }
    return HOME_BAIDU
}



/**
 * 搜索
 */
fun getSearchUrl(key: String): String {
    if (key.startsWith("http")) {
        return key
    }
    if (key.contains("www") || key.contains(".com") || key.contains(".cn")) {
        return "http://$key"
    }

    //百度搜索
    if (getEngine() == BAIDU) {
        return "https://m.baidu.com/s?word=" + key
    }
    //谷歌搜索
    if (getEngine() == GOOGLE) {
        return "https://www.google.com/search?q=" + key
    }
    return "https://m.baidu.com/s?word=" + key
}