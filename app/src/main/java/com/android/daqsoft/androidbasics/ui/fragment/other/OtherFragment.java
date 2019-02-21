package com.android.daqsoft.androidbasics.ui.fragment.other;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.utils.ToastUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/3/25.
 */

public class OtherFragment extends BaseFragment implements
        OnChartValueSelectedListener {
    @BindView(R.id.include_img_back)
    ImageView includeImgBack;
    @BindView(R.id.include_tv_title)
    TextView includeTvTitle;
    @BindView(R.id.chart)
    LineChart mChart;
    @BindView(R.id.chart2)
    LineChart mChart2;
    @BindView(R.id.chart3)
    LineChart mChart3;
    @BindView(R.id.chart4)
    LineChart mChart4;
    private Timer timer;
    private String deviceId = "";
    private String deviceIP = "";
    private String devicePort = "";
    private String devideUsr = "";
    private String devicePwd = "";
    //单列
    public static OtherFragment newInstance(String deviceId,
                                            String deviceIP, String devicePort, String devideUsr,
                                            String devicePwd) {
        Bundle args = new Bundle();
        args.putString("deviceId", deviceId);
        args.putString("deviceIP", deviceIP);
        args.putString("devicePort", devicePort);
        args.putString("devideUsr", devideUsr);
        args.putString("devicePwd", devicePwd);
        OtherFragment fragment = new OtherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){
                getData();
            }
        }
    };
    @Override
    public void init(Bundle savedInstanceState) {
        includeTvTitle.setText("数据曲线");
        deviceId = getArguments().getString("deviceId");
        deviceIP = getArguments().getString("deviceIP");
        devicePort = getArguments().getString("devicePort");
        devideUsr = getArguments().getString("devideUsr");
        devicePwd = getArguments().getString("devicePwd");


        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);

        // add an empty data object
        mChart.setData(new LineData());
//        mChart.getXAxis().setDrawLabels(false);
//        mChart.getXAxis().setDrawGridLines(false);

        mChart.invalidate();

        mChart2.setOnChartValueSelectedListener(this);
        mChart2.setDrawGridBackground(false);
        mChart2.getDescription().setEnabled(false);

        // add an empty data object
        mChart2.setData(new LineData());
//        mChart.getXAxis().setDrawLabels(false);
//        mChart.getXAxis().setDrawGridLines(false);

        mChart2.invalidate();

        mChart3.setOnChartValueSelectedListener(this);
        mChart3.setDrawGridBackground(false);
        mChart3.getDescription().setEnabled(false);

        // add an empty data object
        mChart3.setData(new LineData());
//        mChart.getXAxis().setDrawLabels(false);
//        mChart.getXAxis().setDrawGridLines(false);

        mChart3.invalidate();


        mChart4.setOnChartValueSelectedListener(this);
        mChart4.setDrawGridBackground(false);
        mChart4.getDescription().setEnabled(false);

        // add an empty data object
        mChart4.setData(new LineData());
//        mChart.getXAxis().setDrawLabels(false);
//        mChart.getXAxis().setDrawGridLines(false);

        mChart4.invalidate();


        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // (1) 使用handler发送消息
                Message message=new Message();
                message.what=0;
                mHandler.sendMessage(message);
            }
        },0,10000);//每隔一秒使用handler发送一下消息,也就是每隔一秒执行一次,一直重复执行

    }

    private void getData(){
        GetBuilder getBuilder = OkHttpUtils.get()
                .url(Constant.BASE_URL + "imec/getDeviceAttribute")
                .addParams("deviceID", deviceId)
                .addParams("deviceIP", deviceIP)
                .addParams("devicePort", devicePort)
                .addParams("devideUsr", devideUsr)
                .addParams("devicePwd", devicePwd)
                .addParams("firParameter", "dat")
                .addParams("secParameter","5");

        getBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject object = JSONObject.parseObject(response);
                            if (object.getIntValue("resultCode")==0){
                                String value = object.getString("data");
                                String[] split = value.split(" ");
                                if (split[4].equals("02")&&split[5].equals("04")){
                                    addEntry(split[6],split[10]);
                                    addEntry2(split[7],split[11]);
                                    addEntry3(split[8],split[12]);
                                    addEntry4(split[9],split[13]);
                                }else if (split[4].equals("60")&&split[5].equals("06")){
                                    ToastUtils.showToast("请到当前数据查看!");
                                    timer.cancel();
                                }else {
                                    timer.cancel();
                                    ToastUtils.showToast("暂无数据!");
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_other;
    }



    @OnClick(R.id.include_img_back)
    public void onViewClicked() {
        _mActivity.onBackPressed();
        timer.cancel();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        timer.cancel();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void addEntry(String name,String value) {
        LineData data = mChart.getData();
        ILineDataSet set = data.getDataSetByIndex(0);
        // set.addEntry(...); // can be called as well


        if (set == null) {
            set = createSet(name);
            data.addDataSet(set);
        }

        // choose a random dataSet
        int randomDataSetIndex = (int) (Math.random() * data.getDataSetCount());
        double v = Double.parseDouble(value);
        float yValue = (float) v;

        data.addEntry(new Entry(data.getDataSetByIndex(randomDataSetIndex).getEntryCount(), yValue), randomDataSetIndex);
        data.notifyDataChanged();

        // let the chart know it's data has changed
        mChart.notifyDataSetChanged();

        mChart.setVisibleXRangeMaximum(6);
        //mChart.setVisibleYRangeMaximum(15, AxisDependency.LEFT);
//
//            // this automatically refreshes the chart (calls invalidate())
        mChart.moveViewTo(data.getEntryCount() - 7, 50f, YAxis.AxisDependency.LEFT);

    }
    private LineDataSet createSet(String name) {
        LineDataSet set = new LineDataSet(null, name);
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColor(Color.rgb(240, 99, 99));
        set.setCircleColor(Color.rgb(240, 99, 99));
        set.setHighLightColor(Color.rgb(190, 190, 190));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setValueTextSize(10f);

        return set;
    }

    private void addEntry2(String name,String value) {
        LineData data = mChart2.getData();
        ILineDataSet set = data.getDataSetByIndex(0);
        // set.addEntry(...); // can be called as well


        if (set == null) {
            set = createSet2(name);
            data.addDataSet(set);
        }

        // choose a random dataSet
        int randomDataSetIndex = (int) (Math.random() * data.getDataSetCount());
        double v = Double.parseDouble(value);
        float yValue = (float) v;

        data.addEntry(new Entry(data.getDataSetByIndex(randomDataSetIndex).getEntryCount(), yValue), randomDataSetIndex);
        data.notifyDataChanged();

        // let the chart know it's data has changed
        mChart2.notifyDataSetChanged();

        mChart2.setVisibleXRangeMaximum(6);
        //mChart.setVisibleYRangeMaximum(15, AxisDependency.LEFT);
//
//            // this automatically refreshes the chart (calls invalidate())
        mChart2.moveViewTo(data.getEntryCount() - 7, 50f, YAxis.AxisDependency.LEFT);

    }
    private LineDataSet createSet2(String name) {
        LineDataSet set = new LineDataSet(null, name);
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColor(Color.rgb(140, 99, 99));
        set.setCircleColor(Color.rgb(140, 99, 99));
        set.setHighLightColor(Color.rgb(90, 190, 190));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setValueTextSize(10f);

        return set;
    }

    private void addEntry3(String name,String value) {
        LineData data = mChart3.getData();
        ILineDataSet set = data.getDataSetByIndex(0);
        // set.addEntry(...); // can be called as well


        if (set == null) {
            set = createSet3(name);
            data.addDataSet(set);
        }

        // choose a random dataSet
        int randomDataSetIndex = (int) (Math.random() * data.getDataSetCount());
        double v = Double.parseDouble(value);
        float yValue = (float) v;

        data.addEntry(new Entry(data.getDataSetByIndex(randomDataSetIndex).getEntryCount(), yValue), randomDataSetIndex);
        data.notifyDataChanged();

        // let the chart know it's data has changed
        mChart3.notifyDataSetChanged();

        mChart3.setVisibleXRangeMaximum(6);
        //mChart.setVisibleYRangeMaximum(15, AxisDependency.LEFT);
//
//            // this automatically refreshes the chart (calls invalidate())
        mChart3.moveViewTo(data.getEntryCount() - 7, 50f, YAxis.AxisDependency.LEFT);

    }
    private LineDataSet createSet3(String name) {
        LineDataSet set = new LineDataSet(null, name);
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColor(Color.rgb(140, 99, 199));
        set.setCircleColor(Color.rgb(140, 99, 199));
        set.setHighLightColor(Color.rgb(90, 190, 90));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setValueTextSize(10f);

        return set;
    }
    private void addEntry4(String name,String value) {
        LineData data = mChart4.getData();
        ILineDataSet set = data.getDataSetByIndex(0);
        // set.addEntry(...); // can be called as well


        if (set == null) {
            set = createSet4(name);
            data.addDataSet(set);
        }

        // choose a random dataSet
        int randomDataSetIndex = (int) (Math.random() * data.getDataSetCount());
        double v = Double.parseDouble(value);
        float yValue = (float) v;

        data.addEntry(new Entry(data.getDataSetByIndex(randomDataSetIndex).getEntryCount(), yValue), randomDataSetIndex);
        data.notifyDataChanged();

        // let the chart know it's data has changed
        mChart4.notifyDataSetChanged();

        mChart4.setVisibleXRangeMaximum(6);
        //mChart.setVisibleYRangeMaximum(15, AxisDependency.LEFT);
//
//            // this automatically refreshes the chart (calls invalidate())
        mChart4.moveViewTo(data.getEntryCount() - 7, 50f, YAxis.AxisDependency.LEFT);

    }
    private LineDataSet createSet4(String name) {
        LineDataSet set = new LineDataSet(null, name);
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColor(Color.rgb(140, 199, 99));
        set.setCircleColor(Color.rgb(140, 199, 99));
        set.setHighLightColor(Color.rgb(90, 90, 190));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setValueTextSize(10f);

        return set;
    }
}
