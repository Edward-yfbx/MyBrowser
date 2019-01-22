package com.yfbx.mybrowser.adapter

import android.text.TextUtils
import com.squareup.picasso.Picasso
import com.yfbx.mybrowser.R
import com.yfbx.mybrowser.bean.BookMark
import kotlinx.android.synthetic.main.item_history.view.*
import java.io.File

/**
 * Author: Edward
 * Date: 2019/1/4
 * Description:
 */

class CollectionAdapter(data: MutableList<BookMark>) : BaseAdapter<BookMark>(data) {

    override fun getLayout(): Int {
        return R.layout.item_history
    }

    override fun bindData(helper: Helper, position: Int, item: BookMark) {
        val path = item.getFavicon()
        if (TextUtils.isEmpty(path)) {
            helper.itemView.historyIcon.setImageResource(R.mipmap.ic_world)
        } else {
            Picasso.get().load(File(path)).into(helper.itemView.historyIcon)
        }

        helper.itemView.historyName.text = item.title
        helper.itemView.historyLink.text = item.url
    }

}