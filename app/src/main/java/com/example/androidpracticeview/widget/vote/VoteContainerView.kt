package com.example.androidpracticeview.widget.vote

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.example.androidpracticeview.R
import com.example.androidpracticeview.bean.VoteBean
import com.example.androidpracticeview.bean.VoteOption
import kotlinx.android.synthetic.main.widget_vote_layout.view.*
import java.lang.ref.WeakReference

/**
 * <pre>
 *      @author : Joseph
 *      e-mail  : 913870737@qq.com
 *      date    : 2022年1月23日
 *      desc    : 投票控件自定义ViewGroup
 *      version : 1.0
 * </pre>
 */
class VoteContainerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :FrameLayout(context, attrs, defStyleAttr) {

    private var voteViewHoldersList: ArrayList<VoteItemViewHodler> = arrayListOf()

    private var vote_container_vote_btn: Button? = null
    private var vote_container_title: TextView? = null
    private var vote_item_ll: LinearLayout? = null

    private var mData: VoteBean? = null
    private var optionIdsList = arrayListOf<Int>()


    init {
        initFindViewById()
        initView()
    }

    private fun initFindViewById() {
        vote_container_vote_btn = findViewById(R.id.vote_container_vote_btn)
        vote_container_title = findViewById(R.id.vote_container_title)
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.widget_vote_layout, this)

        //投票按钮
        vote_container_vote_btn?.setOnClickListener{

        }
    }

    fun setVoteData(data: VoteBean?) {
        if (data == null || data.options.isNullOrEmpty()){
            visibility = View.GONE
        } else {
            mData = data
            setVoteTitle(data.title)
            voteViewHoldersList.clear()
            optionIdsList.clear()
            vote_item_ll?.removeAllViews()
            data.options?.forEachIndexed{ index, voteOption ->
                val viewHolder: VoteItemViewHodler = onCreateViewHolder()
                viewHolder
            }
        }
    }

    private fun onCreateViewHolder(): VoteItemViewHodler {
        return VoteItemViewHodler(getVoteView(context), this)
    }

    private fun getVoteView(context: Context?): VoteView {
        val voteView = VoteView(context!!)
        voteView.setVoteTextSize(voteView.sp2px(15))
                .setVoteUncheckedContentTextColor(resources.getColor(R.color.unchecked_content_text_color))
                .setVoteCheckedContentTextColor(resources.getColor(R.color.checked_content_text_color))
                .setVoteBorderColor(resources.getColor(R.color.border_color))
                .setVoteBorderRadius(voteView.dp2px(3f))
        return voteView
    }

    private fun setVoteTitle(title: String?) {
        vote_container_title?.text = title ?: ""
    }

    class VoteItemViewHodler(
            var voteView: VoteView,
            voteContainerView: VoteContainerView) {

        private val voteContainerViewRef = WeakReference(voteContainerView)

        private var data: VoteOption? = null
        private var mainVote: VoteBean? = null

        fun bind(position: Int, voteOption: VoteOption, mainVote: VoteBean) {
            this.data = voteOption
            this.mainVote = mainVote


        }
    }

}