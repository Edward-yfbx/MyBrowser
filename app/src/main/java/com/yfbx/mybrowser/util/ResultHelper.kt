package com.yfbx.mybrowser.util

import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity


/**
 * Author:Edward
 * Date:2018/9/13
 * Description:
 */

class ResultHelper(activity: FragmentActivity) {

    private val TAG = "ResultHelper"
    private var frag: ForResultFragment

    init {
        val manager = activity.supportFragmentManager
        val fragment = manager.findFragmentByTag(TAG)
        if (fragment == null) {
            frag = ForResultFragment()
            manager.beginTransaction().add(frag, TAG).commitAllowingStateLoss()
            manager.executePendingTransactions()
        } else {
            frag = fragment as ForResultFragment
        }
    }


    fun startForResult(intent: Intent, listener: (Int, Intent?) -> Unit) {
        frag.startForResult(intent, listener)
    }

    fun startForResult(clazz: Class<*>, listener: (Int, Intent?) -> Unit) {
        startForResult(Intent(frag.activity, clazz), listener)
    }

    fun request(permissions: Array<String>, listener: (Boolean) -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            frag.getPermissions(permissions, listener)
        } else {
            listener.invoke(true)
        }
    }

    fun request(permission: String, listener: (Boolean) -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            frag.getPermissions(arrayOf(permission), listener)
        } else {
            listener.invoke(true)
        }
    }
}


class ForResultFragment : Fragment() {

    private val resultMap = mutableMapOf<Int, (Int, Intent?) -> Unit>()
    private val permissionMap = mutableMapOf<Int, (Boolean) -> Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    /***
     * 跳转Activity
     */
    fun startForResult(intent: Intent, listener: (Int, Intent?) -> Unit) {
        //Can only use lower 16 bits for requestCode
        val requestCode = listener.hashCode().shr(16)
        resultMap.put(requestCode, listener)
        startActivityForResult(intent, requestCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val listener = resultMap.remove(requestCode)
        listener?.invoke(resultCode, data)
    }


    /**
     * 请求权限
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun getPermissions(permissions: Array<String>, listener: (Boolean) -> Unit) {
        //Can only use lower 16 bits for requestCode
        val requestCode = listener.hashCode().shr(16)
        permissionMap.put(requestCode, listener)
        requestPermissions(permissions, requestCode)
    }

    /**
     * 权限返回
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        val listener = permissionMap.remove(requestCode)
        val isGrant = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
        listener?.invoke(isGrant)
    }

}
