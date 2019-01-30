package com.yfbx.mybrowser.bean

import org.litepal.crud.LitePalSupport

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

}