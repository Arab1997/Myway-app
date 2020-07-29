package com.snatap.myway.ui.screens.main.events

import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.ui.adapters.CommentAdapter
import com.snatap.myway.ui.adapters.GalleryAdapter
import com.snatap.myway.ui.adapters.SponsorsAdapter
import com.snatap.myway.utils.extensions.inDevelopment
import kotlinx.android.synthetic.main.content_comments.*
import kotlinx.android.synthetic.main.content_events_toolbar.*
import kotlinx.android.synthetic.main.screen_event_detail.*

class EventDetailsScreen : BaseFragment(R.layout.screen_event_detail) {

    override fun initialize() {

        setClicks()

        initRecycler()
    }

    private lateinit var sponsorAdapter: SponsorsAdapter
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var commentAdapter: CommentAdapter

    private fun initRecycler() {

        val data = arrayListOf(1, 2, 3, 4, 5, "")
        sponsorAdapter = SponsorsAdapter {
        }.apply { setData(data) }
        galleryAdapter = GalleryAdapter {
            addFragment(GalleryScreen())
        }.apply { setData(data) }
        commentAdapter = CommentAdapter(sharedManager)

        recyclerSponsors.adapter = sponsorAdapter
        recyclerGallery.adapter = galleryAdapter
        recyclerComments.adapter = commentAdapter
    }

    private fun setClicks() {

        back.setOnClickListener { finishFragment() }

        right.setOnClickListener { inDevelopment(requireContext()) }

        rightExtra.setOnClickListener { inDevelopment(requireContext()) }

        participate.setOnClickListener {
            val bottomSheet = ParticipateBottomSheet().apply {
                setListener {
                    addFragment(TicketScreen())
                }
            }
            bottomSheet.show(childFragmentManager, "")
        }

        participants.setOnClickListener {
            val bottomSheet = ParticipantsBottomSheet()
            bottomSheet.show(childFragmentManager, "")
        }

    }
}

