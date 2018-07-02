package com.android.daqsoft.androidbasics.ui.fragment.index.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.ui.fragment.index.bean.IndexVPBean;
import com.bumptech.glide.Glide;


/**
 *
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class IndexPagerWindow extends RelativeLayout {
    private ImageView mImg;
    private TextView mTvTitle,mTvContent;
    private Context context;

    public IndexPagerWindow(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public IndexPagerWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    public IndexPagerWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    public IndexPagerWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initView(context);
    }



    private void initView(Context context) {
        View.inflate(context, R.layout.index_viewpager_window,this);
        mImg = (ImageView) findViewById(R.id.vp_img);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvContent = (TextView) findViewById(R.id.tv_content);
    }

    /**
     * 设置内容
     * @param bean
     */
    public void setModel(IndexVPBean bean,OnClickListener listener){
        mTvTitle.setText(bean.getTitle());
        mTvContent.setText(bean.getContent());
        Glide.with(context.getApplicationContext())
                .load(bean.getImg())
                .into(mImg);
    }
}
