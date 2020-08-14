package com.yfbx.mybrowser.bean

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.litepal.LitePal
import org.litepal.crud.LitePalSupport
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Author: Edward
 * Date: 2019/1/7
 * Description:历史
 */

class History() : LitePalSupport() {

    var title: String = ""
    var url: String = ""


    constructor(title: String, url: String) : this() {
        this.title = title
        this.url = url
    }


    companion object {

        suspend fun findAll() = suspendCoroutine<List<History>> { ctx ->
            GlobalScope.launch(Dispatchers.IO) {
                val data = LitePal.order("id DESC").find(History::class.java)
                ctx.resume(data)
            }
        }
    }
}