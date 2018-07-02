package com.android.daqsoft.androidbasics.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 作者：蒋荣波
 * 时间：2016/3/10
 */
public class FileHelper {

    private static FileHelper fileHelper;

    public static FileHelper getFileHelper() {
        if (fileHelper == null) {
            fileHelper = new FileHelper();
        }
        return fileHelper;
    }

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }



    public static String saveBitmap(Bitmap bitmap, String path) {
        File f = new File(path);

        File sd = Environment.getExternalStorageDirectory();
        boolean can_write = sd.canWrite();

        try {
            if (!f.exists()) {
                f.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        comp(bitmap, f);

//        compressBmpToFile(bitmap, f, 100);

        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
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
        return path;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /*
     * 旋转图片并压缩
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap, float maxBound) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        float scaleRate = 0;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (height >= width * 3) {
            if (width > 440) {
                scaleRate = 440f / width;
            }
        } else if (width > maxBound || height > maxBound) {
            scaleRate = width > height ? maxBound / width : maxBound / height;
            matrix.postScale(scaleRate, scaleRate);
        }

        Log.i("test", "width " + bitmap.getWidth() + " height " + bitmap.getHeight());
        Log.i("test", "scaleRate " + scaleRate);

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                width, height, matrix, true);

        return resizedBitmap;
    }

    /**
     * 只是旋转图片
     *
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap justRotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                width, height, matrix, true);
        bitmap.recycle();
        LogUtils.e("字节长度"+resizedBitmap.getByteCount());
        return resizedBitmap;
    }

    /**
     * 将字节数组写成一个本地文件
     *
     * @param imageByteArray
     * @param account
     */
    public static void writeByteArrayToImageFile(byte[] imageByteArray, String account) {
        BufferedOutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(account));
            outputStream.write(imageByteArray);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 压缩图片
     */
    public static void compressBmpToFile(Bitmap bmp, File file, int maxSize) {

//        bmp = comp(bmp);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > maxSize) {
            Log.i("test", "baos.toByteArray().length / 1024 " + baos.toByteArray().length / 1024);
            baos.reset();
            options -= 10;
            if (options <= 0) {
                options = 0;
                bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
                break;
            }

            Log.i("test", "options " + options);

            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        try {
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void comp(Bitmap image, File file) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
//            baos.reset();//重置baos即清空baos
//            image.compress(Bitmap.CompressFormat.JPEG, 20, baos);//这里压缩50%，把压缩后的数据存放到baos中
//        }


        float bitmapLength = baos.toByteArray().length;
        Log.i("test", "bitmapLength " + bitmapLength / 1024);
        if (bitmapLength / 1024 > 200) {//判断如果图片大于100kb,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            int qul = (int) ((200 / (bitmapLength / 1024)) * 100);

            Log.i("test", "qul ------------------------- " + qul);

            image.compress(Bitmap.CompressFormat.JPEG, qul, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        Log.i("test", "baos.toByteArray().length " + baos.toByteArray().length / 1024);

        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        try {
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
