package com.example.androidpracticeview.widget.vote

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import com.example.androidpracticeview.R

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

    private var vote_container_vote_btn: Button? = null

    init {
        initFindViewById()
        initView()
    }

    private fun initFindViewById() {
        vote_container_vote_btn = findViewById(R.id.vote_container_vote_btn)
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.widget_vote_layout, this)

        vote_container_vote_btn?.setOnClickListener{

        }
    }

}