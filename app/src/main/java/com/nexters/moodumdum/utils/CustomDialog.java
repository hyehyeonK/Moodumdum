package com.nexters.moodumdum.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.nexters.moodumdum.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kimhyehyeon on 2018. 12. 8..
 */

public class CustomDialog extends Dialog {

    @BindView(R.id.dialog_title)
    TextView tvTitle;
    @BindView(R.id.dialog_msg)
    TextView tvMsg;
    @BindView(R.id.dialog_btnOk)
    Button btnOk;
    @BindView(R.id.dialog_btnCancel)
    Button btnCancel;

    private String title, msg, btnName;
    private View.OnClickListener btnListener;

    //one button
    public CustomDialog(@NonNull Context context, @StringRes int title, @StringRes int msg, @StringRes int btnName) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.title = getContext().getResources().getString(title);
        this.msg = getContext().getResources().getString(msg);
        this.btnName = getContext().getResources().getString(btnName);
    }

    //two button default(네 아니오)
    public CustomDialog(@NonNull Context context, @StringRes int title, @StringRes int msg, View.OnClickListener listener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.title = getContext().getResources().getString(title);
        this.msg = getContext().getResources().getString(msg);
        this.btnListener = listener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.dialog_custom);

        ButterKnife.bind(this);

        initLayout();
    }

    private void initLayout() {

        if (title != null) {
            tvTitle.setText(title);
        }
        if (msg != null) {
            tvMsg.setText(msg);
        }

        if (btnName != null) {
            btnCancel.setVisibility(View.GONE);
            btnOk.setText(btnName);
        } else {
            btnOk.setOnClickListener(btnListener);
        }

        if(btnName != null)
        {
            btnOk.setText(btnName);
        }

    }

    @OnClick(R.id.dialog_btnCancel)
    public void onClickCloseButton() {
        closeDialog(this);
    }

    public static CustomDialog closeDialog(CustomDialog dialog) {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
        return dialog;
    }
}
