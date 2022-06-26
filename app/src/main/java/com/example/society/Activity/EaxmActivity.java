package com.example.society.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.society.Bean.ExercisesBean;
import com.example.society.R;
import com.example.society.adapter.ExercisesListItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class EaxmActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView vlist;//传给fragment_exercises
    private ExercisesListItemAdapter adapter; //适配器
    private List<ExercisesBean> beanlist; //列表集合
    private ImageView imageView;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eaxm);

        Intent intent = getIntent();
        name = intent.getStringExtra("username");



        initData();
        vlist = findViewById(R.id.lv_list);
        adapter = new ExercisesListItemAdapter(EaxmActivity.this);//获取适配器
        adapter.setData(beanlist);//数据传输
        vlist.setAdapter(adapter);
        imageView = findViewById(R.id.eaxmback);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(EaxmActivity.this,EassyActivity.class);
                intento.putExtra("username",name);
                startActivity(intento);
            }
        });

        findViewById(R.id.im_q).setOnClickListener(this);
        findViewById(R.id.im_f).setOnClickListener(this);
        findViewById(R.id.im_o).setOnClickListener(this);
        findViewById(R.id.im_b).setOnClickListener(this);
    }



    //章节习题信息
    private void initData(){
        beanlist = new ArrayList<ExercisesBean>();
        for (int i=0;i<10;i++){
            ExercisesBean bean = new ExercisesBean();
            bean.id=(i+1);
            switch (i){
                case 0:
                    bean.title="背包问题简述";
                    bean.content="共计10题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 1:
                    bean.title="背包问题详细介绍";
                    bean.content="共计10题";
                    bean.background=(R.drawable.exercises_bg_2);
                    break;
                case 2:
                    bean.title="背包问题贪心算法解决";
                    bean.content="共计10题";
                    bean.background=(R.drawable.exercises_bg_3);
                    break;
                case 3:
                    bean.title="背包问题遗传算法解决";
                    bean.content="共计10题";
                    bean.background=(R.drawable.exercises_bg_4);
                    break;
                case 4:
                    bean.title="背包问题动态规划算法解决";
                    bean.content="共计10题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 5:
                    bean.title="背包问题回溯算法解决";
                    bean.content="共计10题";
                    bean.background=(R.drawable.exercises_bg_2);
                    break;
                case 6:
                    bean.title="背包问题总结";
                    bean.content="共计10题";
                    bean.background=(R.drawable.exercises_bg_3);
                    break;
                case 7:
                    bean.title="背包问题概述";
                    bean.content="共计10题";
                    bean.background=(R.drawable.exercises_bg_4);
                    break;
                case 8:
                    bean.title="背包问题总结";
                    bean.content="共计10题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 9:
                    bean.title="背包问题";
                    bean.content="共计10题";
                    bean.background=(R.drawable.exercises_bg_2);
                    break;
                default:
                    break;
            }
            beanlist.add(bean);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.im_q:
                Intent intentq = new Intent(EaxmActivity.this,ForumActivity.class);
                intentq.putExtra("username",name);
                startActivity(intentq);
                break;
            case R.id.im_f:
                Intent intentb = new Intent(EaxmActivity.this,EassyActivity.class);
                intentb.putExtra("username",name);
                startActivity(intentb);

                break;
            case R.id.im_o:
                Intent intento = new Intent(EaxmActivity.this,OwnActivity.class);
                intento.putExtra("username",name);
                startActivity(intento);
                break;


        }
    }
}




