package dev.software.api

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


fun RecyclerView.addPaginationScrollListener(
    layoutManager: LinearLayoutManager,
    itemsToLoad: Int,
    loadMore: () -> Unit
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val totalItems = layoutManager.itemCount
            println("totalItemCount $totalItems")

            val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
            println("lastVisibleItem $lastVisibleItem")

            if (dy != 0 && totalItems <= (lastVisibleItem + itemsToLoad)) {
                loadMore()
            }
        }
    })
}


fun RecyclerView.addSpaceDecoration(bottomSpace: Int) {

    addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val itemCount = parent.adapter?.itemCount ?: return
            val position = parent.getChildAdapterPosition(view)
            if (position != itemCount - 1) {
                outRect.bottom = bottomSpace
            }

        }
    })
}