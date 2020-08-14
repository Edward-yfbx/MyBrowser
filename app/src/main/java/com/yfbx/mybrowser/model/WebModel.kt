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
    fun grapePictureScript(): String {
        return """ 
           javascript:(function(){
               var elements = document.getElementsByTagName("img");
               var imgUrl = "";
               var filter = ["img//EventHead.png","img//kong.png","hdtz//button.png"];
               var isShow = true;
               for(var i=0;i<elements.length;i++){
                   for(var j=0;j<filter.length;j++){
                      if(elements[i].src.indexOf(filter[j])>=0) {
                         isShow = false;
                          break;
                      }
                   }
                   if(isShow && elements[i].width>160 && elements[i].height>160){
                      imgUrl += elements[i].src + ',';
                      isShow = true;
                   }
               }
               window.imageListener.collectImage(imgUrl);
               })()
           """
    }


    @JavascriptInterface
    fun collectImage(imageUrl: String) {
        val array = imageUrl.split(",")
        pictures.clear()
        pictures.addAll(array)
    }
}