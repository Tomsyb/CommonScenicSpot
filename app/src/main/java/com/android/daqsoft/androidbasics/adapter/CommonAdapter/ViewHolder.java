package com.android.daqsoft.androidbasics.adapter.CommonAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.android.daqsoft.androidbasics.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

/**
 * 公用的ViewHolder
 * Created by yulh on 2016/6/6.
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Context mContext;
    private int mLayoutId;

    private RequestManager requestManager;

    public ViewHolder(Context context, ViewGroup parent, int layoutId,
                      int position) {
        mContext = context;
        mLayoutId = layoutId;
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        mConvertView.setTag(this);

        requestManager = Glide.with(mContext);
    }

    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    public int getPosition() {
        return mPosition;
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }
    public ViewHolder setPaddingLeft(int viewId,int left) {
        TextView tv = getView(viewId);
        tv.setPadding(left, 0, 0, 0);
        return this;
    }

    public ViewHolder setHtmlText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(Html.fromHtml(text));
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    /**
     * @param viewId
     * @param child
     * @return
     */
    public ViewHolder setDisplay(int viewId, int child) {
        ViewAnimator view = getView(viewId);
        view.setDisplayedChild(child);
        return this;
    }

    /**
     * 为ImageView设置图片
     * 加载网络图片
     *
     * @param viewId 控件ID
     * @param url    加载的图片地址
     * @param round  圆角
     * @return
     */
    public ViewHolder setImageByUrl(int viewId, String url, int round) {
        round = 0;
        requestManager.load(url).placeholder(R.drawable.icon_img_defalt).transform(
                new GlideRoundTransform(mContext, round)).error(R.drawable.icon_img_defalt).into((ImageView) getView(viewId));
        return this;
    }

    public ViewHolder setImageByUrl(int viewId, String url, int round,int type) {
        round = 0;
        requestManager.load(url).placeholder(R.drawable.icon_img_defalt).transform(
                new GlideRoundTransform(mContext, round)).error(R.drawable.icon_img_defalt).into((ImageView) getView(viewId));
        return this;
    }

    public ViewHolder setImageByUrl(int viewId, int url, int round) {
        requestManager.load(url).placeholder(R.drawable.icon_img_defalt).transform(
                new GlideRoundTransform(mContext, round)).error(R.drawable.icon_img_defalt).into((ImageView) getView(viewId));
        return this;
    }

    /**
     * 设置控件大小
     * @param viewId
     * @param height
     * @return
     */
    public ViewHolder setLayoutparam(int viewId, int height) {
        ImageView imageView = (ImageView) getView(viewId);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height);
        imageView.setLayoutParams(params);
        return this;
    }

    /**
     * 为ImageView设置圆形图片
     *
     * @param viewId 控件ID
     * @param url    加载的图片地址
     * @return
     */
    public ViewHolder setCircleImage(int viewId, String url) {
        requestManager.load(url).placeholder(R.mipmap.mine_head_default).transform(
                new GlideCircleTransform(mContext)).error(R.mipmap.mine_head_default).into((ImageView) getView(viewId));
        return this;
    }

    public ViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public ViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public ViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    @SuppressLint("NewApi")
    public ViewHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public ViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }
    public ViewHolder setGone(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.GONE : View.VISIBLE);
        return this;
    }
    public ViewHolder setVisibleOrInvisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    public ViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public ViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

//    public ViewHolder setStarBar(int viewId, float rating,Context context){
//        StarLinearLayout view = getView(viewId);
//        StarLayoutParams params = new StarLayoutParams();
//        params.setNormalStar(context.getResources().getDrawable(R.mipmap.ratingbar_nor))
//                .setSelectedStar(context.getResources().getDrawable(R.mipmap.ratingbar))
//                .setSelectable(false)
//                .setSelectedStarNum((int)rating)
//                .setTotalStarNum(5)
//                .setStarHorizontalSpace(Utils.dip2px(context,2));
//        view.setStarParams(params);
//        return this;
//    }

    public ViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public ViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public ViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public ViewHolder setCheckedTextview(int viewId, boolean checked) {
        View view = getView(viewId);
        view.setSelected(true);
        return this;
    }
    /**
     * 关于事件的
     */
    public ViewHolder setOnClickListener(int viewId,
                                         View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setOnTouchListener(int viewId,
                                         View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public ViewHolder setOnLongClickListener(int viewId,
                                             View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

}
