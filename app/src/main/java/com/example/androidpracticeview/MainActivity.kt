package com.example.androidpracticeview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidpracticeview.activity.BubbleViewActivity
import com.example.androidpracticeview.activity.WaveByBezierActivity
import com.example.androidpracticeview.adapter.MainAdapter

import com.example.androidpracticeview.bean.TypeBean
import com.example.androidpracticeview.widget.SuperDividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

//                    _ooOoo_
//                   o8888888o
//                   88" . "88
//                   (| -_- |)
//                    O\ = /O
//                ____/`---'\____
//              .   ' \\| |// `.
//               / \\||| : |||// \
//             / _||||| -:- |||||- \
//               | | \\\ - /// | |
//             | \_| ''\---/'' | |
//              \ .-\__ `-` ___/-. /
//           ___`. .' /--.--\ `. . __
//        ."" '< `.___\_<|>_/___.' >'"".
//       | | : `- \`.;`\ _ /`;.`/ - ` : | |
//         \ \ `-. \_ __\ /__ _/ .-` / /
// ======`-.____`-.___\_____/___.-`____.-'======
//                    `=---='
// .............................................
//          佛祖保佑             永无BUG

class MainActivity() : AppCompatActivity() {

    private var adapter: MainAdapter? = null

    private val typeBeans = ArrayList<TypeBean>()

    private val data: List<TypeBean>
        get() {
            typeBeans.add(TypeBean("气泡漂浮动画", 0))
            typeBeans.add(TypeBean("波浪动画--贝塞尔曲线实现", 1))
            return typeBeans
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MainAdapter(data, this);
        adapter?.setOnItemClickListener = itemClickListener
        recycler_view.layoutManager = LinearLayoutManager(this);
        recycler_view.addItemDecoration(
            SuperDividerItemDecoration.Builder(this)
            .build())
        recycler_view.adapter = adapter
    }

    var itemClickListener = fun(position: Int){
        when (typeBeans[position].type) {
            0 -> startActivity(Intent(this@MainActivity, BubbleViewActivity::class.java))
            1 -> startActivity(Intent(this@MainActivity, WaveByBezierActivity::class.java))
        }
    }



}