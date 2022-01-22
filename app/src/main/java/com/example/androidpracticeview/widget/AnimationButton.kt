package com.example.androidpracticeview.widget

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

/**
 * <pre>
 *      @author : Joseph
 *      e-mail  : 913870737@qq.com
 *      date    : 2022年1月22日
 *      desc    : 自带动画效果的按钮
 *      version : 1.0
 * </pre>
 */
class AnimationButton@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private var mWidget: Int = 0
    private var mHeight: Int = 0

    /**
     * 圆角半径
     */
    private var mCircleAngle = 0

    /**
     * button的画笔
     */
    private var mPaint: Paint? = null

    /**
     * 文字画笔
     */
    private var mTextPaint: Paint? = null

    /**
     * 背景颜色
     */
    private var bg_color: Int = -0x4382ad

    /**
     * 按钮的文字内容
     */
    private var buttonString = "确认完成"

    /**
     * 文字在矩形的范围
     */
    private var mTextRect: Rect = Rect()

    /**
     * 矩形范围
     */
    private var mRectf: RectF = RectF()

    /**
     * 两圆圆心之间的距离
     */
    private var two_circle_distance = 0

    /**
     * 动画执行时间
     */
    private var duration = 1000

    /**
     * view向上移动距离
     */
    private var move_distance = 300

    /**
     * 绘制对勾（√）的动画
     */
    private var animator_draw_ok: ValueAnimator? = null

    /**
     * 是否开始绘制对勾
     */
    private var startDrawOk = false

    /**
     * 动画集
     */
    private val mAnimatorSet: AnimatorSet = AnimatorSet()

    /**
     * 矩形到圆角矩形过度的动画
     */
    private var animator_rect_to_angle: ValueAnimator? = null

    /**
     * 矩形到正方形过度的动画
     */
    private var animator_rect_to_square: ValueAnimator? = null

    /**
     * view上移的动画
     */
    private var animator_move_to_up: ObjectAnimator? = null

    /**
     * 路径--用来获取对勾的路径
     */
    private var path = Path()

    /**
     * 取路径的长度
     */
    private var pathMeasure: PathMeasure? = null

    /**
     * 对路径处理实现绘制动画效果
     */
    private var effect: PathEffect? = null

    /**
     * 对勾（√）画笔
     */
    private var okPaint: Paint? = null

    /**
     * 矩形两边到中间圆的距离，圆的直径就是矩形的高度
     */
    private var default_two_circle_distance = 0

    var mAimationButtonListener: AnimationButtonListener? = null
        set(value) {
            field = value
        }

    init {
        initPaint()
        setOnClickListener {
            mAimationButtonListener?.onClickListener()
        }
        mAnimatorSet.addListener(object :Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                mAimationButtonListener?.animationFinish()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }

        })
    }

    private fun initPaint() {
        mPaint = Paint().apply {
            strokeWidth = 4F
            style = Paint.Style.FILL
            isAntiAlias = true
            color = bg_color
        }

        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 40F
            color = Color.WHITE
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }

        okPaint = Paint().apply {
            strokeWidth = 10F
            style = Paint.Style.STROKE
            isAntiAlias = true
            color = Color.WHITE
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mWidget = w
        mHeight = h
        default_two_circle_distance = (w - h) / 2

        initOk()
        initAnimation()
    }

    private fun initOk() {
        path.moveTo((default_two_circle_distance + mHeight/8 * 3).toFloat(),
            (mHeight /2).toFloat())
        path.lineTo((default_two_circle_distance + mHeight/2).toFloat(),
            (mHeight/5 * 3).toFloat())
        path.lineTo((default_two_circle_distance + mHeight/3 * 2).toFloat(),
            (mHeight/5 *2).toFloat()
        )
        pathMeasure = PathMeasure(path, true)
    }

    private fun initAnimation() {
        set_rect_to_angle_animation()
        set_rect_to_circle_animation()
        set_move_to_up_animation()
        set_draw_ok_animation()

        mAnimatorSet
            .play(animator_move_to_up)
            .before(animator_draw_ok)
            .after(animator_rect_to_square)
            .after(animator_rect_to_angle)
    }

    /**
     * 绘制对勾的动画
     */
    private fun set_draw_ok_animation() {
        animator_draw_ok = ValueAnimator.ofFloat(1F, 0F)
        animator_draw_ok?.duration = duration.toLong()
        animator_draw_ok?.addUpdateListener {
            startDrawOk = true
            var value: Float = it.animatedValue as Float
            effect = DashPathEffect(
                floatArrayOf(pathMeasure!!.length, pathMeasure!!.length),
                value * pathMeasure!!.length
            )
            okPaint!!.pathEffect = effect
            invalidate()
        }
    }

    /**
     *
     */
    private fun set_move_to_up_animation() {
        val curTranslationY = this.translationY
        animator_move_to_up = ObjectAnimator.ofFloat(this, "translationY", curTranslationY , translationY - move_distance)
        animator_move_to_up?.setDuration(duration.toLong())
        animator_move_to_up?.interpolator = AccelerateDecelerateInterpolator()
    }

    /**
     * 矩形变圆形的动画
     */
    private fun set_rect_to_circle_animation() {
        animator_rect_to_square = ValueAnimator.ofInt(0, default_two_circle_distance)
        animator_rect_to_square?.duration = duration.toLong()
        animator_rect_to_square?.addUpdateListener {
            two_circle_distance = it.getAnimatedValue() as Int
            var alpha = 255 - (two_circle_distance * 255) / default_two_circle_distance
            mTextPaint?.alpha = alpha
            invalidate()
        }
    }

    /**
     * 矩形过度变圆角矩形的动画
     */
    private fun set_rect_to_angle_animation() {
        animator_rect_to_angle = ValueAnimator.ofInt(0, mHeight /2)
        animator_rect_to_angle?.duration = duration.toLong()
        animator_rect_to_angle?.addUpdateListener {
            mCircleAngle = it.getAnimatedValue() as Int
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //绘制矩形按钮
        draw_oval_to_circle(canvas)
        darwText(canvas)

        if (startDrawOk) {
            canvas!!.drawPath(path, okPaint!!)
        }
    }

    /**
     * 绘制矩形
     */
    private fun draw_oval_to_circle(canvas: Canvas?) {
        with(mRectf) {
            left = two_circle_distance.toFloat()
            top = 0F
            right = (width - two_circle_distance).toFloat()
            bottom = mHeight.toFloat()
        }
        canvas?.drawRoundRect(mRectf, mCircleAngle.toFloat(), mCircleAngle.toFloat(), mPaint!!)
    }

    /**
     * 绘制文字
     */
    private fun darwText(canvas: Canvas?) {
        with(mTextRect) {
            left = 0
            top = 0
            right = mWidget
            bottom = mHeight
        }
        val fontMetrics = mTextPaint?.fontMetricsInt
        val baseline = (mTextRect.bottom + mTextRect.top - fontMetrics!!.bottom - fontMetrics.top)/2
        canvas?.drawText(
            buttonString,
            mTextRect.centerX().toFloat(),
            baseline.toFloat(),
            mTextPaint!!
        )
    }

    /**
     * 动画启动
     */
    fun start(){
        mAnimatorSet.start()
    }

    /**
     * 动画复原
     */
    fun reset(){
        startDrawOk = false
        mCircleAngle = 0
        two_circle_distance = 0
        default_two_circle_distance = (width - height) / 2
        mTextPaint?.setAlpha(255)
        translationY += move_distance
        invalidate()
    }

    interface AnimationButtonListener {
        /**
         * 按钮点击事件
         */
        fun onClickListener()

        /**
         * 动画完成回调
         */
        fun animationFinish()
    }

}