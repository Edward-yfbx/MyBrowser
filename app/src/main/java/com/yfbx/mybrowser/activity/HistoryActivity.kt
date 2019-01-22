package com.yfbx.mybrowser.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.yfbx.mybrowser.R
import com.yfbx.mybrowser.adapter.HistoryAdapter
import com.yfbx.mybrowser.bean.History
import kotlinx.android.synthetic.main.activity_history.*
import org.litepal.LitePal
import kotlin.concurrent.thread

/**
 * Author: Edward
 * Date: 2019/1/4
 * Description:
 */

class HistoryActivity : BaseActivity() {


    private lateinit var data: MutableList<History>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setToolbar(R.string.history)

        findData()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.clear_history, menu)
        return true
    }

    /**
     * 清除历史记录
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        LitePal.deleteAll(History::class.java)
        data.clear()
        historyRecycler.adapter.notifyDataSetChanged()
        return true
    }

    /**
     * 查找数据
     *
     */
    private fun findData() {
        thread {
            data = LitePal.order("id DESC").find(History::class.java)
            runOnUiThread { setAdapter() }
        }
    }

    /**
     * 设置Adapter
     */
    private fun setAdapter() {
        val adapter = HistoryAdapter(data)
        historyRecycler.adapter = adapter
        adapter.setOnItemClick(this::onItemClick)
    }

    /**
     * 列表点击事件
     */
    private fun onItemClick(position: Int, item: History) {
        val intent = Intent()
        intent.putExtra("url", item.url)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}