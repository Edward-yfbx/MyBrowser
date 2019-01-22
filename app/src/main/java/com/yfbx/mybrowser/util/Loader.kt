package com.yfbx.mybrowser.util

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.webkit.URLUtil

/**
 * Author: Edward
 * Date: 2019/1/15
 * Description:
 */

class Loader {


    fun download(context: Context, filePath: String, url: String) {
        if (URLUtil.isValidUrl(url)) {
            val fileName = url.substring(url.lastIndexOf("/"))
            val request = DownloadManager.Request(Uri.parse(url))
            request.allowScanningByMediaScanner()
            request.setDestinationInExternalFilesDir(context, filePath, fileName)
            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)
            show("下载成功")
        } else {
            show("下载失败")
        }
    }
}