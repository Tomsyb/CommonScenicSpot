package com.android.daqsoft.androidbasics.ui.fragment.index;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.utils.ObjectUtils;
import com.android.daqsoft.androidbasics.utils.ToastUtils;
import com.android.daqsoft.androidbasics.utils.img.GlideUtils;
import com.android.daqsoft.androidbasics.view.ChoicePopupWindow;
import com.android.daqsoft.androidbasics.view.MyGridView;
import com.android.daqsoft.androidbasics.view.audio.RecordMedia;
import com.android.daqsoft.androidbasics.view.audio.RecordTask;
import com.android.daqsoft.androidbasics.view.suppertext.SuperTextView;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionGen;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;



/**
 * 事件上报具体上报
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class IndexPoliceXqFragment extends BaseFragment implements AdapterView.OnItemLongClickListener{
    @BindView(R.id.include_img_back)
    ImageView mImgBack;
    @BindView(R.id.include_tv_title)
    TextView mTvTitle;
    @BindView(R.id.exception_et)
    EditText mReportEt;
    @BindView(R.id.police_xq_img_video)
    ImageView mVideo;
    @BindView(R.id.police_xq_img_audio)
    ImageView mAudio;
    @BindView(R.id.fg_mine_qure)
    SuperTextView mQure;
    @BindView(R.id.police_route)
    LinearLayout mLlRoute;
    @BindView(R.id.police_tv_audio)
    TextView mTvaudio;
    @BindView(R.id.police_xq_img_video2)
    ImageView mImgPicture;
    @BindView(R.id.rl_picture)
    LinearLayout mRlPicture;
    @BindView(R.id.exception_gridView)
    MyGridView exception_gridView;
    @BindView(R.id.myView)
    View myView;

    public PopupWindow videoPopupWindow;//视屏
    public PopupWindow audioPopupWindow;//音频
    private boolean videoState = false, audioState = false;

    private RecordTask recordTask;
    //true 说明这个timer以daemon方式运行（优先级低，程序结束timer也自动结束）
    private Timer timer = new Timer(true);
    public PopupWindow audioRecordPopupWindow;
    public AnimationDrawable animationDrawable = null;
    private int audioFirst = 0;//判断是否是第一次点击录制音频
    private File audioFile = null;
    private TestTimerTask task;
    private int i = 0;
    public TextView tvAudioAnim = null;
    private ChoicePopupWindow popupWindow;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                i++;
                if (i < 120) {
                    tvAudioAnim.setText("时长: " + i + "″");

                } else {
                    if (audioRecordPopupWindow.isShowing()) {
                        animationDrawable.stop();
                        audioRecordPopupWindow.dismiss();
                    }
                    recordTask.setRecording(false);
                    mTvaudio.setText("播放");
                    audioState = true;
                    ToastUtils.showToast("录制音频最大时长2分钟");
                }
            }
        }
    };

    //单列
    public static IndexPoliceXqFragment newInstance() {
        Bundle args = new Bundle();
        IndexPoliceXqFragment fragment = new IndexPoliceXqFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        initPresenon();
        initView();
        initGridView(imgList);
    }

    private void initPresenon() {
        PermissionGen.with(_mActivity)
                .addRequestCode(100)
                .permissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();
    }

    private void initView() {
        mTvTitle.setText("一键报警");
        exception_gridView.setOnItemLongClickListener(this);
        exception_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, String> clickMap = allListPic.get(i);
                if (clickMap.get("itemImage").equals(defult)) {
                    popupWindow.showAtLocation(exception_gridView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    myView.setVisibility(View.VISIBLE);
                }
            }
        });
        popupWindow = new ChoicePopupWindow(_mActivity, firstListener, secondListener);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                myView.setVisibility(View.GONE);
            }
        });
    }



    @Override
    public int getLayoutResId() {
        return R.layout.fg_index_police_xq;
    }


    //--
    public class TestTimerTask extends TimerTask {

        public void run() {
            //每次需要执行的代码放到这里面。
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    }
    /**
     * 拍照
     */
    private View.OnClickListener firstListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showCamera();
        }
    };
    /**
     * 从手机相册中选择的监听事件
     */
    private View.OnClickListener secondListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            choicePicture(5 - allListPic.size(), MODE_MULTI);
        }
    };
    @OnClick({R.id.include_img_back, R.id.police_xq_img_video, R.id.police_xq_img_audio, R.id.fg_mine_qure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.include_img_back:
                _mActivity.onBackPressed();
                if (ObjectUtils.isNotEmpty(audioRecordPopupWindow)&&audioRecordPopupWindow.isShowing()) {
                    animationDrawable.stop();
                    audioRecordPopupWindow.dismiss();
                }
                RecordMedia.stop();
                break;
            case R.id.police_xq_img_video:
                ToastUtils.showToast("待开发...");
                break;
            case R.id.police_xq_img_audio://点击录音
                if (!audioState){
                    try {
                        if (audioFirst == 0) {//第一次点击
                            mTvaudio.setText("暂停");
                            audioFirst++;
                            timer = new Timer();
                            task = new TestTimerTask();
                            timer.schedule(task, 1000, 1000);
                            showAudioRecordPopupWindow(mAudio);
                            audioFile = RecordMedia.init(getActivity());
                        } else {//第二次点击
                            audioFirst = 0;
                            if (ObjectUtils.isNotEmpty(audioRecordPopupWindow)) {
                                if (audioRecordPopupWindow.isShowing()) {
                                    animationDrawable.stop();
                                    audioRecordPopupWindow.dismiss();
                                }
                            }
                            //recordTask.setRecording(false);
                            RecordMedia.stop();
                            mTvaudio.setText("预览");
                            audioState = true;
                            if (timer != null) {
                                timer.cancel();
                                timer = null;
                            }
                            i = 0;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    showAudioPopupWindow(mAudio);
                }
                break;
            case R.id.fg_mine_qure://点击上报
                ToastUtils.showToast("上报成功");
                break;
        }
    }
    /**
     * 跳转到系统相机
     */
    private File mTmpFile;
    private void showCamera() {
        // 跳转到系统照相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(_mActivity.getPackageManager()) != null) {
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            mTmpFile = new File(Environment.getExternalStorageDirectory() + "/DaQI/Img", "temp" + System.currentTimeMillis() + ".jpg");
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
            startActivityForResult(cameraIntent, Constant.REQUEST_CAMERA);
        } else {
            ToastUtils.showToast("没找到相机");
        }
        popupWindow.dismiss();
    }
    /**
     * 从手机相册中选择
     */
    private ArrayList<HashMap<String, String>> allListPic = new ArrayList<>();
    public static final int MODE_MULTI = 1;
    private void choicePicture(int num, int model) {
        popupWindow.dismiss();
        Intent intent = new Intent(_mActivity, MultiImageSelectorActivity.class);
        // 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
        // 最大图片选择数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, num);
        // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, model);
        // 默认选择
      /*  if (imgList != null && imgList.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, imgList);
        }*/
        startActivityForResult(intent, Constant.REQUEST_IMAGE);

    }

    //-----------------------------------------------------------------------------------下面是弹窗
    /**
     * 显示录音动画
     * @param v
     */
    public void showAudioRecordPopupWindow(View v) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_audio_anim, null);
        tvAudioAnim = (TextView) view.findViewById(R.id.tv_audio_anim);
        ImageView ivAudioAnim = (ImageView) view.findViewById(R.id.iv_audio_anim);
        ivAudioAnim.setBackgroundResource(R.drawable.audio_anim_list);
        animationDrawable = (AnimationDrawable) ivAudioAnim.getBackground();
        audioRecordPopupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        audioRecordPopupWindow.setFocusable(false);

        audioRecordPopupWindow.setTouchable(false);
        audioRecordPopupWindow.setOutsideTouchable(false);

        audioRecordPopupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.white));
        // 设置好参数之后再show
        audioRecordPopupWindow.showAtLocation(view, Gravity.CENTER, 0, -50);
        animationDrawable.start();
    }

    /**
     * 音频操作弹窗
     * @param v
     */
    public void showAudioPopupWindow(View v) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_audio_click, null);
        Button btnPlay = (Button) view.findViewById(R.id.btn_play_audio);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (audioPopupWindow.isShowing()) {
                    audioPopupWindow.dismiss();
                }
                if (ObjectUtils.isNotEmpty(audioFile) && audioFile.exists()) {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer
                                .setDataSource(audioFile.getPath());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        ToastUtils.showToast("该音频已损坏，无法播放");
                    }

                    //调用系统
//					Intent intent = new Intent();
//					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					intent.setAction(Intent.ACTION_VIEW);
//        			/* 设置文件类型 */
//					intent.setDataAndType(Uri.fromFile(audioFile), "audio");
//					startActivity(intent);
                    audioPopupWindow.dismiss();
                } else {
                    ToastUtils.showToast("音频播放异常，请稍后重试");
                    audioPopupWindow.dismiss();
                }
            }
        });
        Button btnDelete = (Button) view.findViewById(R.id.btn_delete_audio);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ObjectUtils.isNotEmpty(audioFile) && audioFile.exists()) {
                    if (audioFile.exists()) {
                        audioFile.delete();
                    }
                }
                audioFirst = 0;
                audioState = false;
                mTvaudio.setText("录音");
                if (audioPopupWindow.isShowing()) {
                    audioPopupWindow.dismiss();
                }
            }
        });
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel_audio);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (audioPopupWindow.isShowing()) {
                    audioPopupWindow.dismiss();
                }
            }
        });
        audioPopupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        audioPopupWindow.setFocusable(true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        audioPopupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.white));
        // 设置好参数之后再show
        audioPopupWindow.showAtLocation(view, Gravity.BOTTOM, 10, 10);
    }

    /**
     * 图片选择
     * @param
     */
    public void showPictureSelectWindow() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_picture_selected, null);
        //拍照哦
        Button photo = (Button) view.findViewById(R.id.btn_photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (audioPopupWindow.isShowing()) {
                    audioPopupWindow.dismiss();
                }
                showCamera();
            }
        });
        //相册选择
        Button photoimg = (Button) view.findViewById(R.id.btn_img);
        photoimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (audioPopupWindow.isShowing()) {
                    audioPopupWindow.dismiss();
                }
                choicePicture(5 - allListPic.size(), MODE_MULTI);
            }
        });
        Button btnCancel = (Button) view.findViewById(R.id.btn_picture_cancle);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (audioPopupWindow.isShowing()) {
                    audioPopupWindow.dismiss();
                }
            }
        });
        audioPopupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        audioPopupWindow.setFocusable(true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        audioPopupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.white));
        // 设置好参数之后再show
        audioPopupWindow.showAtLocation(view, Gravity.BOTTOM, 10, 10);
    }

    private ArrayList<String> imgList = new ArrayList<>();
    private String location = "";
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                LogUtils.e("调用相机返回" + mTmpFile.getAbsolutePath());
                imgList.clear();
                imgList.add(mTmpFile.getAbsolutePath());
                initGridView(imgList);
            }
        } else if (requestCode == Constant.REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                LogUtils.e("直接选择图片返回" + data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT));
                initGridView(data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT));
            }
        }else if (requestCode == Constant.REQUEST_LOCATION){
            if (resultCode == RESULT_OK) {
                location = data.getStringExtra("location");
                //event_place_value.setText(location);
            }
        }
    }

    /**
     * 初始化gridView
     */
    private SimpleAdapter simpleAdapter;
    private String defult = "drawable//" + R.mipmap.defult_add;
    private MyGridView emergency_grid_event;
    private void initGridView(ArrayList<String> imgList) {
        if (imgList.size() == 0) {
            HashMap<String, String> addBtnMap = new HashMap<>();
            addBtnMap.put("itemImage", defult);
            allListPic.add(addBtnMap);
        } else {
            Iterator<HashMap<String, String>> result = allListPic.iterator();
            while (result.hasNext()) {
                Map<String, String> list = result.next();
                if (list.get("itemImage").equals(defult)) {
                    result.remove();
                }
            }
            for (int i = 0; i < imgList.size(); i++) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("itemImage", imgList.get(i));
                allListPic.add(hashMap);
            }
            this.imgList = imgList;
            if (allListPic.size() < 4) {
                HashMap<String, String> addBtnMap = new HashMap<>();
                addBtnMap.put("itemImage", defult);
                allListPic.add(addBtnMap);
            }
        }
        setSimpleAdapter(allListPic);
    }
    private void setSimpleAdapter(ArrayList<HashMap<String, String>> allListPic) {
        if (null == simpleAdapter) {
            simpleAdapter = new SimpleAdapter(_mActivity,
                    allListPic, R.layout.rounded_imageview_layout, new String[]{"itemImage"}, new int[]{R.id.rounded_item_image});
        } else {
            simpleAdapter.notifyDataSetChanged();
        }
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                // TODO Auto-generated method stub
                if (view instanceof RoundedImageView && data instanceof String) {
                    RoundedImageView imageView = (RoundedImageView) view;
                    String str = (String) data;
                    if (str.equals(defult)) {
                        imageView.setImageResource(R.mipmap.defult_add);
                    } else {
                        String result = "file://" + str;
                        GlideUtils.GlideImg(_mActivity,result,imageView);
                    }
                    return true;
                }
                return false;
            }

        });
        exception_gridView.setAdapter(simpleAdapter);

    }
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        HashMap<String, String> clickMap = allListPic.get(i);
        if (!clickMap.get("itemImage").equals(defult)) {
            if (allListPic.size() >= 4) {
                if (!allListPic.get(3).get("itemImage").equals(defult)) {
                    HashMap<String, String> addBtnMap = new HashMap<>();
                    addBtnMap.put("itemImage", defult);
                    allListPic.add(addBtnMap);
                }
            }
            allListPic.remove(i);
            setSimpleAdapter(allListPic);
        }
        return false;
    }
}
