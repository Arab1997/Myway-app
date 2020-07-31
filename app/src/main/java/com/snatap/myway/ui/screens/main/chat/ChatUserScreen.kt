package com.snatap.myway.ui.screens.main.chat

import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.ChatMessageAdapter
import com.snatap.myway.utils.extensions.inDevelopment
import com.snatap.myway.utils.extensions.showGone
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.screen_chat_user.*
import kotlinx.coroutines.launch
import java.io.File

class ChatUserScreen : BaseFragment(R.layout.screen_chat_user) {

    companion object {
        private var isSingleChat = true
        fun newInstance(isSingleChat: Boolean): ChatUserScreen {
            this.isSingleChat = isSingleChat
            return ChatUserScreen()
        }
    }

    private lateinit var adapter: ChatMessageAdapter
    override fun initialize() {

        back.setOnClickListener { finishFragment() }
//        chatImg.loadImage() //todo
//        chatName.text  //todo
//        chatDesc.text  //todo

        more.showGone(!isSingleChat)
        more.setOnClickListener { addFragment(MembersScreen()) }

        adapter = ChatMessageAdapter(true, sharedManager)
        recycler.adapter = adapter

        attach.setOnClickListener { inDevelopment(requireContext()) }

        img.setOnClickListener {
            PickImageDialog.build(PickSetup()).setOnPickResult { result ->
                lifecycleScope.launch {
                    val compressedImg = Compressor.compress(requireContext(), File(result.path))
                }

            }.show(childFragmentManager)
        }

        send.setOnClickListener {
            messageEdt.text.toString().let {
                if (it.isNotEmpty()) {
                    messageEdt.text?.clear()
                    viewModel.sendMessageChats(it)
                }
            }
        }
    }

    override fun observe() {
        viewModel.getChats()

        viewModel.chats.observe(viewLifecycleOwner, Observer {
            adapter.setData(ArrayList(it))
            recycler.layoutManager?.scrollToPosition(it.lastIndex)
        })
    }
}