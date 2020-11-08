package com.topnotch.developers.interfaces

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class EndlessRecyclerOnScrollListener(private var layoutManager: GridLayoutManager) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (!recyclerView.canScrollVertically(1)) {
            onScrolledToEnd()
        }
    }

    abstract fun onScrolledToEnd()

    init {
        layoutManager = layoutManager
    }
}