package com.yfbx.mybrowser.adapter

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Author: Edward
 * Date: 2019/1/10
 * Description:
 */

abstract class BaseAdapter<T>(var data: MutableList<T>) : RecyclerView.Adapter<BaseAdapter<T>.Helper>() {

    private var listener: ((Int, T) -> Unit)? = null


    fun setOnItemClick(listener: ((Int, T) -> Unit)) {
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Helper {
        val view = LayoutInflater.from(parent.context).inflate(getLayout(), parent, false)
        view.setOnClickListener(this::onItemClicked)
        return Helper(view)
    }

    override fun onBindViewHolder(holder: Helper, position: Int) {
        holder.itemView.tag = position
        bindData(holder, position, data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }


    private fun onItemClicked(v: View) {
        if (listener != null) {
            val position = v.tag as Int
            listener!!.invoke(position, data[position])
        }
    }

    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun bindData(helper: Helper, position: Int, item: T)

    inner class Helper(itemView: View) : RecyclerView.ViewHolder(itemView)

}