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
import kotlinx.android.synthetic.main.view_item_radio_picker.view.*
import kotlinx.android.synthetic.main.view_layout_radio_picker.view.*

class PickerData(val name: String, var isChecked: Boolean = false)

class RadioPicker : LinearLayout {

    private var activePos: Int = 0

    private var pickerList = arrayListOf<PickerData>()
    private lateinit var adapter: PickerAdapter

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @SuppressLint("Recycle")
    private fun init(context: Context, attrs: AttributeSet?) {
        View.inflate(context, R.layout.view_layout_radio_picker, this)
    }

    fun setItems(list: List<String>) {

        pickerList = ArrayList(list.map { PickerData(it, false) })

        initRecycler()

        if (pickerList.isNotEmpty()) {
            pickerList.forEach { it.isChecked = false }
            pickerList[activePos].isChecked = true
            adapter.setData(pickerList)
        }
    }

    fun getCheckedItems() = pickerList.filter { it.isChecked }

    private fun initRecycler() {
        adapter = PickerAdapter {
            pickerList.forEach { it.isChecked = false }

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

    class PickerAdapter(private val listener: (Int) -> Unit) : RecyclerView.Adapter<ViewHolder>() {

        private var data = ArrayList<PickerData>()
        fun setData(data: ArrayList<PickerData>) {
            this.data = data
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_item_radio_picker, parent, false)
            )
        }

        override fun getItemCount(): Int = data.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.apply {
                data[holder.adapterPosition].apply {
                    radioName.text = name

                    radio.setImageResource(
                        if (isChecked) R.drawable.ic_radio_checked
                        else R.drawable.ic_not_checked_dot
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