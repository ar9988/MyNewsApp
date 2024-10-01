package com.example.mynewsapp.ui.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemSpacingDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = spacing/2
        outRect.right = spacing/2
        outRect.bottom = spacing

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = spacing
        }
    }
}