package com.yfbx.mybrowser.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.yfbx.mybrowser.R
import com.yfbx.mybrowser.adapter.CollectionAdapter
import com.yfbx.mybrowser.bean.BookMark
import kotlinx.android.synthetic.main.activity_collection.*
import org.litepal.LitePal
import kotlin.concurrent.thread

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
        setData()
    }


    private fun setData() {
        thread {
            val data = LitePal.findAll(BookMark::class.java, true)
            runOnUiThread { setAdapter(data) }
        }
    }


    private fun setAdapter(data: MutableList<BookMark>) {
        val adapter = CollectionAdapter(data)
        collectionRecycler.adapter = adapter
        adapter.setOnItemClick(this::onItemClickListener)
    }


    /**
     * 列表点击事件
     */
    private fun onItemClickListener(position: Int, item: BookMark) {
        val intent = Intent()
        intent.putExtra("url", item.url)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}