package com.snatap.myway.ui.screens.main.events

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.Comment
import com.snatap.myway.network.models.Event
import com.snatap.myway.network.models.EventResp
import com.snatap.myway.ui.adapters.CommentAdapter
import com.snatap.myway.ui.adapters.GalleryAdapter
import com.snatap.myway.ui.adapters.SponsorsAdapter
import com.snatap.myway.ui.screens.main.chat.ChatScreen
import com.snatap.myway.ui.screens.main.common.ImageScreen
import com.snatap.myway.ui.screens.main.common.VideoScreen
import com.snatap.myway.ui.screens.main.home.news.CommentsScreen
import com.snatap.myway.ui.screens.main.store.StoreScreen
import com.snatap.myway.utils.extensions.*
import kotlinx.android.synthetic.main.content_add_comments.*
import kotlinx.android.synthetic.main.content_comment_layout.*
import kotlinx.android.synthetic.main.content_comments.*
import kotlinx.android.synthetic.main.content_events_toolbar.*
import kotlinx.android.synthetic.main.screen_event_detail.*


class EventDetailsScreen : BaseFragment(R.layout.screen_event_detail) {

    companion object {
        fun newInstance(data: Event): EventDetailsScreen {
            return EventDetailsScreen().apply {
                arguments = Bundle().apply {
                    putString("data", Gson().toJson(data))
                }
            }
        }
    }

    private lateinit var data: Event
    override fun initialize() {
        data = Gson().fromJson(requireArguments().getString("data"), Event::class.java)

        initViews()

        setClicks()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        data.apply {
            backgroundImage.loadImage(photo)
            nameTitle.text = title
            descTitle.text = short_description.fromHtml()
            date.text = start_date.formatTime4() + " " + location
            partCount.text = participants_count.toString()
            price_rub.text = "$price ₽"

            commentCount.text = comments_count.toString()
            commentsCount.text = comments_count.toString()
            sharesCount.text = shares_count.toString()

            likesCount.apply {
                text = likes_count.toString()

                if (is_liked) {
                    setDrawableStart(R.drawable.ic_liked)
                    setTextColorRes(R.color.red)
                } else {
                    setDrawableStart(R.drawable.ic_like)
                    setTextColorRes(R.color.hint)
                }

                setOnClickListener {
                    it.blockClickable()
                    viewModel.likeEvents(data.id)
                }
            }

            name.text = title
            shortDesc.text = short_description.fromHtml()
            desc.text = description.fromHtml()

            image.loadImage(photo)
            address.text = location

            // todo map
        }
    }

    private lateinit var sponsorAdapter: SponsorsAdapter
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var commentAdapter: CommentAdapter
    private var comments = arrayListOf<Comment>()

    private fun initRecycler(it: Event) {

        sponsorAdapter = SponsorsAdapter().apply { setData(ArrayList(it.sponsors)) }
        galleryAdapter = GalleryAdapter(true) { showTotal, data ->
            when {
                showTotal -> addFragment(GalleryScreen.newInstance(ArrayList(it.gallery_items)))
                data.photo != null -> {
                    addFragment(ImageScreen.newInstance(data.photo))
                    return@GalleryAdapter
                }
                data.video != null -> {
                    addFragment(VideoScreen.newInstance(data.video))
                    return@GalleryAdapter
                }
            }
        }.apply { setData(ArrayList(it.gallery_items)) }

        commentAdapter = CommentAdapter(sharedManager)

        recyclerSponsors.adapter = sponsorAdapter
        recyclerGallery.adapter = galleryAdapter
        recyclerComments.adapter = commentAdapter


        if (it.comments_count != 0) {
            contentComments.visible()
            commentCount.text = it.comments_count.toString()
            if (it.comments_count > 2) commentAdapter.setData(ArrayList(it.comments.subList(0, 2)))
            else commentAdapter.setData(ArrayList(it.comments))

            comments = ArrayList(it.comments)
            userImg.loadImage(sharedManager.user.avatar)
        }
    }

    private fun setClicks() {

        back.setOnClickListener { finishFragment() }

        right.setOnClickListener { addFragment(StoreScreen()) }

        rightExtra.setOnClickListener { addFragment(ChatScreen()) }

        participate.setOnClickListener {
            it.blockClickable()
            val bottomSheet = ParticipateBottomSheet().apply {
                setListener {
                    addFragment(TicketScreen())
                }
            }
            bottomSheet.show(childFragmentManager, "")// todo
        }

        participants.setOnClickListener {
            it.blockClickable()
            val bottomSheet = MembersBottomSheet()
            bottomSheet.show(childFragmentManager, "") // todo
        }

        call.setOnClickListener {
            it.blockClickable()
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:" + data.phone)))
        }
        message.setOnClickListener {
            it.blockClickable()
            inDevelopment(requireContext())
            // todo
        }

        allComments.setOnClickListener { openComments() }

        addComment.apply {
            isClickable = false
            isFocusable = false
            setOnClickListener { openComments() }
        }
    }

    private fun openComments() = addFragment(CommentsScreen.newInstance(data.id, true))

    override fun observe() {
        viewModel.getEventsDetail(data.id)

        viewModel.data.observe(viewLifecycleOwner, Observer {
            if (it is EventResp) {
                data = it.event
                initViews()
                initRecycler(it.event)
            }
        })
    }
}

