package com.android.daqsoft.androidbasics.ui;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.android.daqsoft.androidbasics.Main2Activity;
import com.android.daqsoft.androidbasics.MainActivity;
import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseActivity;
import com.android.daqsoft.androidbasics.base.IApplication;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.utils.ActivityUtils;
import com.android.daqsoft.androidbasics.utils.ToastUtils;
import com.android.daqsoft.androidbasics.view.DrawableTextView;
import com.android.daqsoft.androidbasics.view.KeyboardWatcher;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class LoginsActivity extends BaseActivity implements KeyboardWatcher.SoftKeyboardStateListener{
    @BindView(R.id.logo)
    DrawableTextView mLogo;
    @BindView(R.id.et_mobile)
    EditText mEtAccount;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.iv_clean_phone)
    ImageView mImgCleanPhone;
    @BindView(R.id.clean_password)
    ImageView mCleanPassword;
    @BindView(R.id.iv_show_pwd)
    ImageView mImgShowPwd;
    @BindView(R.id.body)
    LinearLayout body;

    private boolean flag = false;
    private int screenHeight = 0;//屏幕高度
    private KeyboardWatcher keyboardWatcher;
    @Override
    public int getContentViewResId() {
        return R.layout.activity_logins;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyboardWatcher = new KeyboardWatcher(findViewById(Window.ID_ANDROID_CONTENT));
        keyboardWatcher.addSoftKeyboardStateListener(this);
        initListener();
    }
    /**
     * 添加监听
     */
    private void initListener() {
        mEtAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && mImgCleanPhone.getVisibility() == View.GONE) {
                    mImgCleanPhone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    mImgCleanPhone.setVisibility(View.GONE);
                }
            }
        });
        mEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && mCleanPassword.getVisibility() == View.GONE) {
                    mCleanPassword.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    mCleanPassword.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    ToastUtils.showToast("请输入数字或字母");
                    s.delete(temp.length() - 1, temp.length());
                    mEtPassword.setSelection(s.length());
                }
            }
        });
    }
    @OnClick({ R.id.iv_clean_phone, R.id.clean_password, R.id.iv_show_pwd,R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login://登录
                String acctount = mEtAccount.getText().toString().trim();
                String psd = mEtPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(acctount)&&!TextUtils.isEmpty(psd)){
                    showLoadingDialog();
                    OkHttpUtils.get()
                            .url(Constant.BASE_URL+"imec/login?")
                            .addParams("username",acctount)
                            .addParams("password",psd)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onAfter(int id) {
                                    super.onAfter(id);
                                    dismissLoadingDialog();
                                }

                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    ToastUtils.showToastShort("登录失败!");
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    try {
                                        JSONObject object = JSONObject.parseObject(response);
                                        JSONObject data = object.getJSONObject("data");
                                        if (object.getIntValue("resultCode")==0){
                                            ToastUtils.showToastShort("登录成功");
                                            IApplication.SP.put("account",data.getString("account"));
                                            IApplication.SP.put("imghead",data.getString("profile"));
                                            IApplication.SP.put("bottomtag",data.getString("branch"));
                                            IApplication.SP.put("tel",data.getString("tel"));
                                            IApplication.SP.put("stationID",data.getString("stationID"));
                                            ActivityUtils.startActivity(Main2Activity.class);
                                            finish();
                                        }else {
                                            ToastUtils.showToastShort("登录失败!");
                                        }
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                }else {
                    ToastUtils.showToast("请检查登录信息");
                }
                break;
            case R.id.iv_clean_phone://清除账号
                mEtAccount.setText("");
                break;
            case R.id.clean_password://清除密码
                mEtPassword.setText("");
                break;
            case R.id.iv_show_pwd://显示密码
                if (flag == true) {
                    mEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mImgShowPwd.setImageResource(R.drawable.ic_login_closeeye);
                    flag = false;
                } else {
                    mEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mImgShowPwd.setImageResource(R.drawable.ic_login_openeye);
                    flag = true;
                }
                String pwd = mEtPassword.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    mEtPassword.setSelection(pwd.length());
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        keyboardWatcher.removeSoftKeyboardStateListener(this);
    }
    @Override
    public void onSoftKeyboardOpened(int keyboardSize) {
        int[] location = new int[2];
        body.getLocationOnScreen(location); //获取body在屏幕中的坐标,控件左上角
        int x = location[0];
        int y = location[1];
        Log.e("wenzhihao", "y = " + y + ",x=" + x);
        int bottom = screenHeight - (y + body.getHeight());
        Log.e("wenzhihao", "bottom = " + bottom);
        Log.e("wenzhihao", "con-h = " + body.getHeight());
        if (keyboardSize > bottom) {
            ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(body, "translationY", 0.0f, -(keyboardSize - bottom));
            mAnimatorTranslateY.setDuration(300);
            mAnimatorTranslateY.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimatorTranslateY.start();
            AnimationUtils.zoomIn(mLogo, keyboardSize - bottom);

        }
    }

    @Override
    public void onSoftKeyboardClosed() {
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(body, "translationY", body.getTranslationY(), 0);
        mAnimatorTranslateY.setDuration(300);
        mAnimatorTranslateY.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimatorTranslateY.start();
        AnimationUtils.zoomOut(mLogo);
    }
}
