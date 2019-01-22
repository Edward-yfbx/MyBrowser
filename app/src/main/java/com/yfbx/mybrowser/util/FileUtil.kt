package com.yfbx.mybrowser.util

import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.concurrent.thread


/**
 * Author: Edward
 * Date: 2019/1/7
 * Description:
 */


val FILE_SD = Environment.getExternalStorageDirectory().path
val FILE_ROOT = FILE_SD + "/MyBrowser"
val FILE_ICON = FILE_ROOT + "/icon"
val FILE_IMG = FILE_ROOT + "/Pictures"

/**
 * 创建APP文件夹
 */
fun createAppDirs() {
    thread {
        val file = File(FILE_ROOT)
        if (!file.exists()) {
            file.mkdirs()
        }
        val iconFile = File(FILE_ICON)
        if (!iconFile.exists()) {
            iconFile.mkdirs()
        }
        val imgFile = File(FILE_IMG)
        if (!imgFile.exists()) {
            imgFile.mkdirs()
        }
    }
}

/**
 * 保存网站的Icon
 */
fun saveIcon(bitmap: Bitmap, fileName: String): String {
    val file = File(FILE_ICON, fileName + ".png")
    try {
        val outStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
        outStream.flush()
        outStream.close()
        return file.path
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return ""
}
