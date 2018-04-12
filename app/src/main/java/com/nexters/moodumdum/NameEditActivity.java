package com.nexters.moodumdum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexters.moodumdum.api.MooDumDumService;
import com.nexters.moodumdum.factory.DeviceUuidFactory;
import com.nexters.moodumdum.model.UserDataModel;

import java.util.UUID;

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
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.title_profileImg)
    TextView titleProfileImg;
    @BindView(R.id.btn_ok)
    TextView btnOk;

    UserDataModel userDataModel = new UserDataModel();
//    PutUserDataModel putUserDataModel = new PutUserDataModel( "",null );

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
        DeviceUuidFactory uuidFactory = new DeviceUuidFactory( this );
        UUID uid = uuidFactory.getDeviceUuid();
        String uuid = uid.toString();

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

//    @OnClick(R.id.btn_ok)
//    public void onBtnOkClicked() {
//        putUserData();
//    }

//    public void putUserData() {
//        DeviceUuidFactory uuidFactory = new DeviceUuidFactory( this );
//        final UUID uid = uuidFactory.getDeviceUuid();
//        String uuid = uid.toString();
//        final String nickName = editName.getText().toString();
//
//        putUserDataModel.uuid = uuid;
//        putUserDataModel.nickName = editName.getText().toString();
//
//        MooDumDumService.of().putUserData( uuid, nickName).enqueue( new Callback<ServerResponse>() {
//            @Override
//            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
//                Toast.makeText( getBaseContext(), "변경", Toast.LENGTH_SHORT ).show();
//                Log.d("comment", call.toString());
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                Log.d("comment", t.getMessage());
//
//            }
//        });
//    }
}

