package com.yfbx.mybrowser.net

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Looper
import com.yfbx.mybrowser.App
import kotlin.concurrent.thread

/**
 * Author: Edward
 * Date: 2020-07-03
 * Description:系统下载器
 */
object SystemDownloader {

    fun download(url: String, fileName: String, progress: (Long) -> Unit) {
        val manager = App.app.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(url))
        request.setTitle(fileName)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        val id = manager.enqueue(request)
        val handler = Handler(Looper.getMainLooper())
        thread {
            while (true) {
                val cursor = manager.query(DownloadManager.Query().setFilterById(id))
                cursor.moveToFirst()
                val loaded = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                val total = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                val percent = loaded * 100 / total
                cursor.close()
                handler.post { progress.invoke(percent) }
                if (loaded == total) {
                    break
                }
            }
        }
    }
}