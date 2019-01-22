package com.yfbx.mybrowser.helper;

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.annotation.LayoutRes
import android.support.v7.widget.PagerSnapHelper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.squareup.picasso.Picasso
import com.yfbx.mybrowser.R
import com.yfbx.mybrowser.adapter.GalleryAdapter
import kotlinx.android.synthetic.main.dialog_gallery.view.*
import kotlinx.android.synthetic.main.dialog_img.view.*
import kotlinx.android.synthetic.main.dialog_menu.view.*

/**
 * Author: Edward
 * Date: 2019/1/15
 * Description:
 */


class Popups(private val context: Context) : PopupWindow() {

    private val MATCH = ViewGroup.LayoutParams.MATCH_PARENT
    private val WRAP = ViewGroup.LayoutParams.WRAP_CONTENT

    private fun create(width: Int, height: Int, @LayoutRes layout: Int) {
        this.width = width
        this.height = height
        this.contentView = LayoutInflater.from(context).inflate(layout, null)
        this.isOutsideTouchable = true
        setBackgroundDrawable(ColorDrawable())
    }


    /**
     * 首页菜单
     */
    fun showMainMenuPopup(parent: View, listener: View.OnClickListener) {
        create(MATCH, WRAP, R.layout.dialog_menu)
        animationStyle = R.style.MenuAnim
        contentView.menuCollection.setOnClickListener(listener)
        contentView.menuCollect.setOnClickListener(listener)
        contentView.menuHistory.setOnClickListener(listener)
        contentView.menuRefresh.setOnClickListener(listener)
        contentView.menuSetting.setOnClickListener(listener)
        contentView.menuNight.setOnClickListener(listener)
        contentView.menuDownload.setOnClickListener(listener)
        contentView.menuExit.setOnClickListener(listener)
        showAtLocation(parent, Gravity.BOTTOM, 0, 0)
    }


    /**
     * 图片展示PopupWindow
     */
    fun showPicturePopup(parent: View, url: String) {
        create(MATCH, MATCH, R.layout.dialog_img)
        Picasso.get().load(url).into(contentView.popupImg)
        contentView.setOnClickListener({ dismiss() })
        showAtLocation(parent, Gravity.CENTER, 0, 0)
    }


    /**
     * 图片展示PopupWindow
     */
    fun showGalleryPopup(parent: View, gallery: MutableList<String>) {
        create(MATCH, MATCH, R.layout.dialog_gallery)
        contentView.galleryRecycler.adapter = GalleryAdapter(gallery)
        PagerSnapHelper().attachToRecyclerView(contentView.galleryRecycler)
        contentView.galleryClose.setOnClickListener { dismiss() }
        showAtLocation(parent, Gravity.CENTER, 0, 0)
    }
}
