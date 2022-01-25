package com.example.androidpracticeview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidpracticeview.R
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sina_vote)
        voteview.setVoteTextSize(voteview.sp2px(15))
            .setVoteUncheckedContentTextColor(resources.getColor(R.color.unchecked_content_text_color))
            .setVoteCheckedContentTextColor(resources.getColor(R.color.checked_content_text_color))
            .setVoteUncheckedProgressColor(resources.getColor(R.color.unchecked_progress_color))
            .setVoteBorderColor(resources.getColor(R.color.border_color))
            .setVoteBorderRadius(voteview.dp2px(3f))
            .setVoteContent("测试")
            .invalidate()
    }
}