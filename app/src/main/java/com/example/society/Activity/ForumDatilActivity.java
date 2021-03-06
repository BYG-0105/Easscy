package com.example.society.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.society.Bean.Comment;
import com.example.society.Bean.Eassy;
import com.example.society.Bean.Forum;
import com.example.society.R;
import com.example.society.adapter.CommentAdapter;
import com.example.society.adapter.EassyAdapter;
import com.example.society.database.DBinformation;
import com.example.society.database.SQLiteHelper;

import java.io.File;
import java.util.List;

public class ForumDatilActivity extends AppCompatActivity implements View.OnClickListener{
    ListView listView;
    List<Comment> list;
    SQLiteHelper sQliteHelper;
    SQLiteHelper.Comment comment = new SQLiteHelper.Comment();
    CommentAdapter adapter;
     TextView tforumname,forumtime,forumcontent,commentsave;
    ImageView back,forumimg,csave;
    String name;
    String forumname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_datil);


        Intent intent = getIntent();
        name = intent.getStringExtra("username");
        forumname = intent.getStringExtra("forumname");


        listView = (ListView) findViewById(R.id.comment_listview);


        forumimg = findViewById(R.id.forumimg);
        tforumname = findViewById(R.id.forum_name);
        forumtime = findViewById(R.id.forumtime);
        forumcontent = findViewById(R.id.forumcontent);
        commentsave = findViewById(R.id.tvautcomment);

        tforumname.setText(forumname);

        SQLiteHelper.Forum forum1 = new SQLiteHelper.Forum();
        Forum forum = forum1.userquery(forumname);
        if(forum.getForumimgpath() != null)
        {
            Bitmap bitmap = getImgFromDesc(forum.getForumimgpath());
            forumimg.setImageBitmap(bitmap);
        }
        else
        {
            forumimg.setImageResource(R.drawable.bb);
        }

        forumcontent.setText(forum.getForumcomment());
        forumtime.setText(forum.getTime());
        findViewById(R.id.backfor).setOnClickListener(this);
        findViewById(R.id.commentsave).setOnClickListener(this);

        initData();




    }
    protected void initData()
    {
        sQliteHelper = new SQLiteHelper(this);
        showQueryData();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int position, long id) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(ForumDatilActivity.this)
                        .setMessage("????????????????????????")
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Comment comments = list.get(position);
                                if(comments.getAuthorname().equals(name))
                                {
                                    if(comment.deleteData(comments.getId()))
                                    {
                                        list.remove(position);
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(ForumDatilActivity.this,"????????????",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(ForumDatilActivity.this,"??????????????????????????????????????????",Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                        .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }
    private void showQueryData()
    {
        if(list != null)
        {
            list.clear();

        }
        list = comment.query();
        adapter = new CommentAdapter(this,list);
        listView.setAdapter(adapter);

    }
    public void showToast(String message)
    {
        Toast.makeText(ForumDatilActivity.this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.backfor:
                Intent intent = new Intent(ForumDatilActivity.this,ForumActivity.class);
                intent.putExtra("username",name);
                startActivity(intent);
                finish();
                break;
            case R.id.commentsave:
                String commentstr = commentsave.getText().toString();
                if(comment.insertData(forumname,name,commentstr, DBinformation.getTime()))
                {
                    showToast("????????????");
                    showQueryData();
                    commentsave.setText("");
                }
                else
                {
                    showToast("????????????");
                }
                break;



        }
    }
    //????????????????????????
    private Bitmap getImgFromDesc(String path) {
        Bitmap bm = null;
        File file = new File(path);
        // ??????????????????
        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        final int REQUEST_CODE = 10001;

        // ???????????????????????????????????? 23 ?????????????????????????????????????????????
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // ?????????????????????????????????

            for (String permission : permissions) {
                //  GRANTED---??????  DINIED---??????
                if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
                }
            }
        }

        boolean permission_readStorage = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        boolean permission_camera = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
        Log.d("ImgActivity:", "getImageFromDesc: \n");
        Log.d("ImgActivity: ", "readPermission: " + permission_readStorage + "\n");
        Log.d("ImgActivity??? ", "cameraPermission: " + permission_camera + "\n");

        if(file.exists()) {
            bm = BitmapFactory.decodeFile(path);
        } else {

            Toast.makeText(ForumDatilActivity.this,"?????????????????????",Toast.LENGTH_LONG).show();
            Log.d("ImgActivity ", "getImgFromDesc: ?????????????????????");
        }
        return bm;
    }
}