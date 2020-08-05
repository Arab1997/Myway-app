package com.snatap.myway.ui.screens.main.home.news

import androidx.lifecycle.Observer
import com.snatap.myway.R
import com.snatap.myway.base.BaseFragment
import com.snatap.myway.network.models.Comment
import com.snatap.myway.network.models.NewsDetail
import com.snatap.myway.network.models.NewsDetailResp
import com.snatap.myway.ui.adapters.CommentAdapter
import com.snatap.myway.ui.adapters.SimilarNewsAdapter
import com.snatap.myway.ui.adapters.TagsAdapter
import com.snatap.myway.utils.extensions.*
import kotlinx.android.synthetic.main.content_add_comments.*
import kotlinx.android.synthetic.main.content_comments.*
import kotlinx.android.synthetic.main.screen_news_detailed.*

class NewsDetailScreen : BaseFragment(R.layout.screen_news_detailed) {

    companion object {
        private var newsId = 0
        fun newInstance(newsId: Int): NewsDetailScreen {
            this.newsId = newsId
            return NewsDetailScreen()
        }
    }

    private lateinit var commentAdapter: CommentAdapter
    private var comments = arrayListOf<Comment>()
    private lateinit var similarNewsAdapter: SimilarNewsAdapter

    override fun initialize() {

        commentAdapter = CommentAdapter(sharedManager)

        similarNewsAdapter = SimilarNewsAdapter {
            addFragment(newInstance(it.id))
        }

        recyclerComments.adapter = commentAdapter

        recyclerSimilarNews.adapter = similarNewsAdapter

        back.setOnClickListener { finishFragment() }

        mark.setOnClickListener { viewModel.addBookmark(newsId) }

        share.setOnClickListener { shareText(requireContext(), "In development") /*todo*/ }

        changeFontSize.setOnClickListener { inDevelopment(requireContext()) }

        questions.setOnClickListener { inDevelopment(requireContext()) } // todo open victorina

        liked.setOnClickListener { viewModel.addLike(newsId) }

        allComments.setOnClickListener { openComments() }

        addComment.apply {
            isClickable = false
            isFocusable = false
            setOnClickListener { openComments() }
        }
    }

    private fun openComments() = addFragment(CommentsScreen.newInstance(newsId))

    override fun observe() {
        viewModel.getNewsDetail(newsId)

        viewModel.data.observe(viewLifecycleOwner, Observer {
            if (it is NewsDetailResp) initViews(it.news_item)
        })
    }

    private fun initViews(it: NewsDetail) {

        mark.setImageResource(if (it.is_bookmarked) R.drawable.ic_marked_white else R.drawable.ic_mark_white)
        author.text = it.author_name
        avatar.loadImage(it.author_avatar)
        created.text = it.created_at.formatTime()
        title.text = it.title
        desc.text = it.description.fromHtml()
        shortDesc.text = it.short_description.fromHtml()
        photo.loadImage(it.photo)

        liked.showGone(!it.is_liked)

        relatedNews.showGone(it.related.isNotEmpty())

        similarNewsAdapter.setData(ArrayList(it.related))

        recyclerTags.adapter = TagsAdapter().apply {
            setData(ArrayList(it.tags))
        }

        if (it.comments_count != 0) {
            contentComments.visible()
            commentCount.text = it.comments_count.toString()
            if (it.comments_count > 2) commentAdapter.setData(ArrayList(it.comments.subList(0, 2)))
            else commentAdapter.setData(ArrayList(it.comments))

            comments = ArrayList(it.comments)
            userImg.loadImage(sharedManager.user.avatar)
        }
    }

}
