package com.internship.move.presentation.menu.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RideHistoryItemDecorator(private val space : Float) :RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.top = space.toInt() / 2
        outRect.bottom = space.toInt() / 2
        outRect.left = space.toInt() / 2
        outRect.right = space.toInt() / 2
    }
}