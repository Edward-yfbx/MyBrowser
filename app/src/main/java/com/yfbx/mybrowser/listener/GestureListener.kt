package com.yfbx.mybrowser.listener;

import android.view.GestureDetector
import android.view.MotionEvent

/**
 * Author: Edward
 * Date: 2019/1/2
 * Description:
 */


class GestureListener(val listener: (Boolean) -> Unit) : GestureDetector.SimpleOnGestureListener() {


    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        //横向速度 > 纵向速度
        if (Math.abs(velocityX) - Math.abs(velocityY) > 200) {
            listener.invoke(velocityX < 0)
        }
        return false
    }


}
