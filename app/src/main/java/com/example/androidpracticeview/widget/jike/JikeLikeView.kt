package com.example.androidpracticeview.widget.jike

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.example.androidpracticeview.R
import kotlin.math.sign

/**
 * <pre>
 *      @author : Joseph
 *      e-mail  : 913870737@qq.com
 *      date    : 2022年1月29日
 *      desc    : 即可点赞效果
 *      version : 1.0
 * </pre>
 */
class JikeLikeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :View(context, attrs, defStyleAttr) {



    private var likeNumber: Int = 0;
    private var afterClickTheLikeNumber: Int = -1

    private var numberTextPaint: Paint? = null
    private var bitmapPaint: Paint? = null

    private val numberTextColor = Color.GRAY
    private var textSize: Int = 0

    private var bitToeBitmap: Bitmap? = null
    private var shineBitmap: Bitmap? = null

    var handScale: Float = 1f
    var shiningAlpha: Float = 0f
    var shiningScale: Float = 1f

    private var bitTopeRectf: RectF? = null
    private var holoeRectf: RectF? = null

    /**
     * 初始化标签，二次点击标签
     */
    private var isInitView: Boolean = true
    private var isLike: Boolean = false



    init {
        textSize = dp2px(14f).toInt()
        initAttr(context, attrs, defStyleAttr)
        initBitmap()
        initPaint()
    }

    private fun initBitmap() {
        bitToeBitmap = if (isLike)
            (resources.getDrawable(R.mipmap.ic_message_jike_like) as BitmapDrawable).bitmap
        else
            (resources.getDrawable(R.mipmap.ic_message_jike_unlike) as BitmapDrawable).bitmap

        shineBitmap = (resources.getDrawable(R.mipmap.ic_message_jike_like_shining) as BitmapDrawable).bitmap
    }

    private fun initPaint() {
        numberTextPaint = getTextPaint(numberTextColor, textSize.toFloat())
        bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    private fun initAttr(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.JikeLikeView)

        likeNumber = typedArray.getInt(R.styleable.JikeLikeView_likeNumber, 0)

        typedArray.recycle()
    }

    /**
     *  整体view的大小
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val viewHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
            bitToeBitmap!!.height + dp2px(20F).toInt(),
            MeasureSpec.EXACTLY
        )
        val likeNumberString = likeNumber.toString()
        val textWidth: Float? = numberTextPaint?.measureText(likeNumberString, 0, likeNumberString.length)

        val viewWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
            bitToeBitmap!!.width + textWidth!!.toInt() + dp2px(30F).toInt(),
            MeasureSpec.EXACTLY
        )
        super.onMeasure(viewWidthMeasureSpec, viewHeightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawBigTope(canvas)
        drawShine(canvas!!)
//        drawText(canvas)
    }

    private fun drawShine(canvas: Canvas) {
        bitmapPaint?.alpha = (255 * shiningAlpha).toInt()
        val bitToeBitmapHeight = bitToeBitmap!!.height
        val handTop: Int = (height - bitToeBitmapHeight) / 2
        val shiningTop: Float = handTop - shineBitmap!!.height + dp2px(17F)
        if (isLike){
            canvas.run {
                save()
                scale(shiningScale, shiningScale, (bitToeBitmapHeight / 2).toFloat(), handTop.toFloat());
                canvas.drawBitmap(shineBitmap!!, dp2px(15F), shiningTop, bitmapPaint);
                restore()
            }
            bitmapPaint?.alpha = 255
        } else {
            canvas.run {
                save()
                bitmapPaint!!.alpha = 0
                canvas.drawBitmap(shineBitmap!!, dp2px(15F), shiningTop, bitmapPaint);
                restore()
            }
            bitmapPaint?.alpha = 255
        }

    }

    private fun drawText(canvas: Canvas?) {

        val textNumberString: String = likeNumber.toString()

        if (isLike) {//第一次点赞动画

        } else {
            //初始化
            if (isInitView) {
                isInitView = false
                return

            }
            //点赞回退

        }
        //1.分别绘制文字
        //2.点赞动画，文字上移/或者下移
    }


    /**
     * 画大拇指
     */
    private fun drawBigTope(canvas: Canvas?) {
        val bitToeBitmapHeight = bitToeBitmap!!.height
        val bitToeBitmapWidth = bitToeBitmap!!.width

        val handTop: Int = (height - bitToeBitmapHeight) / 2
        canvas?.run {
            save()
            scale(
                handScale, handScale,
                (bitToeBitmapWidth / 2).toFloat(), (height / 2).toFloat()
            )
            drawBitmap(
                bitToeBitmap!!,
                dp2px(10F).toFloat(),
                handTop.toFloat(),
                bitmapPaint
            )
            restore()
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
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

    fun dp2px(dpVal: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal, resources.displayMetrics)
    }

}