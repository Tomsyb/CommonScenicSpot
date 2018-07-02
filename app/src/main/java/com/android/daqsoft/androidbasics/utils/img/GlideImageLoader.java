package com.android.daqsoft.androidbasics.utils.img;

import android.content.Context;
import android.widget.ImageView;
import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.utils.imgs.ShowImageUtils;
import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by yanbo on 2018-4-2.
 */

public class GlideImageLoader extends ImageLoader{
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context.getApplicationContext())
                .load(path)
                .error(R.drawable.icon_img_defalt)// 设置错误图片
                .placeholder(R.drawable.icon_img_defalt)// 设置占位图
                .into(imageView);
        //ShowImageUtils.showImageView(context,R.drawable.icon_img_defalt,(String) path,imageView);
    }
}
