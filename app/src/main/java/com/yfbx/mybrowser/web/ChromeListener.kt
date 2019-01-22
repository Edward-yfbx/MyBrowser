package com.yfbx.mybrowser.web

import android.webkit.WebView

/**
 * Author: Edward
 * Date: 2019/1/4
 * Description:
 */

interface ChromeListener {


    fun onProgressChanged(view: WebView?, newProgress: Int)

}