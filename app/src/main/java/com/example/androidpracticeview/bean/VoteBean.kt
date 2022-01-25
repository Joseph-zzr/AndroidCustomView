package com.example.androidpracticeview.bean

/**
 * <pre>
 *      @author : Joseph
 *      e-mail  : 913870737@qq.com
 *      date    : 2022年1月25日
 *      desc    : 投票的bean类
 *      version : 1.0
 * </pre>
 */
class VoteBean(
    val id: Int = 0,
    val title: String?,
    val choiceType: String?,
    val maxSelect: Int?,
    var voted: Boolean?,
    val sumVoteCount: Int?,
    val options: ArrayList<VoteOption>?
)

data class VoteOption(var id: Int?,
                      var content: String?,
                      var voteId: Int?,
                      var showCount: Int?,
                      var voted: Boolean?)