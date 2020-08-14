package com.yfbx.mybrowser.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.yfbx.mybrowser.activity.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

/**
 * Author: Edward
 * Date: 2019/1/9
 * Description:
 */

abstract class BaseFragment : Fragment(), CoroutineScope by MainScope() {


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