package com.android.daqsoft.androidbasics.view.audio;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by huangx on 2016/8/27.
 */
public class RecordMedia {

    private static File mRecAudioPath = null;
    private static MediaRecorder mMediaRecorder = null;
    private static File mRecAudioFile = null;
    private static String strTempFile = "audio";

    public static File init(Context context) {
         /* 检测是否存在SD卡 */
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            mRecAudioPath = Environment.getExternalStorageDirectory();// 得到SD卡得路径
        } else {
            Toast.makeText(context, "没有SD卡", Toast.LENGTH_LONG).show();
        }
        try {
                    /* ①Initial：实例化MediaRecorder对象 */
            mMediaRecorder = new MediaRecorder();
                    /* ②setAudioSource/setVedioSource*/
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置麦克风
                    /* ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default
                     * THREE_GPP(3gp格式，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
                     * */
            //指定音频输出格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            //指定音频编码方式()
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                    /* ②设置输出文件的路径 */
            try {
                mRecAudioFile = File.createTempFile(strTempFile, ".mp3", mRecAudioPath);

            } catch (Exception e) {
                e.printStackTrace();
            }
            mMediaRecorder.setOutputFile(mRecAudioFile.getAbsolutePath());
                    /* ③准备 */
            mMediaRecorder.prepare();
                    /* ④开始 */
            mMediaRecorder.start();
                    /*按钮状态*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mRecAudioFile;
    }

    public static void stop() {
        if (mRecAudioFile != null && null != mMediaRecorder) {
            try {
                    /* ⑤停止录音 */
                mMediaRecorder.stop();
                    /* ⑥释放MediaRecorder */
                mMediaRecorder.release();
                mMediaRecorder = null;
                    /* 按钮状态 */
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
