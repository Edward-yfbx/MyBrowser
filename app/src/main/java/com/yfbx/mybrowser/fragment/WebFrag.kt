package com.yfbx.mybrowser.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.view.ContextMenu
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.webkit.WebBackForwardList
import android.webkit.WebHistoryItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.squareup.picasso.Picasso
import com.yfbx.mybrowser.R
import com.yfbx.mybrowser.bean.History
import com.yfbx.mybrowser.constants.SearchEngine
import com.yfbx.mybrowser.helper.BitmapTarget
import com.yfbx.mybrowser.helper.Popups
import com.yfbx.mybrowser.model.WebModel
import com.yfbx.mybrowser.web.ChromeListener
import com.yfbx.mybrowser.web.ClientListener
import kotlinx.android.synthetic.main.frag_web.*


/**
 * Author: Edward
 * Date: 2019/1/9
 * Description:
 */

class WebFrag : BaseFragment(), ClientListener, ChromeListener {

    private val model = WebModel()

    override fun onCreate(view: View, savedInstanceState: Bundle?) {
        web_txt.setOnKeyListener { v, keyCode, event -> onEnterKey(v, keyCode, event) }
        mWebView.setOnClientListener(this)
        mWebView.setOnChromeListener(this)

        val url = arguments?.getString("url")
        if (url.isNullOrEmpty()) {
            loadHomePage()
        } else {
            mWebView.loadUrl(url)
        }
        registerForContextMenu(mWebView)
    }

    override fun getLayout(): Int {
        return R.layout.frag_web
    }

    /**
     * 上下文菜单(长按)
     */
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        val type = mWebView.hitTestResult.type
        if (type == WebView.HitTestResult.IMAGE_TYPE || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
            menu.add(0, 0, 0, "查看图片")
            menu.add(0, 1, 1, "保存图片")
        }
    }

    /**
     * 上下文菜单点击事件
     */
    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.itemId == 0) {
            Popups(requireActivity()).showGalleryPopup(mWebView, model.pictures)
        }

        if (item.itemId == 1) {
            val url = mWebView.hitTestResult.extra ?: return false
            val fileName = url.substring(url.lastIndexOf("/") + 1)
            Picasso.get().load(url).into(BitmapTarget(fileName))
        }
        return false
    }

    /**
     * 主页
     */
    fun loadHomePage() {
        mWebView.loadUrl(SearchEngine.getEngine().homePage)
    }


    /**
     * 加载Url
     */
    fun load(url: String) {
        mWebView.loadUrl(url)
    }

    /**
     * 刷新
     */
    fun reload() {
        mWebView.reload()
    }

    /**
     * 回退
     */
    fun goBack() {
        mWebView.onBack()
    }

    fun canGoBack(): Boolean {
        return mWebView.canGoBack()
    }

    /**
     * 前进
     */
    fun goForward() {
        mWebView.onForward()
    }

    /**
     * 当前页面的历史记录
     */
    fun getCurrent(): WebHistoryItem {
        return mWebView.copyBackForwardList().currentItem!!
    }

    /**
     * 历史记录
     */
    fun getHistory(): WebBackForwardList {
        return mWebView.copyBackForwardList()
    }

    /**
     * 清除历史记录
     */
    fun clearHistory() {
        mWebView.clearHistory()
    }


    /**
     * Enter Key
     */
    private fun onEnterKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
            if (!TextUtils.isEmpty(web_txt.text)) {
                mWebView.loadUrl(SearchEngine.search(web_txt.text.toString()))
            }
            return true
        }
        return false
    }


    /**
     * 加载进度
     */
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        if (newProgress == 100) {
            web_progress.visibility = View.GONE
        } else {
            web_progress.visibility = View.VISIBLE
            web_progress.progress = newProgress
        }
    }


    var reset = false

    /**
     * 重定向(在 onPageStarted 之前执行)
     * 重定向(重定向链接)-->开始(重定向链接)-->重定向(目标链接)-->结束(重定向链接)
     * 开始(目标链接)-->结束(目标链接)
     */
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        reset = true
        return false
    }


    /**
     * 开始加载
     */
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        if (url != SearchEngine.getEngine().homePage) {
            web_head.visibility = View.VISIBLE
            web_txt.setText(url)
        } else {
            web_head.visibility = View.GONE
        }
    }

    /**
     * 加载完成
     */
    override fun onPageFinished(view: WebView?, url: String?) {
        getParent().setEnable(mWebView.canGoBack(), mWebView.canGoForward())
        collectImage()

        if (reset) {
            reset = false
            saveHistory()
        }
    }


    /**
     * 保存历史
     */
    private fun saveHistory() {
        val item = mWebView.copyBackForwardList().currentItem
        item?.let {
            val title = if (TextUtils.isEmpty(it.title)) it.url else it.title
            History(title, it.url).save()
        }
    }


    /**
     * 加载脚本，抓取页面内的图片
     */
    private fun collectImage() {
        mWebView.addJavascriptInterface(model, "imageListener")
        mWebView.loadUrl(model.grapPictureScript())
    }


}