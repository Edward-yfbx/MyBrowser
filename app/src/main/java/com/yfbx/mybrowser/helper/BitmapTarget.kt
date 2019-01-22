package com.yfbx.mybrowser.helper

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.yfbx.mybrowser.util.FILE_IMG
import com.yfbx.mybrowser.util.show
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

/**
 * Author: Edward
 * Date: 2019/1/15
 * Description:
 */

class BitmapTarget(val fileName: String) : Target {


    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
    }

    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        show("下载失败")
    }

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        if (bitmap != null) {
            val file = File(FILE_IMG, fileName)
            val outStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            outStream.flush()
            outStream.close()
        } else {
            show("下载失败")
        }
    }

}