package com.project.monopad.ui.view.custom.viewpager

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.viewpager.widget.PagerAdapter
import com.project.monopad.R
import com.project.monopad.model.entity.Day
import com.project.monopad.model.entity.Review
import java.util.*

class CalendarPagerAdapter2(private val context : Context) : PagerAdapter() {

    companion object{
        const val NUMBER_OF_PAGES = 12*10
    }
    private var onDayClickListener: ((Int, Day) -> Unit)? = null

    fun setonDayClickListener(listener: ((Int, Day) -> Unit)) {
        this.onDayClickListener = listener
    }

    private var viewContainer: ViewGroup? = null


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val gridView = GridView(context)
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        gridView.apply {
            overScrollMode = GridView.OVER_SCROLL_NEVER
            layoutParams = params
            numColumns = 7

            adapter = CalendarGridAdapter(context, position)

            setSelector(R.drawable.calendar_list_selector)
            setOnItemClickListener { adapterView, view, pos, l ->
                val item = adapterView.getItemAtPosition(pos) as Day
                onDayClickListener?.invoke(getMonth(position), item)
            }
        }

        container.addView(gridView)
        viewContainer = container

        return gridView
    }


    override fun getCount(): Int = NUMBER_OF_PAGES

    override fun isViewFromObject(view: View, `object`: Any): Boolean
            = (view == `object`)

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    fun getMonth(position: Int) : Int{
        return Calendar.getInstance().apply {
            add(Calendar.MONTH, position - NUMBER_OF_PAGES / 2)
        }
            .get(Calendar.MONTH)
    }

    fun setList(list : List<Review>) {
        Log.e("SEULGI adapter list",""+list.size)
        val views = viewContainer ?: return
        (0 until views.childCount).forEach { i ->
            ((views.getChildAt(i) as? GridView)?.adapter as? CalendarGridAdapter)?.setList(list)
        }
    }

}