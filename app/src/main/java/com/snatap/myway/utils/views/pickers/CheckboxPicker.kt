package com.snatap.myway.utils.views.pickers

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.snatap.myway.R
import kotlinx.android.synthetic.main.view_item_checkbox_picker.view.*
import kotlinx.android.synthetic.main.view_layout_checkbox_picker.view.*

class CheckboxPicker : LinearLayout {

    private var pickerList = arrayListOf<PickerData>()
    private lateinit var adapter: PickerAdapter

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    @SuppressLint("Recycle")
    private fun init(context: Context) {
        View.inflate(context, R.layout.view_layout_checkbox_picker, this)
    }

    fun setItems(list: List<String>) {

        pickerList = ArrayList(list.map { PickerData(it, false) })

        initRecycler()
    }

    fun getCheckedItems() = pickerList.filter { it.isChecked }

    private fun initRecycler() {
        adapter = PickerAdapter() {

            pickerList[it].isChecked = !pickerList[it].isChecked
            adapter.setData(pickerList)
            checkedItemsListener?.invoke(getCheckedItems())
        }.apply { setData(pickerList) }

        recycler.adapter = adapter

    }

    private var checkedItemsListener: ((List<PickerData>) -> Unit)? = null
    fun setCheckedItemsListener(checkedItemsListener: (List<PickerData>) -> Unit) {
        this.checkedItemsListener = checkedItemsListener
    }

    class PickerAdapter(private val listener: (Int) -> Unit) :
        RecyclerView.Adapter<ViewHolder>() {

        private var data = ArrayList<PickerData>()
        fun setData(data: ArrayList<PickerData>) {
            this.data = data
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_item_checkbox_picker, parent, false)
            )
        }

        override fun getItemCount(): Int = data.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.apply {
                data[holder.adapterPosition].apply {
                    checkboxName.text = name

                    checkbox.setImageResource(
                        if (isChecked) R.drawable.ic_checkbox_active
                        else R.drawable.ic_checkbox_inactive
                    )

                    itemCategory.setOnClickListener {
                        if (holder.adapterPosition != -1) listener.invoke(holder.adapterPosition)
                    }
                }
            }
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}