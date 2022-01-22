package com.example.androidpracticeview.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidpracticeview.R
import com.example.androidpracticeview.widget.WaveViewByBezier

/**
 * <pre>
 *      @author : Joseph
 *      e-mail  : 913870737@qq.com
 *      date    : 2022年1月21日
 *      desc    : 波浪动画--贝塞尔曲线实现
 *      version : 1.0
 * </pre>
 */
class WaveByBezierActivity : AppCompatActivity() {

    var waveViewByBezier: WaveViewByBezier? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wave_by_bezier)
        waveViewByBezier = findViewById(R.id.wave_bezier) as WaveViewByBezier

        waveViewByBezier?.startAnimation()
    }

    override fun onPause() {
        super.onPause()
        waveViewByBezier!!.pauseAnimation()
    }

    override fun onResume() {
        super.onResume()
        waveViewByBezier!!.resumeAnimation()
    }


    override fun onDestroy() {
        super.onDestroy()
        waveViewByBezier!!.stopAnimation()
    }
}