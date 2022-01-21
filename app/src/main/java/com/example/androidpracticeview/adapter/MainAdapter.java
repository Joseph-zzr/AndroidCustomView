package com.example.androidpracticeview.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.androidpracticeview.R;
import com.example.androidpracticeview.bean.TypeBean;

import java.util.List;

/**
 *
 */

public class MainAdapter extends BaseQuickAdapter<TypeBean, BaseViewHolder> {
    public MainAdapter( @Nullable List<TypeBean> data) {
        super(R.layout.adapter_item_main, data);
    }
    
    @Override
    protected void convert(BaseViewHolder baseViewHolder, TypeBean typeBean) {
        baseViewHolder.setText(R.id.title_tv, typeBean.getTitle());
    }
}
