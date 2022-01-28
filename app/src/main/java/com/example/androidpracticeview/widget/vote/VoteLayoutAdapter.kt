package com.example.androidpracticeview.widget.vote

import android.view.View
import android.view.ViewGroup
import com.example.androidpracticeview.bean.VoteBean
import com.example.androidpracticeview.bean.VoteOption
import java.lang.ref.WeakReference

/**
 * <pre>
 *      @author : Joseph
 *      e-mail  : 913870737@qq.com
 *      date    : 2022年1月26日
 *      desc    : vote
 *      version : 1.0
 * </pre>
 */
class VoteLayoutAdapter(private val viewGroup: ViewGroup){

    private var viewHoldersList = mutableListOf<VoteViewHolder>()
    var onVoteClickListener: OnAdapterVoteClickListener? = null

    fun setData(voteBeanList: ArrayList<VoteBean>?) {
        viewGroup.removeAllViews()
        viewHoldersList.clear()
        if (voteBeanList == null || voteBeanList.size <= 0){
            viewGroup.visibility = View.GONE
        } else {
            viewGroup.visibility = View.VISIBLE
            for (i in 0 until voteBeanList.size) {
                val viewHolder = onCreateViewHolder(viewGroup, i)
                viewHolder.bind(voteBeanList[i])
                viewHoldersList.add(viewHolder)
                viewGroup.addView(viewHolder.voteContainerView)
            }
        }
    }

    fun refreshDataAfterVotedSuccess(position: Int) {
        viewHoldersList[position].voteContainerView.refreshDataAfterVoteSuccess()
    }

//    fun refreshDataAfterVotedFailed(position: Int) {
//        viewHoldersList[position].voteContainerView.refreshDataAfterVoteFailed()
//    }

    fun onDestroy() {
        viewHoldersList.forEach {
            it.voteContainerView.onDestroy()
        }
    }
    private fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): VoteViewHolder {
        val view = VoteContainerView(viewGroup.context)
        return VoteViewHolder(view, this, position)
    }

    class VoteViewHolder(val voteContainerView: VoteContainerView, adapter: VoteLayoutAdapter, var position: Int){

        private val adapterRef = WeakReference(adapter)
        private fun ref(): VoteLayoutAdapter? {
            return adapterRef.get()
        }

        private var mMainVote: VoteBean? = null
        fun bind(voteBean: VoteBean) {
            mMainVote = voteBean
            voteContainerView.setVoteData(voteBean)
            voteContainerView.onVoteClickListener = object : VoteContainerView.OnVoteClickListener {
                override fun onVoteCommitBtnClick(mainVote: VoteBean?, optionIds: ArrayList<Int>) {
                    ref()?.onVoteClickListener?.OnAdapterVoteCommitBtnClick(mainVote, optionIds, position)
                }

                override fun onVoteItemClick(mainVote: VoteBean?, voteOption: VoteOption?) {
                    ref()?.onVoteClickListener?.OnAdapterVoteItemClick(mainVote, voteOption, position)
                }

            }
        }
    }

    interface OnAdapterVoteClickListener {
        fun OnAdapterVoteCommitBtnClick(mainVote: VoteBean?, optionIds: ArrayList<Int>, position: Int)
        fun OnAdapterVoteItemClick(mainVote: VoteBean?, voteOption: VoteOption?, position: Int)
    }
}