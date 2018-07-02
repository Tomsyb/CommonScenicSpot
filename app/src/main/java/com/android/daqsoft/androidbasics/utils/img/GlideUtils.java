package com.android.daqsoft.androidbasics.utils.img;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.utils.imgs.ShowImageUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


/**
 * Created by yanbo on 2018-4-3.
 */

public class GlideUtils {

    public static void GlideImg(Context context, String imgPath, ImageView imgview){
        ShowImageUtils.showImageView(context,R.drawable.icon_img_defalt,imgPath,imgview);
    }
    public static void GlideCircleImg(Application context, String imgPath, ImageView imgview){
        ShowImageUtils.showImageViewToCircle(context,R.drawable.icon_img_defalt,imgPath,imgview);
    }


}
