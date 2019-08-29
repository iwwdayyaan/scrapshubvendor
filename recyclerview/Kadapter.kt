package com.scrapshubvendor.recyclerview

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View



class Kadapter<in ITEM>(private val items: MutableList<ITEM>, layoutResId: Int,
                        private val bindHolder: View.(ITEM) -> Unit,
                        private val itemClick: ITEM.(View,Int) -> Unit = { view: View, i: Int -> })
    : AbstractAdapter<ITEM>(items, layoutResId) {

    override fun onItemClick(itemView: View, position: Int) {
       items[position].itemClick(itemView,position)
    }

    override fun View.bind(item: ITEM) {
        bindHolder(item)
    }
}

fun <ITEM> RecyclerView.setUp(items: MutableList<ITEM>,
                              layoutResId: Int,
                              bindHolder: View.(ITEM) -> Unit,
                              itemClick: ITEM.(View,Int) -> Unit = {view: View, i: Int -> },
                              manager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)): Kadapter<ITEM> {
    layoutManager = manager
    return Kadapter(items, layoutResId, bindHolder, itemClick).apply { adapter = this }
}