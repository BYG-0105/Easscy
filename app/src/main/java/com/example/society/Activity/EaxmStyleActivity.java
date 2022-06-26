package com.example.society.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.society.Bean.Eassy;
import com.example.society.R;
import com.example.society.adapter.EassyAdapter;
import com.example.society.database.SQLiteHelper;

import java.util.List;

public class EaxmStyleActivity extends AppCompatActivity implements View.OnClickListener{

    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eaxm_style);

        Intent intent = getIntent();
        name = intent.getStringExtra("username");


        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.im_q).setOnClickListener(this);
        findViewById(R.id.im_f).setOnClickListener(this);
        findViewById(R.id.im_o).setOnClickListener(this);

        findViewById(R.id.bcbtn).setOnClickListener(this);
        findViewById(R.id.zsdbtn).setOnClickListener(this);
    }



    public void showToast(String message)
    {
        Toast.makeText(EaxmStyleActivity.this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                Intent intent = new Intent(EaxmStyleActivity.this,EassyActivity.class);
                intent.putExtra("username",name);
                startActivity(intent);
                finish();
                break;
            case R.id.im_q:
                Intent intentq = new Intent(EaxmStyleActivity.this,ForumActivity.class);
                intentq.putExtra("username",name);
                startActivity(intentq);
                break;
            case R.id.im_f:
                Intent intentb = new Intent(EaxmStyleActivity.this,EassyActivity.class);
                intentb.putExtra("username",name);
                startActivity(intentb);

                break;
            case R.id.im_o:
                Intent intento = new Intent(EaxmStyleActivity.this,OwnActivity.class);
                intento.putExtra("username",name);
                startActivity(intento);
                break;
            case R.id.bcbtn:
                Intent intentbtn = new Intent(EaxmStyleActivity.this,ProgrammingActivity.class);
                intentbtn.putExtra("username",name);
                startActivity(intentbtn);

                break;
            case R.id.zsdbtn:
                Intent intentzsd = new Intent(EaxmStyleActivity.this,EaxmActivity.class);
                intentzsd.putExtra("username",name);
                startActivity(intentzsd);
                break;

        }
    }
}

