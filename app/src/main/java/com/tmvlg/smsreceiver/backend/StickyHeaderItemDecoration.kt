import android.graphics.Canvas
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class StickyHeaderItemDecoration(@LayoutRes private val headerId: Int, private val HEADER_TYPE: Int) : RecyclerView.ItemDecoration() {

    private lateinit var stickyHeaderView: View
    private lateinit var headerView: View

    private var sticked = false

    // executes on each bind and sets the stickyHeaderView
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)

        val adapter = parent.adapter ?: return
        val viewType = adapter.getItemViewType(position)

        if (viewType == HEADER_TYPE) {
            headerView = view
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (::headerView.isInitialized) {

            if (headerView.y <= 0 && !sticked) {
                stickyHeaderView = createHeaderView(parent)
                fixLayoutSize(parent, stickyHeaderView)
                sticked = true
            }

            if (headerView.y > 0 && sticked) {
                sticked = false
            }

            if (sticked) {
                drawStickedHeader(c)
            }
        }
    }

    private fun createHeaderView(parent: RecyclerView) = LayoutInflater.from(parent.context).inflate(headerId, parent, false)

    private fun drawStickedHeader(c: Canvas) {
        c.save()
//        if (sticked)
//            c.translate(0f, Math.max(0f, stickyHeaderView.top.toFloat() - stickyHeaderView.height.toFloat()))
//        else
//            c.translate(0f, stickyHeaderView.top.toFloat() - stickyHeaderView.height.toFloat())
        c.translate(0f, Math.max(0f, stickyHeaderView.top.toFloat() - stickyHeaderView.height.toFloat()))
        headerView.draw(c)
        c.restore()
    }

    private fun fixLayoutSize(parent: ViewGroup, view: View) {

        // Specs for parent (RecyclerView)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        // Specs for children (headers)
        val childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, parent.paddingLeft + parent.paddingRight, view.getLayoutParams().width)
        val childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, parent.paddingTop + parent.paddingBottom, view.getLayoutParams().height)
//
//        val childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, viewGroup.getPaddingLeft() + viewGroup.getPaddingRight(), view.layoutParams.width)
//        val childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, viewGroup.getPaddingTop() + viewGroup.getPaddingBottom(), view.layoutParams.height)

        view.measure(childWidthSpec, childHeightSpec)

        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

}