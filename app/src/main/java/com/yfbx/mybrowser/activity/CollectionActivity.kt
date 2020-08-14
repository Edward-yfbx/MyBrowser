package com.yfbx.mybrowser.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.yfbx.adapter.adapter
import com.yfbx.mybrowser.R
import com.yfbx.mybrowser.bean.BookMark
import com.yuxiaor.base.net.network
import kotlinx.android.synthetic.main.activity_collection.*
import kotlinx.android.synthetic.main.item_history.view.*

/**
 * Author: Edward
 * Date: 2019/1/4
 * Description:收藏
 */

class CollectionActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        setToolbar(R.string.collection)
        collectionRecycler.adapter = adapter
        network {
            val data = BookMark.findAll()
            adapter.setNewData(data)
        }
    }

    /**
     * 列表点击事件
     */
    private fun onItemClick(item: BookMark) {
        val intent = Intent()
        intent.putExtra("url", item.url)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private val adapter = adapter<BookMark>(R.layout.item_history) { helper, item ->
        helper.itemView.historyName.text = item.title
        helper.itemView.historyLink.text = item.url
        helper.itemView.setOnClickListener { onItemClick(item) }
    }

}