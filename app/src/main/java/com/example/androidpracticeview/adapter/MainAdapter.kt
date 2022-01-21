package com.example.androidpracticeview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpracticeview.R
import com.example.androidpracticeview.bean.TypeBean

/**
 * <pre>
 *      @author : Joseph
 *      e-mail  : 913870737@qq.com
 *      date    : 2022年1月21日
 *      desc    :
 *      version : 1.0
 * </pre>
 */
class MainAdapter(
    val dataList: List<TypeBean>,
    private val context: Context
    ) : RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder>() {
    class MainAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view.findViewById(R.id.title_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MainAdapterViewHolder{
        return MainAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_item_main, parent, false))
    }

    override fun onBindViewHolder(viewHolder: MainAdapterViewHolder, position: Int) {
        viewHolder.textView.text = dataList[position].toString()
        viewHolder.itemView.setOnClickListener {
            setOnItemClickListener?.invoke(position)
        }
    }

    override fun getItemCount() = dataList.size

    var setOnItemClickListener:((position:Int)->Unit)? = null
}