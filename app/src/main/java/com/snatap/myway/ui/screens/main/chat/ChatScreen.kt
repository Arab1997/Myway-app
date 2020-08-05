package com.snatap.myway.ui.screens.main.chat

import android.app.AlertDialog
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.ChatAdapter
import com.snatap.myway.utils.extensions.inDevelopment
import kotlinx.android.synthetic.main.content_rounded_toolbar.*
import kotlinx.android.synthetic.main.screen_recycler.*

class ChatScreen : BaseFragment(R.layout.screen_recycler) {

    private lateinit var adapter: ChatAdapter
    override fun initialize() {

        initViews()
    }

    private fun initViews() {
        title.text = "Чаты"

        right.setImageResource(R.drawable.ic_active_bell)

        back.setOnClickListener { finishFragment() }

        right.setOnClickListener { addFragment(NotificationScreen()) }

        adapter = ChatAdapter { data, delete ->
            if (delete) delete(data)
            else addFragment(ChatUserScreen.newInstance(true))
        }.apply {
            setData(arrayListOf(1, 2, 3, 4, 5, 6, 7))
        }
        recycler.adapter = adapter

        swipeLayout.setOnRefreshListener {
            removePreviousCallback({
                swipeLayout?.isRefreshing = false
            })
            // todo
        }
    }

    private fun delete(data: Any) {
        AlertDialog.Builder(requireContext())
            .setTitle("Удалить Чат")
            .setMessage("Вы точно хотите удалить этот чат")
            .setPositiveButton("Да") { _, _ ->
//                showProgress(true)
                inDevelopment(requireContext())// todo
            }
            .setNegativeButton("Нет") { _, _ ->
            }.show()
    }

}

