package com.android.daqsoft.androidbasics.ui.fragment.index;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.ui.fragment.index.adapter.ImageUtils;
import com.android.daqsoft.androidbasics.ui.fragment.index.adapter.UploadImageAdapter;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.utils.ObjectUtils;
import com.android.daqsoft.androidbasics.utils.ToastUtils;
import com.android.daqsoft.androidbasics.utils.UpFileUtils;
import com.android.daqsoft.androidbasics.utils.Utils;
import com.android.daqsoft.androidbasics.utils.img.GlideUtils;
import com.android.daqsoft.androidbasics.view.ChoicePopupWindow;
import com.android.daqsoft.androidbasics.view.MyGridView;
import com.android.daqsoft.androidbasics.view.audio.RecordMedia;
import com.android.daqsoft.androidbasics.view.audio.RecordTask;
import com.android.daqsoft.androidbasics.view.suppertext.SuperTextView;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.qqtheme.framework.picker.DatePicker;
import kr.co.namee.permissiongen.PermissionGen;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;


/**
 * 事件上报具体上报
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-4-28.
 * @since JDK 1.8
 */
public class IndexPoliceXqFragment extends BaseFragment{
    @BindView(R.id.include_img_back)
    ImageView mImgBack;
    @BindView(R.id.include_tv_title)
    TextView mTvTitle;
    @BindView(R.id.grid_upload_pictures)
    GridView uploadGridView;
    @BindView(R.id.btn_typeshijian)
    Button mBtnshijian;
    @BindView(R.id.collect_time)
    TextView mTvTime;
    private String mTime = "";
    /**
     * 选择图片的返回码
     */
    private static final int REQUEST_IMAGE = 2;
    /**
     * 选择图片的返回码
     */
    public final static int SELECT_IMAGE_RESULT_CODE = 200;
    /**
     * 图片上传Adapter
     */
    private UploadImageAdapter adapter;
    /**
     * 选择的集合
     */
    private ArrayList<String> mSelectPath;
    //单列
    public static IndexPoliceXqFragment newInstance() {
        Bundle args = new Bundle();
        IndexPoliceXqFragment fragment = new IndexPoliceXqFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        initView();
    }

    /**
     * 图片字符列表(本地)
     */
    private LinkedList<String> imgList = new LinkedList<String>();
    /**
     * 需要上传的图片路径  控制默认图片在最后面需要用LinkedList
     */
    private LinkedList<String> dataList = new LinkedList<String>();
    private void initView() {
        mTvTitle.setText("一键报警");
        // 图片上传初始化第一个添加按钮数据
        dataList.addLast(null);
        adapter = new UploadImageAdapter(getActivity(), dataList);
        uploadGridView.setAdapter(adapter);
        uploadGridView.setOnItemClickListener(mItemClick);
        uploadGridView.setOnItemLongClickListener(mItemLongClick);
    }

    /**
     * 上传图片GridView Item长按监听
     */
    private AdapterView.OnItemLongClickListener mItemLongClick = new AdapterView
            .OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            // 长按删除
            if (parent.getItemAtPosition(position) != null) {
                imgList.remove(parent.getItemAtPosition(position));
                dataList.remove(parent.getItemAtPosition(position));
                // 刷新图片
                adapter.update(dataList);
            }
            return true;
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.fg_index_police_xq;
    }

    /**
     * 当前选择的图片的路径
     */
    private String mImagePath;

    private String[] CoolectARR = {"主机故障", "传感器故障", "电源故障", "测试场地线路故障", "内置程序出错", "其他"};
    @OnClick({R.id.include_img_back, R.id.fg_mine_qure,R.id.btn_typeshijian,R.id.collect_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.collect_time:
                DatePicker picker = new DatePicker(getActivity(), DatePicker.YEAR_MONTH_DAY);
                picker.setGravity(Gravity.BOTTOM);
                picker.setWidth((int) (picker.getScreenWidthPixels()));
                picker.setRangeStart(2016, 10, 14);
                picker.setRangeEnd(2020, 10, 10);
                picker.setSelectedItem(2019, 1,29);
                picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        mTime = year + "-" + month+"-"+day;
                        mTvTime.setText(mTime);
                    }

                });
                picker.show();
                break;
            case R.id.include_img_back:
                _mActivity.onBackPressed();
                break;
            case R.id.fg_mine_qure://点击上报
                upData();
                break;
            case R.id.btn_typeshijian:
                Utils.showPicker(getActivity(), CoolectARR, new Utils.onBackListener() {

                    @Override
                    public void getItem(int pos, String item) {
                        mBtnshijian.setText(item);
                    }
                });
                break;
        }
    }

    private void upData() {
        File file = UpFileUtils.getFileformpathandsave(dataList.get(0));
        OkHttpUtils.post()
                .url(Constant.BASE_URL+"imec/uploadBreakdownEvent")
                .addParams("stationID","34")
                .addParams("deviceID","34")
                .addParams("submitperson","张三")
                .addParams("submittime",mTime)
                .addParams("eventstatus","状态好")
                .addParams("eventtype","类型")
                .addParams("eventdesc","描述")
                .addParams("eventIsCheck","0")
                .addFile("file","yan01.png",file)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showToast("上报失败!");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ToastUtils.showToast("上报成功");
                    }
                });

    }

    /**
     * 上传图片GridView Item单击监听
     */
    private AdapterView.OnItemClickListener mItemClick = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // 添加图片
            if (parent.getItemAtPosition(position) == null) {
                // showPictureDailog();//Dialog形式
                ImageUtils.showPicker(getActivity(),
                        ImageUtils.photoArr, new ImageUtils.onBackListener() {

                            @Override
                            public void getItem(int pos, String item) {
                                if (pos == 0) {
                                    takePhoto();
                                } else {
                                    pickImage();
                                }
                            }
                        });
            } else {
            }
        }
    };
    /**
     * 拍照获取图片
     */
    private void takePhoto() {
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            /**
             * 通过指定图片存储路径，解决部分机型onActivityResult回调 data返回为null的情况
             */
            // 获取与应用相关联的路径
            String imageFilePath = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .getAbsolutePath();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
            // 根据当前时间生成图片的名称
            String timestamp = "/" + formatter.format(new Date()) + ".jpg";
            // 通过路径创建保存文件
            File imageFile = new File(imageFilePath, timestamp);
            mImagePath = imageFile.getAbsolutePath();
            // 获取文件的Uri
            Uri imageFileUri = Uri.fromFile(imageFile);
            // 告诉相机拍摄完毕输出图片到指定的Uri
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
            startActivityForResult(intent, SELECT_IMAGE_RESULT_CODE);
        } else {
            Toast.makeText(getActivity(), "内存卡不存在!", Toast.LENGTH_LONG).show();
        }
    }
    /**
     * 选择图片的返回码
     */
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    /**
     * 仿微信图片选择
     */
    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission
                .READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            if (Utils.isnotNull(imgList)) {
                int maxNum = 5 - imgList.size();
                MultiImageSelector selector = MultiImageSelector.create(getActivity());
                // 是否要相机
                selector.showCamera(false);
                // 图片选择最大数量
                selector.count(maxNum);
                selector.multi();
                selector.origin(mSelectPath);
                selector.start(getActivity(), REQUEST_IMAGE);
            } else {
                int maxNum = 5;
                MultiImageSelector selector = MultiImageSelector.create(getActivity());
                // 是否要相机
                selector.showCamera(false);
                // 图片选择最大数量
                selector.count(maxNum);
                selector.multi();
                selector.origin(mSelectPath);
                selector.start(getActivity(), REQUEST_IMAGE);
            }
        }
    }
    /**
     * 权限
     *
     * @param permission  权限
     * @param rationale   编码
     * @param requestCode 返回编码
     */
    private void requestPermission(final String permission, String rationale, final int
            requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface
                            .OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), new
                                            String[]{permission},
                                    requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
        }
    }

    /**
     * 数据
     */
    String[] proj = {MediaStore.MediaColumns.DATA};



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 第一个是拍照
        if (requestCode == SELECT_IMAGE_RESULT_CODE && resultCode == RESULT_OK) {
            String imagePath = "";
            Uri uri = null;
            // 有数据返回直接使用返回的图片地址
            if (data != null && data.getData() != null) {
                uri = data.getData();
                Cursor cursor = getActivity().getContentResolver().query(uri, proj, null,
                        null, null);
                if (cursor == null) {
                    uri = ImageUtils.getUri(getActivity(), data);
                }
                imagePath = ImageUtils.getFilePathByFileUri(getActivity(), uri);
            } else {
                // 无数据使用指定的图片路径
                imagePath = mImagePath;
            }
            imgList.addFirst(imagePath);
            dataList.addFirst(imagePath);
            // 刷新图片
            adapter.update(dataList);
            // 多图选择
        } else if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
            StringBuilder sb = new StringBuilder();
            for (String p : mSelectPath) {
                sb.append(p);
                sb.append(",");
            }
            String imagePathToatle = sb.toString();
            String[] split = imagePathToatle.split(",");
            for (int i = 0; i < split.length; i++) {
                imgList.addFirst(split[i]);
                dataList.addFirst(split[i]);
            }
            adapter.update(dataList);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
