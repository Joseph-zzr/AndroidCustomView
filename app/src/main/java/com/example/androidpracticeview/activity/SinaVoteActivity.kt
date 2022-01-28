package com.example.androidpracticeview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.allen.androidcustomview.data.getMockData
import com.example.androidpracticeview.R
import com.example.androidpracticeview.bean.VoteBean
import com.example.androidpracticeview.bean.VoteOption
import com.example.androidpracticeview.widget.vote.VoteLayoutAdapter
import kotlinx.android.synthetic.main.activity_sina_vote.*


/**
 * <pre>
 *      @author : Joseph
 *      e-mail  : 913870737@qq.com
 *      date    : 2022年1月23日
 *      desc    : 新浪微博投票控件(单选，多选)
 *      version : 1.0
 * </pre>
 */
class SinaVoteActivity : AppCompatActivity(), VoteLayoutAdapter.OnAdapterVoteClickListener {

    var voteLayoutAdapter: VoteLayoutAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sina_vote)
        voteLayoutAdapter = VoteLayoutAdapter(vote_ll!!)
        voteLayoutAdapter?.setData(getMockData())
        voteLayoutAdapter?.onVoteClickListener = this
    }

    override fun onDestroy() {
        super.onDestroy()
        voteLayoutAdapter?.onDestroy()
    }

    override fun OnAdapterVoteCommitBtnClick(
        mainVote: VoteBean?,
        optionIds: ArrayList<Int>,
        position: Int
    ) {
        voteLayoutAdapter?.refreshDataAfterVotedSuccess(position)
    }

    override fun OnAdapterVoteItemClick(
        mainVote: VoteBean?,
        voteOption: VoteOption?,
        position: Int
    ) {

    }
}