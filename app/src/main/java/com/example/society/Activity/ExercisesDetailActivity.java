package com.example.society.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.society.R;
import com.example.society.adapter.ExercisesDetailListItemAdapter;
import com.example.society.Bean.ExercisesBean;
import com.example.society.utils.AnalysisUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExercisesDetailActivity extends Activity {
    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout title_bar;
    private TextView tv_save;
    private TextView tv_di;
    private int id;
    private String title;
    private RecyclerView rv_list;

    private List<ExercisesBean> ebl;
    private ExercisesDetailListItemAdapter adapter;
    //
    public  int ff = 0;

    private int score=0;

    private static TextView text_time = null;
    private static TimeCount time;
    private String namess;
    //注册计时器
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            Toast.makeText(ExercisesDetailActivity.this,"考试结束，共计"+score+"分",Toast.LENGTH_SHORT).show();
            ExercisesDetailActivity.this.finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            long curr_time=millisUntilFinished / 1000;
            text_time.setText("倒计时："+curr_time + "秒");
            if(curr_time==120){
                Toast.makeText(ExercisesDetailActivity.this,"距离考试结束还有120秒",Toast.LENGTH_SHORT).show();

            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_detail);
        id = getIntent().getIntExtra("id",0);
        title = getIntent().getStringExtra("title");
        ebl = new ArrayList<ExercisesBean>();
        initData();
        initView();
        initTime(savedInstanceState);

        Intent intent = getIntent();
        namess = intent.getStringExtra("username");
    }

    private void initTime(Bundle savedInstanceState) {
        time = new TimeCount(300000, 1000);//构造CountDownTimer对象
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    private void initData() {
        //从xml文件中获取习题数据
        try {
            InputStream is = getResources().getAssets().open("chapter" + id + ".xml");
            ebl = AnalysisUtils.getExercisesInfos(is);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        tv_save = findViewById(R.id.tv_save);
        tv_back = findViewById(R.id.tv_back);
        tv_main_title = findViewById(R.id.tv_main_title);
        tv_main_title.setText(title);
        title_bar = findViewById(R.id.title_bar);
        title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        //
        tv_di = findViewById(R.id.tv_di);
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ExercisesDetailActivity.this,"考试结束，共计"+score+"分",Toast.LENGTH_SHORT).show();
                time.cancel();
                ExercisesDetailActivity.this.finish();

            }
        });
        adapter = new ExercisesDetailListItemAdapter(ExercisesDetailActivity.this,
                new ExercisesDetailListItemAdapter.OnSelectListener() {
                    @Override
                    public void onSelectA(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d) {
                        //判断如果答案不是1即A选项
                        if (ebl.get(position).answer != 1) {
                            ebl.get(position).select = 1;
                        } else {
                            ebl.get(position).select = 0;
                        }
                        switch (ebl.get(position).answer) {
                            case 1:
                                iv_a.setImageResource(R.drawable.exercises_right_icon);
                                score+=10;
                                break;
                            case 2:
                                iv_a.setImageResource(R.drawable.exercises_error_icon);
                                iv_b.setImageResource(R.drawable.exercises_right_icon);
                                break;
                            case 3:
                                iv_a.setImageResource(R.drawable.exercises_error_icon);
                                iv_c.setImageResource(R.drawable.exercises_right_icon);
                                break;
                            case 4:
                                iv_a.setImageResource(R.drawable.exercises_error_icon);
                                iv_d.setImageResource(R.drawable.exercises_right_icon);
                                break;
                        }
                        AnalysisUtils.setABCDEnable(false, iv_a, iv_b, iv_c, iv_d);
                    }

                    @Override
                    public void onSelectB(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d) {
                        //判断如果答案不是2即B选项
                        if (ebl.get(position).answer != 2) {
                            ebl.get(position).select = 2;
                        } else {
                            ebl.get(position).select = 0;
                        }
                        switch (ebl.get(position).answer) {
                            case 1:
                                iv_a.setImageResource(R.drawable.exercises_right_icon);
                                iv_b.setImageResource(R.drawable.exercises_error_icon);
                                break;
                            case 2:
                                iv_b.setImageResource(R.drawable.exercises_right_icon);
                                break;
                            case 3:
                                iv_b.setImageResource(R.drawable.exercises_error_icon);
                                iv_c.setImageResource(R.drawable.exercises_right_icon);
                                break;
                            case 4:
                                iv_b.setImageResource(R.drawable.exercises_error_icon);
                                iv_d.setImageResource(R.drawable.exercises_right_icon);
                                break;
                        }
                        AnalysisUtils.setABCDEnable(false, iv_a, iv_b, iv_c, iv_d);
                    }

                    @Override
                    public void onSelectC(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d) {
                        //判断如果答案不是3即C选项
                        if (ebl.get(position).answer != 3) {
                            ebl.get(position).select = 3;
                        } else {
                            ebl.get(position).select = 0;
                        }
                        switch (ebl.get(position).answer) {
                            case 1:
                                iv_a.setImageResource(R.drawable.exercises_right_icon);
                                iv_c.setImageResource(R.drawable.exercises_error_icon);
                                break;
                            case 2:
                                iv_b.setImageResource(R.drawable.exercises_right_icon);
                                iv_c.setImageResource(R.drawable.exercises_error_icon);
                                break;
                            case 3:
                                iv_c.setImageResource(R.drawable.exercises_right_icon);
                                break;
                            case 4:
                                iv_c.setImageResource(R.drawable.exercises_error_icon);
                                iv_d.setImageResource(R.drawable.exercises_right_icon);
                                break;
                        }
                        AnalysisUtils.setABCDEnable(false, iv_a, iv_b, iv_c, iv_d);
                    }

                    @Override
                    public void onSelectD(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d) {
                        //判断如果答案不是4即D选项
                        if (ebl.get(position).answer != 4) {
                            ebl.get(position).select = 4;
                        } else {
                            ebl.get(position).select = 0;
                        }
                        switch (ebl.get(position).answer) {
                            case 1:
                                iv_a.setImageResource(R.drawable.exercises_right_icon);
                                iv_d.setImageResource(R.drawable.exercises_error_icon);
                                break;
                            case 2:
                                iv_d.setImageResource(R.drawable.exercises_error_icon);
                                iv_b.setImageResource(R.drawable.exercises_right_icon);
                                break;
                            case 3:
                                iv_d.setImageResource(R.drawable.exercises_error_icon);
                                iv_c.setImageResource(R.drawable.exercises_right_icon);
                                break;
                            case 4:
                                iv_d.setImageResource(R.drawable.exercises_right_icon);
                                break;
                        }
                        AnalysisUtils.setABCDEnable(false, iv_a, iv_b, iv_c, iv_d);
                    }
                });
        adapter.setData(ebl);
        adapter.setOnItemListener(new ExercisesDetailListItemAdapter.OnItemListener() {
            @Override
            public void onItem(View view, int position) {
                ff=ff+1;
                tv_di.setText("第"+(position+1)+"题完成，共"+adapter.getItemCount()+"题");

                if (ff==5){
                    AnalysisUtils.saveExerciseStatus(ExercisesDetailActivity.this,id);
                    Log.i("DD",id+"");
                    setResult(RESULT_OK);
                    //ff = 0;
                }
            }

        });
        rv_list.setAdapter(adapter);
    }
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);

        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_time, container, false);
            text_time = (TextView) rootView.findViewById(R.id.bt_get);
            time.start();

            return rootView;
        }
    }



}