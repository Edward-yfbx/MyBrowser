package com.yfbx.mybrowser.bean

import android.webkit.WebHistoryItem
import com.yfbx.mybrowser.constants.getHomePage
import org.litepal.crud.LitePalSupport

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
        if (item.url != getHomePage()) {
            this.title = item.title
            this.url = item.url
            save()
        }
    }
}