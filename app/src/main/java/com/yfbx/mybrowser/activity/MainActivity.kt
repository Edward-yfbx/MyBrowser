package com.yfbx.mybrowser.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.view.View
import com.yfbx.mybrowser.R
import com.yfbx.mybrowser.bean.BookMark
import com.yfbx.mybrowser.fragment.WebFrag
import com.yfbx.mybrowser.helper.Popups
import com.yfbx.mybrowser.util.ResultHelper
import com.yfbx.mybrowser.util.createAppDirs
import com.yfbx.mybrowser.util.show
import kotlinx.android.synthetic.main.layout_navigation_bar.*


class MainActivity : BaseActivity(), View.OnClickListener {


    private val pageList = mutableListOf<WebFrag>()
    private lateinit var menu: Popups
    private var current = WebFrag()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission()

        setEnable(false, false)
        backwardBtn.setOnClickListener { current.goBack() }
        forwardBtn.setOnClickListener { current.goForward() }
        menuBtn.setOnClickListener { menu.showMainMenuPopup(menuBtn, this) }
        homeBtn.setOnClickListener { onHome() }

        menu = Popups(this)

        switchPage(current)

    }

    /**
     * 请求权限
     */
    private fun requestPermission() {
        ResultHelper(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, {
            if (it) {
                createAppDirs()
            }
        })
    }

    /**
     * Menu 菜单
     */
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.menuCollection -> onCollection()
            R.id.menuCollect -> BookMark().collect(current.getCurrent())
            R.id.menuHistory -> onHistory()
            R.id.menuRefresh -> current.reload()
            R.id.menuSetting -> toActivity(SettingActivity::class.java)
            R.id.menuNight -> show("开发中...")
            R.id.menuDownload -> toActivity(FileActivity::class.java)
            R.id.menuExit -> onExit()
        }
        menu.dismiss()
    }


    /**
     * 主页按钮
     */
    private fun onHome() {
        current.clearHistory()
        current.loadHomePage()
    }


    /**
     * 前进后退按钮状态
     */
    fun setEnable(canBack: Boolean, canForward: Boolean) {
        forwardBtn.isEnabled = canForward
        backwardBtn.isEnabled = canBack
    }


    /**
     * 收藏夹
     */
    private fun onCollection() {
        val intent = Intent(this, CollectionActivity::class.java)
        ResultHelper(this).startForResult(intent, { code, data ->
            if (code == Activity.RESULT_OK) {
                val url = data!!.getStringExtra("url")
                current.load(url)
            }
        })
    }

    /**
     * 历史
     */
    private fun onHistory() {
        val intent = Intent(this, HistoryActivity::class.java)
        ResultHelper(this).startForResult(intent, { code, data ->
            if (code == Activity.RESULT_OK) {
                val url = data!!.getStringExtra("url")
                current.load(url)
            }
        })
    }

    /**
     * 创建新的Web页
     */
    fun newWebPage(url: String) {
        val frag = WebFrag()
        val bundle = Bundle()
        bundle.putString("url", url)
        frag.arguments = bundle

        pageList.add(frag)
        switchPage(frag)
    }

    /**
     * 切换Fragment
     */
    private fun switchPage(fragment: WebFrag) {
        val transaction = supportFragmentManager.beginTransaction()
        if (!fragment.isAdded) {
            pageList.add(fragment)
            transaction.add(R.id.main_frag_body, fragment)
        }
        transaction.hide(current)
        transaction.show(fragment)
        current = fragment
        transaction.commit()
    }

    /**
     * 退出
     */
    private fun onExit() {
        finish()
        Process.killProcess(Process.myPid())
    }


    /**
     * 返回按键
     */
    override fun onBackPressed() {
        if (menu.isShowing) {
            menu.dismiss()
            return
        }
        if (current.canGoBack()) {
            current.goBack()
            return
        }
        onExit()
    }


}
