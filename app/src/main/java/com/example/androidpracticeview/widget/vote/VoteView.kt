package com.example.androidpracticeview.widget.vote

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.example.androidpracticeview.R

/**
 * <pre>
 *      @author : Joseph
 *      e-mail  : 913870737@qq.com
 *      date    : 2022年1月23日
 *      desc    : 新浪微博投票控件里面的item
 *      version : 1.0
 * </pre>
 */
class VoteView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :View(context, attrs, defStyleAttr) {

    private var mWidth = 0
    private var mHeight = 0

    private var bgRectF = RectF()
    private var progressRectF = RectF()
    private var voteContentRectF = Rect()

    private var voteContentBaseline = 0

    private var mProgress = -1f
    private var mScale = 1f

    private var mVoteContent: String? = null


    private var bgPaint: Paint? = null
    private var progressPaint: Paint? = null
    private var borderPaint: Paint? = null
    private var voteContentTextPaint: Paint? = null

    private var textPaintSize: Int = 0

    private var checkedProgressColor = 0
    private var unCheckedProgressColor = 0

    private var checkedContentTextColor = 0
    private var uncheckedContentTextColor = 0

    private var checkedResultTextColor = 0
    private var uncheckedResultTextColor = 0

    private var borderColor = 0
    private var borderRadius = 0f

    private var isVoteChecked = false
    private var textWidth = 0

    private val defaultCheckedProgressColor = Color.argb(1, 255, 124, 5)
    private val defaultUncheckedProgressColor = Color.parseColor("#F3F3F3")
    private val defaultCheckedTextColor = Color.parseColor("#FF7C05")
    private val defaultUncheckedTextColor = Color.parseColor("#1a1a1a")
    private val defaultBorderColor = Color.parseColor("#e6e6e6")


    init {
        initAttr(context, attrs, defStyleAttr)

        initPaint()

        initColor()

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h

        setBgRect()
        setProgressRect()

        setVoetContextRect()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawBg(canvas)
        drawProgress(canvas)
        drawBorder(canvas)

        drawVoteContentText(canvas)
    }


    private fun initPaint() {
        bgPaint = getPaint(dp2px(0.5f), Color.WHITE, Paint.Style.FILL)
        progressPaint = getPaint(dp2px(0.5f), unCheckedProgressColor, Paint.Style.FILL)
        borderPaint = getPaint(dp2px(0.5f), borderColor, Paint.Style.STROKE)

        voteContentTextPaint = getTextPaint(uncheckedContentTextColor, textPaintSize.toFloat())

    }

    private fun initColor() {
        bgPaint?.color = if (isVoteChecked) checkedProgressColor else unCheckedProgressColor
        progressPaint?.color = if (isVoteChecked) checkedProgressColor else unCheckedProgressColor
        voteContentTextPaint?.color = if (isVoteChecked) checkedContentTextColor else uncheckedContentTextColor
    }

    private fun initAttr(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VoteView)

        checkedProgressColor = typedArray.getColor(R.styleable.VoteView_voteCheckedProgressColor, defaultCheckedProgressColor)
        unCheckedProgressColor = typedArray.getColor(R.styleable.VoteView_voteUncheckedProgressColor, defaultUncheckedProgressColor)

        checkedContentTextColor = typedArray.getColor(R.styleable.VoteView_voteCheckedContentTextColor, defaultCheckedTextColor)
        uncheckedContentTextColor = typedArray.getColor(R.styleable.VoteView_voteUncheckedContentTextColor, defaultUncheckedTextColor)

        checkedResultTextColor = typedArray.getColor(R.styleable.VoteView_voteCheckedResultTextColor, defaultCheckedTextColor)
        uncheckedResultTextColor = typedArray.getColor(R.styleable.VoteView_voteUncheckedResultTextColor, defaultUncheckedTextColor)

        textPaintSize = typedArray.getDimensionPixelSize(R.styleable.VoteView_voteTextSize, sp2px(15))

        borderColor = typedArray.getColor(R.styleable.VoteView_voteBorderColor, defaultBorderColor)
        borderRadius = typedArray.getDimensionPixelOffset(R.styleable.VoteView_voteBorderRadius, dp2px(1f).toInt()).toFloat()

//        animDuration = typedArray.getInt(R.styleable.VoteView_voteAnimDuration, 500).toLong()

//        rightCheckedBitmapRes = (typedArray.getDrawable(R.styleable.VoteView_voteCheckedIcon) as? BitmapDrawable)?.bitmap

//        rightIconWidth = typedArray.getDimensionPixelOffset(R.styleable.VoteView_voteRightIconWidth, 0)
//        rightIconHeight = typedArray.getDimensionPixelOffset(R.styleable.VoteView_voteRightIconHeight, 0)

        typedArray.recycle()
    }

        /**
     * 画整体白色背景
     */
    private fun drawBg(canvas: Canvas) {
        if (mProgress != -1f) {
            bgPaint?.color = Color.WHITE
        }
        canvas.drawRoundRect(bgRectF, 0f, 0f, bgPaint!!)
    }

    /**
     * 绘制的进度条
     */
    private fun drawProgress(canvas: Canvas) {
        if (mProgress == -1f) return
        canvas.drawRoundRect(getProgressRectF(), 0f, 0f, progressPaint!!)
    }

    /**
     * 边界
     */
    private fun drawBorder(canvas: Canvas) {
        borderPaint?.color = borderColor
        canvas.drawRoundRect(bgRectF, borderRadius, borderRadius, borderPaint!!)
    }

    /**
     * view里面的文字
     */
    private fun drawVoteContentText(canvas: Canvas) {
        if (mVoteContent.isNullOrBlank()) return
        //文字绘制到整个布局的中心位置
        if (mProgress == -1f) {
            voteContentRectF.left = (mWidth - textWidth) / 2
            voteContentRectF.right = voteContentRectF.left + textWidth
        } else {

        }
        canvas.drawText(
            mVoteContent!!,
            voteContentRectF.centerX().toFloat(),
            voteContentBaseline.toFloat(),
            voteContentTextPaint!!
        )

    }



    private fun getPaint(strokeWidth: Float, color: Int, style: Paint.Style): Paint {
        return Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.strokeWidth = strokeWidth
            this.color = color
            this.style = style
            this.isAntiAlias = true
        }
    }

    private fun getTextPaint(color: Int, textSize: Float): Paint {
        return Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.textSize = textSize
            this.color = color
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
    }

    private fun setProgressRect() {
        progressRectF.set(0f, 0f, 0f, mHeight.toFloat())
    }

    private fun getProgressRectF(): RectF {
        val currentProgress = mProgress * mWidth * mScale
        progressRectF.set(0f, 0f, currentProgress, mHeight.toFloat())
        return progressRectF
    }

    /**
     * 整个view的边界
     */
    private fun setBgRect() {
        bgRectF.set(0f, 0f, mWidth.toFloat(), mHeight.toFloat())
    }

    private fun setVoetContextRect() {
        if (mVoteContent.isNullOrBlank()) return
        voteContentTextPaint!!.getTextBounds(mVoteContent, 0, mVoteContent!!.length, voteContentRectF)

        textWidth = (voteContentRectF.right - voteContentRectF.left)
        with(voteContentRectF) {
            top = 0
            bottom = mHeight
            left = (mWidth - textWidth) / 2
            right = voteContentRectF.left + textWidth
        }

        val fontMetrics = voteContentTextPaint!!.fontMetricsInt
        //这里就相当于，取控件一半高度 + Ascent的半高度，刚好就是baseline的高度
        voteContentBaseline = (voteContentRectF.bottom + voteContentRectF.top - fontMetrics.bottom - fontMetrics.top) /3
    }

    fun setProgress(progress: Float) {
        mProgress = progress
        if (mProgress != -1f) {
            invalidate()
        }
    }

    fun setVoteCheckedProgressColor(color: Int): VoteView {
        this.checkedProgressColor = color
        return this
    }

    fun setVoteUncheckedProgressColor(color: Int): VoteView {
        this.unCheckedProgressColor = color
        return this
    }

    fun setVoteCheckedContentTextColor(color: Int): VoteView {
        this.checkedContentTextColor = color
        return this
    }

    fun setVoteUncheckedContentTextColor(color: Int): VoteView {
        this.uncheckedContentTextColor = color
        return this
    }

    fun setVoteBorderColor(color: Int): VoteView {
        this.borderColor = color
        return this
    }

    fun setVoteBorderRadius(radius: Float): VoteView {
        this.borderRadius = radius
        return this
    }

    fun setVoteIsSelected(isVoteSelected: Boolean): VoteView {
        this.isVoteChecked = isVoteSelected
        return this
    }

    fun setVoteTextSize(textSize: Int): VoteView{
        textPaintSize = textSize
        return this
    }

    fun setVoteContent(content: String?): VoteView {
        mVoteContent = content ?: ""

        return this
    }

    fun dp2px(dpVal: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal, resources.displayMetrics)
    }

    fun sp2px(spVal: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            spVal.toFloat(), resources.displayMetrics).toInt()
    }
}