package com.android.daqsoft.androidbasics.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import com.android.daqsoft.androidbasics.view.CustomDialog;
//import com.qq.wx.voice.synthesizer.SpeechSynthesizerResult;
//import com.qq.wx.voice.synthesizer.TextSenderResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Daqsoft on 2018-4-10.
 */

public class CommonUtils {
    /**
     * 返回美国时间格式 26 Apr 2006
     *
     * @param str
     * @return
     */
    public static String getEDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        return k[2]+ ","+ k[1].toUpperCase() + ","+k[5];
    }
    private static String phoneUtils = "^(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,14}$";
    //机器人说话电话号码处理
    public static String robotsay(String str){
        //StringBuilder sb = new StringBuilder(str);
        boolean b = true;
        String result = "";
        String phone = "";
        for(int i= 0 ;i<str.length();i++){
            if(str.charAt(i) == '0'||
                    str.charAt(i) == '1'||
                    str.charAt(i) == '2'||
                    str.charAt(i) == '3'||
                    str.charAt(i) == '4'||
                    str.charAt(i) == '5'||
                    str.charAt(i) == '6'||
                    str.charAt(i) == '7'||
                    str.charAt(i) == '8'||
                    str.charAt(i) == '9'||
                    str.charAt(i) == '-'){
                b = false;
                phone += str.charAt(i);
                if (i==str.length()-1){
                    if(phone.matches(phoneUtils)||phone.length()>9){
                        for(int j =0 ;j<phone.length();j++){
                            result+=getNum(phone.charAt(j));
                        }
                        phone="";
                    }else{
                        result+=phone;
                        phone = "";
                    }
                }
            }else{
                if(!b){
                    if(phone.matches(phoneUtils)||phone.length()>9){
                        for(int j =0 ;j<phone.length();j++){
                            result+=getNum(phone.charAt(j));
                        }
                        phone="";
                    }else{
                        result+=phone;
                        phone = "";
                    }
                }
                b=true;
                result += str.charAt(i);
            }
        }

        return result;
    }
    public static String getNum(char ch){
        String result = "";
        switch (ch) {
            case '0':
                result = "零";
                break;
            case '1':
                result = "一";
                break;
            case '2':
                result = "二";
                break;
            case '3':
                result = "三";
                break;
            case '4':
                result = "四";
                break;
            case '5':
                result = "五";
                break;
            case '6':
                result = "六";
                break;
            case '7':
                result = "七";
                break;
            case '8':
                result = "八";
                break;
            case '9':
                result = "九";
                break;
            case '-':
                result = "-";
                break;
        }
        return result;
    }
//    public static String txt2Voice(Activity activity, TextSenderResult data) {
//        SpeechSynthesizerResult result = (SpeechSynthesizerResult) data;
//        byte[] speech = result.speech;
//
//        String filepath = null;
//        boolean sdCardExist = Environment.getExternalStorageState()
//                .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
//        if (!sdCardExist) {
//            /**
//             * 写手机内部存储
//             */
//            @SuppressWarnings("deprecation")
//            File mediaFilesDir = activity.getDir("mediaFiles", Context.MODE_WORLD_READABLE);
//            filepath = mediaFilesDir.getPath();
//        } else {
//            filepath = Environment.getExternalStorageDirectory().getPath()
//                    + "/daqsoft/voice";
//            File outputpath = new File(filepath);
//            if (!outputpath.exists()) {
//                outputpath.mkdirs();
//            }
//        }
//        String voiceType = ".mp3";
//        String voiceFileName = filepath + "/txt2voice" + voiceType;
//        File voiceFile = new File(voiceFileName);
//        if (!voiceFile.exists()) {
//            try {
//                voiceFile.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            ;
//        }
//        FileOutputStream voiceOutputStream = null;
//        try {
//            voiceOutputStream = new FileOutputStream(voiceFile);
//        } catch (FileNotFoundException e1) {
//            e1.printStackTrace();
//        }
//        try {
//            voiceOutputStream.write(speech);
//            voiceOutputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return voiceFileName;
//    }
    public static String isNotEmpty(String str){
        if (ObjectUtils.isNotEmpty(str)){
            return str;
        }else {
            return "";
        }
    }

    //======================================过滤html标签
    /**
     * 定义script的正则表达式
     */
    private static final String REGEX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>";
    /**
     * 定义style的正则表达式
     */
    private static final String REGEX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>";
    /**
     * 定义HTML标签的正则表达式
     */
    private static final String REGEX_HTML = "<[^>]+>";
    /**
     * 定义空格回车换行符
     */
    private static final String REGEX_SPACE = "\\s*|\t|\r|\n";
    public static String delHTMLTag(String htmlStr) {
        // 过滤script标签
        Pattern p_script = Pattern.compile(REGEX_SCRIPT, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");
        // 过滤style标签
        Pattern p_style = Pattern.compile(REGEX_STYLE, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");
        // 过滤html标签
        Pattern p_html = Pattern.compile(REGEX_HTML, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
        // 过滤空格回车标签
        Pattern p_space = Pattern.compile(REGEX_SPACE, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll("");
        return htmlStr.trim(); // 返回文本字符串
    }

}
