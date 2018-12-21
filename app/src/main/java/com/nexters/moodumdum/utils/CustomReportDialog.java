package com.nexters.moodumdum.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nexters.moodumdum.R;
import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.model.ServerResponse;

import java.math.BigInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kimhyehyeon on 2018. 12. 21..
 */

public class CustomReportDialog extends Dialog {
    private Context context;

    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.tv_detail)
    TextView tv_description;

    private boolean selectTitle = false;
    private String title;
    private String targetUserId;
    private  BigInteger targetBoardId;

    public CustomReportDialog(@NonNull Context context, @NonNull String targetUserId, @NonNull BigInteger tartgetBoardId) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.targetUserId = targetUserId;
        this.targetBoardId = tartgetBoardId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.dialog_custom_report);

        ButterKnife.bind(this);

        initLayout();
    }

    private void initLayout()
    {
        String[] str = context.getResources().getStringArray(R.array.report);


        //2번에서 생성한 spinner_item.xml과 str을 인자로 어댑터 생성.

        final ArrayAdapter<String> adapter= new ArrayAdapter<>(getContext(),R.layout.item_spinner,str);

        adapter.setDropDownViewResource(R.layout.item_spinner);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner.getSelectedItemPosition() > 0) {
                    selectTitle = true;
                    title = parent.getItemAtPosition(position).toString();
                }
                else
                {
                    selectTitle = false;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void reportBadThings()
    {
        MooDumDumService.of().declareBadThings(targetUserId,title,tv_description.getText().toString(), targetBoardId)
                .enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if(response.isSuccessful())
                    {
                        Toast.makeText(context,"신고 완료 했습니다.",Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                    else
                    {
                        Log.e("declareBadThings",response.message());
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Log.e("declareBadThings",t.getMessage());
                }
            });
    }

    @OnClick(R.id.btn_report)
    public void report()
    {
        if(selectTitle)
        {
            reportBadThings();
        }
        else
        {
            Toast.makeText(context,"범주를 선택해 주세요.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        close();
    }

    @OnClick(R.id.btn_close)
    public void close() { closeDialog(this); }

    public static CustomReportDialog closeDialog(CustomReportDialog dialog) {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
        return dialog;
    }
}
