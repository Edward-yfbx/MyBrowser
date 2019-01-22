package com.yfbx.mybrowser.adapter

import android.text.TextUtils
import com.squareup.picasso.Picasso
import com.yfbx.mybrowser.R
import kotlinx.android.synthetic.main.item_picture.view.*

/**
 * Author: Edward
 * Date: 2019/1/16
 * Description:
 */

class GalleryAdapter(data: MutableList<String>) : BaseAdapter<String>(data) {


    override fun getLayout(): Int {
        return R.layout.item_picture
    }

    override fun bindData(helper: Helper, position: Int, item: String) {
        if (!TextUtils.isEmpty(item)) {
            Picasso.get().load(item).into(helper.itemView.item_gallery)
        }
    }

}