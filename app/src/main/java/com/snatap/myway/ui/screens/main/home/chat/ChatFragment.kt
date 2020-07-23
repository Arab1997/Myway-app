package com.snatap.myway.ui.screens.main.home.chat

import android.content.res.Resources
import android.graphics.Canvas
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.ChatAdapter
import kotlinx.android.synthetic.main.content_rounded_toolbar_info.*
import kotlinx.android.synthetic.main.fragment_chats.*
import kotlinx.android.synthetic.main.item_chat.view.*


class ChatFragment : BaseFragment(R.layout.fragment_chats) {
    private lateinit var adapter: ChatAdapter
    private var lastItemPos: Int? = null

    override fun initialize() {

        initViews()

    }

    private fun initViews(){
        title.text = "Чаты"
        val img = requireContext().resources.getDrawable(R.drawable.ic_active_bell)
        val dip = 24f
        val r: Resources = resources
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            r.displayMetrics
        )
        img.setBounds(0, 0, px.toInt(), px.toInt())
        info.setCompoundDrawables(img, null, null, null)
        info.text = ""

        adapter = ChatAdapter{
            addFragment(ChatWithUserScreen())
        }.apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6, 7))
        }
        recyclerChats.adapter = adapter


        val itemTouchHelper: ItemTouchHelper = ItemTouchHelper(setItemCallback())
        itemTouchHelper.attachToRecyclerView(recyclerChats)
    }

    private fun setItemCallback(): ItemTouchHelper.SimpleCallback {
        return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    lastItemPos = viewHolder.adapterPosition
                    if (dX < -1)
                        itemView.removeLayout.visibility = View.VISIBLE
                }

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.notifyDataSetChanged()
            }
        }
    }

}

