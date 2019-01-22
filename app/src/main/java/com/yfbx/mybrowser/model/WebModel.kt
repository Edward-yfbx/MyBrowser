package com.yfbx.mybrowser.model

import android.webkit.JavascriptInterface

/**
 * Author: Edward
 * Date: 2019/1/15
 * Description:
 */

class WebModel {

    val pictures: MutableList<String> = arrayListOf()

    /**
     * 抓取网页中的所有图片
     */
    fun grapPictureScript(): String {
        return "javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "var imgUrl = \"\";" +
                "var filter = [\"img//EventHead.png\",\"img//kong.png\",\"hdtz//button.png\"];" +
                "var isShow = true;" +
                "for(var i=0;i<objs.length;i++){" +
                "    for(var j=0;j<filter.length;j++){" +
                "        if(objs[i].src.indexOf(filter[j])>=0) {" +
                "            isShow = false; " +
                "            break;" +
                "        }" +
                "    }" +
                "    if(isShow && objs[i].width>160 && objs[i].height>160){" +
                "        imgUrl += objs[i].src + ',';" +
                "        isShow = true;" +
                "    }" +
                "}" +
                "window.imageListener.collectImage(imgUrl);" +
                "})()"
    }


    @JavascriptInterface
    fun collectImage(imageUrl: String) {
        val array = imageUrl.split(",")
        pictures.clear()
        pictures.addAll(array)
    }
}