package com.tmvlg.smsreceiver.util

import android.graphics.Canvas
import android.graphics.Rect
import android.util.Pair
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class ViewHolderStickyDecoration @JvmOverloads constructor(parent: RecyclerView, private val condition: Condition, private val reverseLayout: Boolean = false) : ItemDecoration() {
    private val bounds = Rect()
    private var currentHeader: Pair<Int, RecyclerView.ViewHolder>? = null
    private fun init(parent: RecyclerView) {
        val adapter = parent.adapter ?: throw IllegalArgumentException("Firstly set adapter")
        adapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onChanged() {
                clearHeader()
            }
        })
        parent.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom -> clearHeader() }
    }

    private fun clearHeader() {
        currentHeader = null
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (state.itemCount == 0) {
            return
        }
        val topChild = getTopChild(parent) ?: return
        val topPosition = parent.getChildAdapterPosition(topChild)
        val header = getHeaderViewForItem(topPosition, parent) ?: return
        val contactPoint = header.bottom
        val contactChild = getChildInContact(parent, contactPoint) ?: return
        if (condition.isHeader(parent.getChildAdapterPosition(contactChild))) {
            moveHeader(c, header, contactChild)
            return
        }
        drawHeader(c, header)
    }

    private fun getTopChild(parent: RecyclerView): View? {
        return if (reverseLayout) {
            parent.findChildViewUnder(0f, 0f)
        } else parent.getChildAt(0)
    }

    private fun getHeaderViewForItem(position: Int, parent: RecyclerView): View? {
        val adapter = parent.adapter ?: return null
        val headerPosition = if (reverseLayout) getHeaderPositionForItemRevense(adapter.itemCount, position) else getHeaderPositionForItem(position)
        if (headerPosition == RecyclerView.NO_POSITION) {
            return null
        }
        val viewType = adapter.getItemViewType(headerPosition)
        if (currentHeader != null && headerPosition == currentHeader!!.first && currentHeader!!.second.itemViewType == viewType) {
            return currentHeader!!.second.itemView
        }
        val holder = adapter.createViewHolder(parent, viewType)
        adapter.onBindViewHolder(holder, headerPosition)
        fixViewSize(parent, holder.itemView)
        currentHeader = Pair(headerPosition, holder)
        return holder.itemView
    }

    private fun getHeaderPositionForItem(position: Int): Int {
        var headerPosition = RecyclerView.NO_POSITION
        var currentPosition = position
        do {
            if (condition.isHeader(currentPosition)) {
                headerPosition = currentPosition
                break
            }
            currentPosition -= 1
        } while (currentPosition >= 0)
        return headerPosition
    }

    private fun getHeaderPositionForItemRevense(totalCount: Int, position: Int): Int {
        var headerPosition = RecyclerView.NO_POSITION
        var currentPosition = position
        do {
            if (condition.isHeader(currentPosition)) {
                headerPosition = currentPosition
                break
            }
            currentPosition += 1
        } while (currentPosition < totalCount)
        return headerPosition
    }

    private fun fixViewSize(parent: ViewGroup, view: View) {
        // Specs for parent (RecyclerView)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        // Specs for children (headers)
        val childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, parent.paddingLeft + parent.paddingRight, view.layoutParams.width)
        val childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, parent.paddingTop + parent.paddingBottom, view.layoutParams.height)
        view.measure(childWidthSpec, childHeightSpec)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, bounds)
            if (bounds.bottom > contactPoint && bounds.top <= contactPoint) {
                return child
            }
        }
        return null
    }

    private fun moveHeader(c: Canvas, header: View, nextHeader: View) {
        c.save()
        c.translate(0f, (nextHeader.top - header.height).toFloat())
        header.draw(c)
        c.restore()
    }

    private fun drawHeader(c: Canvas, header: View) {
        c.save()
        c.translate(0f, 0f)
        header.draw(c)
        c.restore()
    }

    interface Condition {
        fun isHeader(position: Int): Boolean
    }

    init {
        init(parent)
    }
}