package com.nexters.moodumdum;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_name_edit );
        ButterKnife.bind( this );


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
//                countOfLength.setText( "(" + editName.length() + "/" + "10)" );
            }
        } );


    }

    @OnClick(R.id.btnX)
    public void onBtnXClicked() {
        editName.setText( null );
    }
}
