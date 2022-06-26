package com.example.society.adapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.society.Bean.Comment;
import com.example.society.Bean.Eassy;
import com.example.society.Bean.Forum;
import com.example.society.Bean.Loginuser;
import com.example.society.R;
import com.example.society.database.SQLiteHelper;

import java.io.File;
import java.util.List;


public class CommentAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Comment> list;
    Context contexteassy;
    public CommentAdapter(Context context, List<Comment> list)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.list=list;
        this.contexteassy = context;
    }
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertview == null)
        {
            convertview=layoutInflater.inflate(R.layout.comment_item,null);
            viewHolder = new ViewHolder(convertview);
            convertview.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertview.getTag();
        }
        Comment comment2 = (Comment) getItem(position);
        SQLiteHelper.Comment comment = new SQLiteHelper.Comment();
        Comment comment1 = comment.userquery(comment2.getForumname());



        SQLiteHelper.User user = new SQLiteHelper.User();
        if(comment1.getAuthorname() != null) {
            viewHolder.tvtime.setText(comment1.getTime());
            viewHolder.tvcotent.setText(comment1.getContent());
            viewHolder.tvauthor.setText(comment1.getAuthorname());
            Loginuser loginuser = user.userquery(comment1.getAuthorname());

            if (loginuser.getImgpath() != null) {

                Bitmap bitmap = getImgFromDesc(loginuser.getImgpath());
                viewHolder.cauthorimg.setImageBitmap(bitmap);
            } else {
                viewHolder.cauthorimg.setImageResource(R.drawable.bb);
            }
        }
            return convertview;


    }


    //根据路径获取图片
    private Bitmap getImgFromDesc(String path) {
        Bitmap bm = null;
        File file = new File(path);

        // 动态申请权限
        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        final int REQUEST_CODE = 10001;

        boolean permission_readStorage = (ContextCompat.checkSelfPermission(contexteassy,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        boolean permission_camera = (ContextCompat.checkSelfPermission(contexteassy,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
        Log.d("ImgActivity:", "getImageFromDesc: \n");
        Log.d("ImgActivity: ", "readPermission: " + permission_readStorage + "\n");
        Log.d("ImgActivity： ", "cameraPermission: " + permission_camera + "\n");

        if(file.exists()) {
            bm = BitmapFactory.decodeFile(path);
        } else {

            //Toast.makeText(ChangePwdActivity.this,"该图片不存在！",Toast.LENGTH_LONG).show();
            Log.d("ImgActivity ", "getImgFromDesc: 该图片不存在！");
        }
        return bm;
    }

    class ViewHolder{
        TextView tvtime;
        TextView tvcotent;
        ImageView cauthorimg;
        TextView tvauthor;
        public ViewHolder(View view)
        {
            tvtime =(TextView) view.findViewById(R.id.tvtime);
            tvcotent = (TextView) view.findViewById(R.id.item_comment);
            tvauthor = (TextView) view.findViewById(R.id.tvauthor);
            cauthorimg = (ImageView) view.findViewById(R.id.imgcauthor);
        }
    }
}
