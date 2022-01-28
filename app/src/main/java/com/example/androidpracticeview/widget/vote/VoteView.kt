package com.example.androidpracticeview.widget.vote

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.example.androidpracticeview.R
import kotlin.math.max

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
    private var voteRightIconRectF = Rect()
    private var voteResultTextRectF = Rect()

    private var voteContentBaseline = 0
    private var voteResultBaseline = 0

    private var mProgress = -1f
    private var mScale = 1f

    private var mVoteContent: String? = null
    private var mVoteResult: String? = null


    private var bgPaint: Paint? = null
    private var progressPaint: Paint? = null
    private var borderPaint: Paint? = null
    private var voteContentTextPaint: Paint? = null
    private var voteResultTextPaint: Paint? = null
    private var iconPaint: Paint? = null


    private var rightCheckedBitmapRes: Bitmap? = null
    private var valueAnimator: ValueAnimator? = null
    private var rightIconWidth = 0
    private var rightIconHeight = 0

    private var animDuration = 1000L

    private var textPaintSize: Int = 0

    private var textMarginLeft = 0
    private var voteIconMarginLeft = 0

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

    private val defaultCheckedProgressColor = Color.argb(1,255,124,1)
    private val defaultUncheckedProgressColor = Color.parseColor("#F3F3F3")
    private val defaultCheckedTextColor = Color.parseColor("#FF7C05")
    private val defaultUncheckedTextColor = Color.parseColor("#1a1a1a")
    private val defaultBorderColor = Color.parseColor("#e6e6e6")


    init {
        textMarginLeft = dp2px(15f).toInt()
        voteIconMarginLeft = dp2px(15f).toInt()
        initAttr(context, attrs, defStyleAttr)

        initPaint()

        initVoteRightIcon()

        initColor()
    }

    private fun initVoteRightIcon() {
        if (rightCheckedBitmapRes != null) {
            if (rightIconWidth == 0 || rightIconHeight == 0) {
                val iconSize = dp2px(36f).toInt()
                rightIconWidth = iconSize
                rightIconHeight = iconSize
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h

        setBgRect()
        setProgressRect()

        setVoetContextRect()
        setResultTextRectF()
        setVoteRightIconRect()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawBg(canvas)
        drawProgress(canvas)
        drawBorder(canvas)

        drawVoteContentText(canvas)
        drawVoteResultText(canvas)
        drawVoteRightIcon(canvas)
    }




    private fun initPaint() {
        iconPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            isFilterBitmap = true
            isDither = true
        }
        bgPaint = getPaint(dp2px(0.5f), Color.WHITE, Paint.Style.FILL)
        progressPaint = getPaint(dp2px(0.5f), unCheckedProgressColor, Paint.Style.FILL)
        borderPaint = getPaint(dp2px(0.5f), borderColor, Paint.Style.STROKE)

        voteContentTextPaint = getTextPaint(uncheckedContentTextColor, textPaintSize.toFloat())
        voteResultTextPaint = getTextPaint(checkedResultTextColor, textPaintSize.toFloat())

    }

    private fun initColor() {
        bgPaint?.color = if (isVoteChecked) checkedProgressColor else unCheckedProgressColor
        progressPaint?.color = if (isVoteChecked) checkedProgressColor else unCheckedProgressColor
        voteContentTextPaint?.color = if (isVoteChecked) checkedContentTextColor else uncheckedContentTextColor
        voteResultTextPaint?.color = if (isVoteChecked) checkedResultTextColor else uncheckedResultTextColor
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

        animDuration = typedArray.getInt(R.styleable.VoteView_voteAnimDuration, 500).toLong()

        rightCheckedBitmapRes = (typedArray.getDrawable(R.styleable.VoteView_voteCheckedIcon) as? BitmapDrawable)?.bitmap

        rightIconWidth = typedArray.getDimensionPixelOffset(R.styleable.VoteView_voteRightIconWidth, 0)
        rightIconHeight = typedArray.getDimensionPixelOffset(R.styleable.VoteView_voteRightIconHeight, 0)

        typedArray.recycle()
    }

    private fun drawVoteResultText(canvas: Canvas) {
        if(mProgress == -1f || mVoteResult.isNullOrBlank()) return
        voteResultTextPaint?.alpha = (255 * mScale).toInt()
        canvas.drawText(mVoteResult!!,
            (mWidth - voteIconMarginLeft - voteResultTextRectF.centerX()).toFloat(),
            voteResultBaseline.toFloat(),
            voteResultTextPaint!!)
    }

    private fun drawVoteRightIcon(canvas: Canvas) {
        if (rightCheckedBitmapRes != null && isVoteChecked) {
            voteRightIconRectF.left = voteContentRectF.right + voteIconMarginLeft
            voteRightIconRectF.right = voteRightIconRectF.left + rightIconWidth
            canvas.drawBitmap(rightCheckedBitmapRes!!, null, voteRightIconRectF, iconPaint)
        }
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
            voteContentRectF.left = max(((1 - mScale) * (mWidth - textWidth) / 2).toInt(), textMarginLeft)
            voteContentRectF.right = voteContentRectF.left + textWidth
        }
        canvas.drawText(
            mVoteContent!!,
            voteContentRectF.centerX().toFloat(),
            voteContentBaseline.toFloat(),
            voteContentTextPaint!!
        )

    }

    fun refreshView() {
        initColor()
        invalidate()
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

    private fun setVoteRightIconRect() {
        voteRightIconRectF.set(voteContentRectF.right + voteIconMarginLeft,
            (mHeight - rightIconHeight) / 2,
            voteContentRectF.right + voteIconMarginLeft + rightIconWidth,
            (mHeight + rightIconHeight) / 2)
    }

    private fun getProgressRectF(): RectF {
        val currentProgress = mProgress * mWidth * mScale
        progressRectF.set(0f, 0f, currentProgress, mHeight.toFloat())
        return progressRectF
    }

    private fun setResultTextRectF() {
        if(mVoteResult.isNullOrBlank()) return
        voteResultTextPaint!!.getTextBounds(mVoteResult, 0, mVoteResult!!.length, voteResultTextRectF)

        voteResultTextRectF.top = 0
        voteResultTextRectF.bottom = mHeight

        val fontMetrics = voteResultTextPaint!!.fontMetricsInt
        voteResultBaseline = (voteResultTextRectF.bottom + voteResultTextRectF.top - fontMetrics.bottom - fontMetrics.top) / 2
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
        voteContentBaseline = (voteContentRectF.bottom + voteContentRectF.top - fontMetrics.bottom - fontMetrics.top) / 2
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

    fun setVoteCheckedIcon(iconBitmap: Drawable): VoteView {
        this.rightCheckedBitmapRes = (iconBitmap as? BitmapDrawable)?.bitmap
        return this
    }

    fun setVoteRightIconSize(width_height: Int): VoteView {
        this.rightIconWidth = width_height
        this.rightIconHeight = width_height
        return this
    }

    fun setVoteCheckedResultTextColor(color: Int): VoteView {
        this.checkedResultTextColor = color
        return this
    }

    fun setVoteUncheckedResultTextColor(color: Int): VoteView {
        this.uncheckedResultTextColor = color
        return this
    }

    fun setProcessWithAnim(progress: Float) {
        mProgress = progress
        startAnim()
    }

    fun setAnimDuration(duration: Long) {
        animDuration = duration
    }

    fun setVoteResultText(result: String): VoteView {
        mVoteResult = result
        return this
    }


    private fun startAnim() {
        valueAnimator?.cancel()
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        }

        valueAnimator?.apply {
            duration = animDuration
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                mScale = it.animatedValue as Float
                invalidate()
            }
            start()
        }

    }

    fun onDestroy() {
        valueAnimator?.cancel()
        valueAnimator = null
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