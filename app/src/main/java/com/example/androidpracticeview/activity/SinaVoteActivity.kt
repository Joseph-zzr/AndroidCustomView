package com.example.androidpracticeview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.allen.androidcustomview.data.getMockData
import com.example.androidpracticeview.R
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
class SinaVoteActivity : AppCompatActivity() {

    private var vote_LinearLayout: LinearLayout? = null
    var voteLayoutAdapter: VoteLayoutAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sina_vote)
        vote_LinearLayout = findViewById(R.id.vote_ll)
        voteLayoutAdapter = VoteLayoutAdapter(vote_LinearLayout!!)
        voteLayoutAdapter?.setData(getMockData())
    }
}