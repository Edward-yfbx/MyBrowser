package com.yfbx.mybrowser.bean

import android.webkit.WebHistoryItem
import com.yfbx.mybrowser.constants.SearchEngine
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

class BookMark : LitePalSupport() {

    var title: String = ""
    var url: String = ""


    /**
     * 添加收藏
     */
    fun collect(item: WebHistoryItem) {
        if (item.url != SearchEngine.getEngine().homePage) {
            this.title = item.title
            this.url = item.url
            save()
        }
    }


    companion object {

        suspend fun findAll() = suspendCoroutine<List<BookMark>> { ctx ->
            GlobalScope.launch(Dispatchers.IO) {
                val data = LitePal.findAll(BookMark::class.java)
                ctx.resume(data)
            }
        }
    }
}