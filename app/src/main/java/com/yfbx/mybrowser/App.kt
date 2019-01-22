package com.yfbx.mybrowser

import android.app.Application
import android.webkit.WebBackForwardList
import org.litepal.LitePal
import kotlin.properties.Delegates

/**
 * Author: Edward
 * Date: 2019/1/2
 * Description:
 */

val history = mutableListOf<WebBackForwardList>()

class App : Application() {

    companion object {
        var app: App by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        LitePal.initialize(this)
    }

}