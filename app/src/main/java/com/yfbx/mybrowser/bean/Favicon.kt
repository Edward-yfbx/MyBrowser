package com.yfbx.mybrowser.bean

import android.graphics.Bitmap
import com.yfbx.mybrowser.util.saveIcon
import org.litepal.LitePal
import org.litepal.crud.LitePalSupport
import org.litepal.extension.findFirst

/**
 * Author: Edward
 * Date: 2019/1/7
 * Description:
 */
class Favicon : LitePalSupport() {

    var host: String = ""
    var path: String = ""

    /**
     * 保存图标
     */
    fun save(url: String, bitmap: Bitmap) {
        val host = getHostKey(url)
        val icon = LitePal.where("host = ?", host).findFirst<Favicon>()
        if (icon == null) {
            this.host = host
            this.path = saveIcon(bitmap, host)
            save()
        }
    }

    /**
     * 查找
     */
    fun find(url: String): Favicon? {
        return LitePal.where("host = ?", getHostKey(url)).findFirst<Favicon>()
    }


    /**
     * http://m.baidu.com/xxx    -->   baidu.com
     */
    private fun getHostKey(url: String): String {
        val spilt = url.split("/")[2]
        return spilt.substring(spilt.indexOf("."))
    }

}