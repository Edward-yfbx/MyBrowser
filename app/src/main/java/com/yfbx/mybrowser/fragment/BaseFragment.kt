package com.yfbx.mybrowser.fragment

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yfbx.mybrowser.activity.MainActivity

/**
 * Author: Edward
 * Date: 2019/1/9
 * Description:
 */

abstract class BaseFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreate(view, savedInstanceState)
    }

    abstract fun onCreate(view: View, savedInstanceState: Bundle?)

    @LayoutRes
    abstract fun getLayout(): Int


    fun getParent(): MainActivity {
        return activity as MainActivity
    }
}