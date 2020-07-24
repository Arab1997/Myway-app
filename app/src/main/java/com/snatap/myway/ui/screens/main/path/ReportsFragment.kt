package com.snatap.myway.ui.screens.main.path

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.utils.common.ViewHolder
import kotlinx.android.synthetic.main.content_rounded_toolbar_info.*
import kotlinx.android.synthetic.main.fragment_reports.*
import kotlin.math.roundToInt


class ReportsFragment : BaseFragment(R.layout.fragment_reports){
    override fun initialize() {
        setClicks()

        initViews()

        recyclerReports.adapter = ReportsAdapter().apply {
            setData(arrayListOf(1,2,3,4,5,6,7))
        }
    }

    private fun initViews(){
        back.visibility = View.GONE
        title.text = "Мои задания"
        val img = requireContext().resources.getDrawable(R.drawable.ic_close)
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 24F, resources.displayMetrics
        ).roundToInt()
        img.setBounds(0, 0, px, px)
        info.setCompoundDrawables(img, null, null, null)
        info.text = ""
    }

    private fun setClicks(){
        info.setOnClickListener { finishFragment() }
        sendTaskBtn.setOnClickListener { addFragment(SendTaskScreen()) }
    }
}

class ReportsAdapter: RecyclerView.Adapter<ViewHolder>() {
    private var data = arrayListOf<Any>()
    fun setData(data: ArrayList<Any>) {
        this.data = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.item_report, parent, false))

    override fun getItemCount(): Int = data.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

}