package com.example.society.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.society.R;

public class OwnActivity extends AppCompatActivity implements View.OnClickListener {

    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own);


        findViewById(R.id.btn_xgmm).setOnClickListener(this);
        findViewById(R.id.btn_xgmes).setOnClickListener(this);
        findViewById(R.id.btn_zy).setOnClickListener(this);
        findViewById(R.id.btn_finish).setOnClickListener(this);


        findViewById(R.id.im_q).setOnClickListener(this);
        findViewById(R.id.im_f).setOnClickListener(this);
        findViewById(R.id.im_o).setOnClickListener(this);
        findViewById(R.id.im_b).setOnClickListener(this);

        TextView tvname = findViewById(R.id.tvname);
        Intent intent = getIntent();
        name = intent.getStringExtra("username");
        tvname.setText("欢迎您，"+name+" ! 您已成功登录");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_xgmm:
                Intent intentm = new Intent(OwnActivity.this,ChangePwdActivity.class);
                intentm.putExtra("username",name);
                startActivity(intentm);
                break;
            case R.id.btn_xgmes:
                Intent intentmes = new Intent(OwnActivity.this,RegisterActivity.class);
                intentmes.putExtra("username",name);
                startActivity(intentmes);
                break;
            case R.id.btn_zy:
                Intent intentz = new Intent(OwnActivity.this,ZyActivity.class);
                intentz.putExtra("username",name);
                startActivity(intentz);
                break;
            case R.id.btn_finish:
                Intent intentf = new Intent(OwnActivity.this,EassyActivity.class);
                intentf.putExtra("username", name);
                startActivity(intentf);
                break;
            case R.id.im_f:
                Intent intentff= new Intent(OwnActivity.this,EassyActivity.class);
                intentff.putExtra("username",name);
                startActivity(intentff);
                break;
            case R.id.im_q:
                Intent intentq = new Intent(OwnActivity.this,ForumActivity.class);
                intentq.putExtra("username",name);
                startActivity(intentq);
                break;
            case R.id.im_b:
                Intent intentb = new Intent(OwnActivity.this,ExercisesDetailActivity.class);
                intentb.putExtra("username",name);
                startActivity(intentb);

                break;
            case R.id.im_o:
                Intent intento = new Intent(OwnActivity.this,OwnActivity.class);
                intento.putExtra("username",name);
                startActivity(intento);
                break;
        }

    }
}
