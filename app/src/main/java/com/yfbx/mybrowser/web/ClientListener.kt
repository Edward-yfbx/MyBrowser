package com.yfbx.mybrowser.web

import android.graphics.Bitmap
import android.webkit.WebResourceRequest
import android.webkit.WebView

/**
 * Author: Edward
 * Date: 2019/1/4
 * Description:
 */

interface ClientListener {

    fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)

    fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?):Boolean

    fun onPageFinished(view: WebView?, url: String?)

}