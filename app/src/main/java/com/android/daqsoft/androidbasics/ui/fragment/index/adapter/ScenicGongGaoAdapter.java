package com.android.daqsoft.androidbasics.ui.fragment.index.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.ui.fragment.index.bean.ScenicGongGaoBean;
import com.android.daqsoft.androidbasics.utils.img.GlideUtils;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;

import java.util.List;
/**
 *景区公告适配器
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class ScenicGongGaoAdapter extends BaseRecyclerAdapter<ScenicGongGaoAdapter.mViewHolder>{
    private List<ScenicGongGaoBean.DatasBean> mDatas;
    private Context mContext;
    private  LayoutInflater inflater;

    /***
     * 构造
     * @param mDatas
     */
    public ScenicGongGaoAdapter(List<ScenicGongGaoBean.DatasBean> mDatas,Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;

    }

    @Override
    public int getAdapterItemViewType(int position) {
        return 0;
    }
    @Override
    public mViewHolder getViewHolder(View view) {
        return new mViewHolder(view,false);
    }
    //设置数据
    public void setDatas(List<ScenicGongGaoBean.DatasBean> mDatas_){
        this.mDatas = mDatas_;
        notifyDataSetChanged();
    }

    /**
     *
     * @param mDatas_ 加载尾部数据
     */
    public void addDatas(List<ScenicGongGaoBean.DatasBean> mDatas_){
        this.mDatas.addAll(mDatas_);
        notifyDataSetChanged();
    }
    public void clear() {
        clear(mDatas);
    }

    public void remove(int position) {
        remove(mDatas, position);
    }
    public void insert(ScenicGongGaoBean.DatasBean person, int position) {
        insert(mDatas, person, position);
    }

    public ScenicGongGaoBean.DatasBean getItem(int position) {
        if (position < mDatas.size())
            return mDatas.get(position);
        else
            return null;
    }


    /**
     * 创建
     * @param parent
     * @param viewType
     * @param isItem   如果是true，才需要做处理 ,但是这个值总是true
     * @return
     */
    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View v = inflater.inflate(R.layout.item_common_lookmsg,parent,false);
        mViewHolder viewHolder = new mViewHolder(v, true);//只找一次控件
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position, boolean isItem) {
        ScenicGongGaoBean.DatasBean bean = mDatas.get(position);
        GlideUtils.GlideImg(mContext,bean.getCover(),holder.mImg);
        holder.mTvtop.setText(bean.getTitle());
        holder.mTvContent.setText(bean.getSummary());
        holder.mTvTime.setText(bean.getCreateDate());
        holder.mTvPeople.setText(bean.getViewCount()+"");
    }

    @Override
    public int getAdapterItemCount() {
        return mDatas.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImg;
        public TextView mTvtop;
        public TextView mTvContent;
        public TextView mTvTime;
        public TextView mTvPeople;//浏览人数

        public mViewHolder(View itemView,boolean isItem) {
            super(itemView);
            if (isItem){
                mImg = (ImageView) itemView.findViewById(R.id.item_scenic_img);
                mTvtop = (TextView) itemView.findViewById(R.id.gonggao_tv_title);
                mTvContent = (TextView) itemView.findViewById(R.id.gonggao_tv_content);
                mTvTime = (TextView) itemView.findViewById(R.id.gonggao_tv_time);
                mTvPeople = (TextView) itemView.findViewById(R.id.gonggao_tv_people);
            }
        }
    }

}
