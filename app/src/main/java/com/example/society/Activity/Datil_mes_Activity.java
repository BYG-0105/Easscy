package com.example.society.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.society.R;

public class Datil_mes_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eassy_mes);

        Intent intent = getIntent();
        String name = intent.getStringExtra("username");
        String content = intent.getStringExtra("content");
        String time  = intent.getStringExtra("time");

        TextView tvc = findViewById(R.id.t_con);
        TextView tvt = findViewById(R.id.t_time);
        tvc.setText(content);
        tvt.setText(time);

        ImageView cal = findViewById(R.id.cal);
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Datil_mes_Activity.this,EassyActivity.class);
                intent1.putExtra("username",name);
                startActivity(intent1);
            }
        });
    }
}