package com.yfbx.mybrowser.web

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.http.SslError
import android.util.AttributeSet
import android.view.GestureDetector
import android.webkit.*
import com.yfbx.mybrowser.bean.Favicon
import com.yfbx.mybrowser.listener.GestureListener

/**
 * Author: Edward
 * Date: 2019/1/4
 * Description:
 */

@SuppressLint("SetJavaScriptEnabled")
class MyWebView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        WebView(context, attrs, defStyleAttr) {

    var needClearHistory = false

    init {
        settings.javaScriptEnabled = true// 启用JS脚本
        settings.domStorageEnabled = true//支持DOM API
        settings.setAppCacheEnabled(true)//开启缓存
        settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK//缓存模式
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW//解决app中部分页面非https导致的问题
        settings.allowFileAccess = true //设置可以访问文件
        settings.loadsImagesAutomatically = true //支持自动加载图片

        settings.setSupportZoom(true)//支持缩放
        settings.useWideViewPort = true//将图片调整到适合webview大小
        settings.loadWithOverviewMode = true// 缩放至屏幕的大小

        settings.supportMultipleWindows() //多窗口
        settings.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口

        val detector = GestureDetector(context, GestureListener({ onGesture(it) }))
        setOnTouchListener { v, event -> detector.onTouchEvent(event) }

    }


    override fun clearHistory() {
        super.clearHistory()
        needClearHistory = true
    }


    fun onBack() {
        if (canGoBack()) {
            goBack()
        }
    }

    fun onForward() {
        if (canGoForward()) {
            goForward()
        }
    }

    /**
     * 滑动手势
     */
    private fun onGesture(isForward: Boolean) {
        if (isForward) {
            onForward()
        } else {
            onBack()
        }
    }


    /**
     * 设置WebViewClient
     */
    fun setOnClientListener(listener: ClientListener) {
        webViewClient = WebClient(listener)
    }

    /**
     * 设置WebChromeClient
     */
    fun setOnChromeListener(listener: ChromeListener) {
        webChromeClient = ChromeClient(listener)
    }


    inner class WebClient(val listener: ClientListener) : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            listener.onPageStarted(view, url, favicon)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return listener.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            listener.onPageFinished(view, url)
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            // 接受所有网站的证书
            handler!!.proceed()
        }

        override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
            if (needClearHistory) {
                clearHistory()
                needClearHistory = false
            }
        }
    }


    inner class ChromeClient(val listener: ChromeListener) : WebChromeClient() {


        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            listener.onProgressChanged(view, newProgress)
        }


        override fun onReceivedTitle(view: WebView?, title: String?) {
        }

        override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
            if (icon != null) {
                Favicon().save(url, icon)
            }
        }
    }
}