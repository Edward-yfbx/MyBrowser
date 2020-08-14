package com.yfbx.mybrowser.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.yfbx.adapter.adapter
import com.yfbx.mybrowser.R
import com.yfbx.mybrowser.bean.History
import com.yuxiaor.base.net.network
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.item_history.view.*
import org.litepal.LitePal

/**
 * Author: Edward
 * Date: 2019/1/4
 * Description:
 */

class HistoryActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setToolbar(R.string.history)
        historyRecycler.adapter = adapter
        network {
            val data = History.findAll()
            adapter.setNewData(data)
        }
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
        adapter.clear()
        return true
    }

    /**
     * 列表点击事件
     */
    private fun onItemClick(item: History) {
        val intent = Intent()
        intent.putExtra("url", item.url)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private val adapter = adapter<History>(R.layout.item_history) { helper, item ->
        helper.itemView.historyName.text = item.title
        helper.itemView.historyLink.text = item.url
        helper.itemView.setOnClickListener { onItemClick(item) }
    }
}