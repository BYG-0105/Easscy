package com.example.society.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.society.R;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener{

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Intent intent = getIntent();
        name = intent.getStringExtra("username");

        findViewById(R.id.backlt).setOnClickListener(this);
        findViewById(R.id.badd1).setOnClickListener(this);
        findViewById(R.id.badd2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.backlt:
                Intent intent = new Intent(CreateActivity.this,ForumActivity.class);
                intent.putExtra("username",name);
                startActivity(intent);
                finish();
                break;
            case R.id.badd1:
                Intent intentq = new Intent(CreateActivity.this,LtActivity.class);
                intentq.putExtra("username",name);
                startActivity(intentq);
                break;
            case R.id.badd2:
                Intent intentb = new Intent(CreateActivity.this,LtActivity.class);
                intentb.putExtra("username",name);
                startActivity(intentb);
                break;



        }
    }
}