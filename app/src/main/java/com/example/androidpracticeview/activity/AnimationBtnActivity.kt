package com.example.androidpracticeview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.androidpracticeview.R
import com.example.androidpracticeview.widget.AnimationButton

/**
 * <pre>
 *      @author : Joseph
 *      e-mail  : 913870737@qq.com
 *      date    : 2022年1月22日
 *      desc    : 自带动画的button
 *      version : 1.0
 * </pre>
 */

class AnimationBtnActivity : AppCompatActivity() {

    private var animationButton: AnimationButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_btn)
        animationButton = findViewById(R.id.animation_btn)
        animationButton?.mAimationButtonListener = object : AnimationButton.AnimationButtonListener{
            override fun onClickListener() {
                animationButton!!.start()
            }

            override fun animationFinish() {
                Toast.makeText(this@AnimationBtnActivity,"动画执行完毕",Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun resetClick(view: android.view.View) {
        animationButton!!.reset()
    }
}