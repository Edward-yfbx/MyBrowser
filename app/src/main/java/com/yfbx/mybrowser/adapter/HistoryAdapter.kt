package com.yfbx.mybrowser.adapter

import android.text.TextUtils
import com.yfbx.mybrowser.R
import com.yfbx.mybrowser.bean.History
import kotlinx.android.synthetic.main.item_history.view.*
import java.io.File

/**
 * Author: Edward
 * Date: 2019/1/4
 * Description:
 */

class HistoryAdapter(data: MutableList<History>) : BaseAdapter<History>(data) {

    override fun getLayout(): Int {
        return R.layout.item_history
    }

    override fun bindData(helper: Helper, position: Int, item: History) {
        helper.itemView.historyName.text = item.title
        helper.itemView.historyLink.text = item.url
    }

}