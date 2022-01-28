package com.example.androidpracticeview.widget.vote

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
    var onVoteClickListener: OnVoteClickListener? = null

    companion object {
        val VOTE_TYPE_MULTIPLE = "multiple"
        val VOTE_TYPE_SINGLE = "single"
    }

    private var mData: VoteBean? = null

    /**
     * 记录点击哪个按钮的id
     */
    private var optionIdsList = arrayListOf<Int>()


    init {
        initView()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.widget_vote_layout, this)

        //投票按钮
        vote_container_vote_btn?.setOnClickListener{
            if (mData == null) return@setOnClickListener
            onVoteClickListener?.onVoteCommitBtnClick(mData, optionIdsList)
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
            for (index in 0 until data.options.size) {
                val voteOption = data.options[index]
                val viewHolder: VoteItemViewHodler = onCreateViewHolder()
                setVoteStatues(data)
                setVoteBtnStatues()
                viewHolder.bind(index, voteOption, data)
                voteViewHoldersList.add(viewHolder)
                vote_item_ll?.addView(viewHolder.voteView)
            }
        }
    }

    private fun setVoteBtnStatues() {
        val buttonClickable = optionIdsList.size > 0
        if (buttonClickable) {
            vote_container_vote_btn.setBackgroundResource(R.drawable.shape_bg_clickable)
        } else {
            vote_container_vote_btn.setBackgroundResource(R.drawable.shape_bg_un_clickable)
        }
        vote_container_vote_btn.isClickable = buttonClickable
    }

    private fun setVoteStatues(data: VoteBean) {
        if (data.choiceType == VOTE_TYPE_MULTIPLE) {//多选
            vote_container_vote_btn.visibility = View.VISIBLE
        } else {//单选
            vote_container_vote_btn.visibility = View.GONE
        }
    }

    private fun onCreateViewHolder(): VoteItemViewHodler {
        return VoteItemViewHodler(getVoteView(context), this)
    }
    
    fun refreshDataAfterVoteSuccess() {
        vote_container_vote_btn.visibility = View.GONE
        vote_container_vote_result.visibility = View.VISIBLE
        refreshVoteResult()
        startprogressAnim()
    }

    private fun refreshVoteResult() {
        val voteResult = (mData?.sumVoteCount ?: 0)
        setVoteResult("共${(voteResult + 1)}人参与了投票")
    }

    private fun setVoteResult(result: String) {
        vote_container_vote_result.text = result ?: ""
    }

    private fun startprogressAnim() {
        voteViewHoldersList.forEach{
            it.setProgress()
        }
    }

    private fun getVoteView(context: Context?): VoteView {
        val voteView = VoteView(context!!)
        voteView.setVoteTextSize(voteView.sp2px(15))
                .setVoteUncheckedContentTextColor(resources.getColor(R.color.unchecked_content_text_color))
                .setVoteCheckedContentTextColor(resources.getColor(R.color.checked_content_text_color))
                .setVoteUncheckedResultTextColor(resources.getColor(R.color.unchecked_result_text_color))
                .setVoteCheckedResultTextColor(resources.getColor(R.color.checked_result_text_color))
                .setVoteUncheckedProgressColor(resources.getColor(R.color.unchecked_progress_color))
                .setVoteCheckedProgressColor(resources.getColor(R.color.checked_progress_color))
                .setVoteBorderColor(resources.getColor(R.color.border_color))
                .setVoteBorderRadius(voteView.dp2px(3f))
                .setVoteCheckedIcon(resources.getDrawable(R.mipmap.icon_vote_check))
                .setVoteRightIconSize(voteView.dp2px(18f).toInt())
                .setAnimDuration(2000L)
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, voteView.dp2px(40F).toInt())
        layoutParams.bottomMargin = voteView.dp2px(12f).toInt()
        voteView.layoutParams = layoutParams
        return voteView
    }

    fun onDestroy() {
        voteViewHoldersList.forEach {
            it.onVoteDestroy()
        }
    }

    private fun setVoteTitle(title: String?) {
        vote_container_title?.text = title ?: ""
    }

    private fun addOptionIds(id: Int){
        optionIdsList.add(id)
    }

    private fun removeOptionIds(id: Int) {
        optionIdsList.remove(id)
    }

    private fun getOptionIdsSize(): Int {
        return optionIdsList.size
    }

    class VoteItemViewHodler(
            var voteView: VoteView,
            voteContainerView: VoteContainerView) {

        private val voteContainerViewRef = WeakReference(voteContainerView)

        private fun ref(): VoteContainerView? {
            return voteContainerViewRef.get()
        }
        private var data: VoteOption? = null
        private var mainVote: VoteBean? = null
        var isVoteMulti = true

        private fun isHaveVoted(): Boolean {
            return mainVote?.voted ?: false
        }


        fun bind(position: Int, voteOption: VoteOption, mainVote: VoteBean) {
            this.data = voteOption
            this.mainVote = mainVote

            isVoteMulti = mainVote.choiceType == VOTE_TYPE_MULTIPLE

            val voteResultCount = voteOption.showCount ?: 0

            voteView.setVoteIsSelected(voteOption.voted ?: false)
                .setVoteContent(voteOption.content)
                .setVoteResultText("${voteResultCount}人")
                .refreshView()

            if (isHaveVoted()) {
                val sum = mainVote?.sumVoteCount ?: 0
                val showCount = data?.showCount ?: 0
                val progress = if (sum == 0) 0f else showCount.toFloat() / sum.toFloat()

                voteView.setProgress(progress)
            }

            voteView.setOnClickListener{
                if (isHaveVoted()) return@setOnClickListener
                if (isVoteMulti) {
                    setMultiChoice(voteView, voteOption)
                } else {
                    setSingleChoice(voteView, voteOption)
                }
                ref()?.onVoteClickListener?.onVoteItemClick(mainVote, data)
            }
        }

        fun onVoteDestroy() {
            voteView.onDestroy()
        }

        private fun setSingleChoice(voteView: VoteView, voteOption: VoteOption) {
            val optionsIdSize = ref()?.optionIdsList?.size ?: 0
            if (optionsIdSize > 0){
                return
            }
            ref()?.addOptionIds(voteOption.id ?: 0)
            voteOption.voted = true
            voteView.setVoteIsSelected(voteOption.voted ?: false).refreshView()
            ref()?.onVoteClickListener?.onVoteCommitBtnClick(mainVote, ref()?.optionIdsList ?: arrayListOf())
        }

        private fun setMultiChoice(voteView: VoteView, voteOption: VoteOption) {
            if (voteOption.voted == true){
                voteOption.voted = false
                ref()?.removeOptionIds(voteOption.id ?: 0)
            } else {
                val optionsIdSize = ref()?.optionIdsList?.size ?: 0
                val maxSelect = mainVote?.maxSelect ?: 0
                if (optionsIdSize < maxSelect) {
                    voteOption.voted = true
                    ref()?.addOptionIds(voteOption.id ?:0 )
                } else {
                    Toast.makeText(ref()?.context, "最多可选${maxSelect}个", Toast.LENGTH_SHORT).show()
                }
            }
            ref()?.setVoteBtnStatues()
            voteView.setVoteIsSelected(voteOption.voted ?: false).refreshView()
        }

        fun setProgress() {
            val sum = mainVote?.sumVoteCount ?: 0
            var showCount = data?.showCount ?: 0
            val realShowCount = if (data?.voted == true) showCount + 1 else showCount
            voteView.setVoteResultText("${realShowCount}人")
            mainVote?.voted = true
            val progress = if (sum == 0) 0f else realShowCount.toFloat() / sum.toFloat()
            voteView.setProcessWithAnim(progress)
        }
    }
    interface OnVoteClickListener {
        fun onVoteCommitBtnClick(mainVote: VoteBean?, optionIds: ArrayList<Int>)
        fun onVoteItemClick(mainVote: VoteBean?, voteOption: VoteOption?)
    }
}