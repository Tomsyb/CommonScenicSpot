package com.android.daqsoft.androidbasics.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.daqsoft.androidbasics.R;


/**
 * 作者：严博
 * 日期：16/3/22
 * 作用：选择是直接拍照还是从相册选择
 */
public class ChoicePopupWindow extends PopupWindow {
    private Context context;
    private View.OnClickListener firstListener, secondListener;


    public ChoicePopupWindow(Context context, View.OnClickListener firstListener, View.OnClickListener secondListener) {
        this.context = context;
        this.firstListener = firstListener;
        this.secondListener = secondListener;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow_layout, null);

        this.setContentView(view);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.AnimBottom);
        ColorDrawable dw = new ColorDrawable(0x000000);
        this.setBackgroundDrawable(dw);
        Button popupWindow_picture = (Button) view.findViewById(R.id.popupWindow_picture);
        Button popupWindow_phone = (Button) view.findViewById(R.id.popupWindow_phone);

        popupWindow_picture.setOnClickListener(firstListener);
        popupWindow_phone.setOnClickListener(secondListener);

        view.findViewById(R.id.popupWindow_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int height = view.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

}
