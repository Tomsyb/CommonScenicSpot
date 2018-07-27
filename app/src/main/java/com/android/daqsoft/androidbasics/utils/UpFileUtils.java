package com.android.daqsoft.androidbasics.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yanbo on 2017/3/22.
 * 文件上传
 */

public class UpFileUtils {

    public static File getFileformpathandsave(String path) {
        File f = null;
        Bitmap bitmap = getBitmapbyFilePath(path);
        f = saveMyBitmaptoFile(bitmap, 1);
        return f;
    }


    public static Bitmap getBitmapbyFilePathgood(String imageFilePath, int dw, int dh) throws FileNotFoundException {
        // 获取屏幕的宽和高
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;// 仅获取图片信息，而不解读图片本身，也就不会占用内存
        // 由于使用了MediaStore存储，这里根据URI获取输入流的形式
        Bitmap pic = BitmapFactory.decodeStream(new FileInputStream(imageFilePath), null, op);
        int wRatio = (int) Math.ceil(op.outWidth / (float) 800); // 计算宽度比例
        int hRatio = (int) Math.ceil(op.outHeight / (float) 600); // 计算高度比例
        // int wRatio = (int) Math.ceil(op.outWidth / (float) dw); // 计算宽度比例
        // int hRatio = (int) Math.ceil(op.outHeight / (float) dh); // 计算高度比例
        if (wRatio > 1 && hRatio > 1) {
            if (wRatio > hRatio) {
                op.inSampleSize = hRatio;
            } else {
                op.inSampleSize = wRatio;
            }
        }
        if (dw == 0 && dh == 0) {
            op.inSampleSize = 1;
        }

//		op.inSampleSize = 2;
        op.inJustDecodeBounds = false; // 注意这里，一定要设置为false，因为上面我们将其设置为true来获取图片尺寸了
        pic = BitmapFactory.decodeStream(new FileInputStream(imageFilePath),null, op);
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
        return pic;
    }

    public static Bitmap getBitmapbyFilePath(String path) {
        try {

            return getBitmapbyFilePathgood(path, 800, 600);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                return getBitmapbyFilePathgood(path, 400, 300);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public static boolean isFilemak(String dir) {
        File file = new File(dir);
        boolean b = true;
        // 如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {

            file.mkdir();
            b = false;
        } else {

            b = true;
        }
        return b;
    }


    public static File saveMyBitmaptoFile(Bitmap bitmap, int issetname) {
        String projectname = "Smart";
        isFilemak(SDCardUtils.getSDCardPath() + File.separator + projectname);
        String filepathname = SDCardUtils.getSDCardPath() + File.separator
                + projectname + File.separator
                + "z_choose_pic_touse_forupload.jpg";
        if (issetname == 1) {
            filepathname = SDCardUtils.getSDCardPath() + File.separator + projectname
                    + File.separator + "TmpF" + System.currentTimeMillis()
                    + ".jpg";
        }
        String bitName = "F" + System.currentTimeMillis();
        File f = new File(filepathname);
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

}
