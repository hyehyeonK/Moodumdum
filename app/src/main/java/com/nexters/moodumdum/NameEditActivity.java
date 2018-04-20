package com.nexters.moodumdum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.model.PutUserDataModel;
import com.nexters.moodumdum.model.UserDataModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NameEditActivity extends AppCompatActivity {

    @BindView(R.id.countOfLength)
    TextView countOfLength;
    @BindView(R.id.editName)
    EditText editName;
    @BindView(R.id.btnX)
    ImageView btnX;
    @BindView(R.id.title_profileImg)
    TextView titleProfileImg;
    @BindView(R.id.btn_ok)
    TextView btnOk;

    UserDataModel userDataModel = new UserDataModel();
    PutUserDataModel putUserDataModel = new PutUserDataModel( "",null );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_name_edit );
        ButterKnife.bind( this );

        Intent intent = getIntent();
        String myname = intent.getStringExtra( "myName" );

        getUserData();

        editName.setText( myname );
        countOfLength.setText( "(" + editName.length() + "/" + "10)" );

        editName.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    btnX.setVisibility( View.VISIBLE );
                } else {
                    btnX.setVisibility( View.INVISIBLE );
                }
                countOfLength.setText( "(" + editName.length() + "/" + "10)" );
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        } );
    }

    public void getUserData() {
        String uuid = ((MainActivity) MainActivity.MainAct).getUUID();

        MooDumDumService.of().getUserData( uuid ).enqueue( new Callback<UserDataModel>() {
            @Override
            public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                userDataModel = response.body();
            }

            @Override
            public void onFailure(Call<UserDataModel> call, Throwable t) {

            }
        } );
    }

    @OnClick(R.id.btnX)
    public void onBtnXClicked() {
        editName.setText( null );
    }

    @OnClick(R.id.btn_ok)
    public void onBtnOkClicked() {
        putUserData();
    }

    public void putUserData() {
        String uuid = ((MainActivity) MainActivity.MainAct).getUUID();
        final String name = editName.getText().toString();
        String profile_image = "";  // 프로필 이미지는 필요없음

        MooDumDumService.of().putUserData( uuid, uuid, name, profile_image).enqueue( new Callback<PutUserDataModel>() {
            @Override
            public void onResponse(Call<PutUserDataModel> call, Response<PutUserDataModel> response) {
                Intent intent = new Intent();
                intent.putExtra( "name", editName.getText().toString() );
                setResult( RESULT_OK, intent );
                finish();
                Log.d("nicknameChange", call.toString());
            }

            @Override
            public void onFailure(Call<PutUserDataModel> call, Throwable t) {
                Log.d("nicknameChangErr", t.getMessage());

            }
        });
    }
    @OnClick(R.id.btn_back)
    public void onBtnBackClicked() {
        this.finish();
        overridePendingTransition( R.anim.not_move_activity, R.anim.leftout_activity );
    }


}

