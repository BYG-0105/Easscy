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

import com.example.society.Bean.Eassy;
import com.example.society.Bean.Forum;
import com.example.society.R;

import java.io.File;
import java.util.List;

public class ForumAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Forum> list;
    Context contexteassy;
    public ForumAdapter(Context context, List<Forum> list)
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
            convertview=layoutInflater.inflate(R.layout.forum_item_layout,null);
            viewHolder = new ViewHolder(convertview);
            convertview.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertview.getTag();
        }
        Forum forum = (Forum) getItem(position);
        viewHolder.Tname.setText(forum.getForumname());
        viewHolder.Tcon.setText(forum.getForumcomment());
        viewHolder.Tauthor.setText("作者："+forum.getForumauthor());
        viewHolder.Ttime.setText(forum.getTime());
        if(forum.getForumimgpath() != null)
        {
            Bitmap bitmap = getImgFromDesc(forum.getForumimgpath());
            viewHolder.imageView.setImageBitmap(bitmap);
        }
       else
        {
            viewHolder.imageView.setImageResource(R.drawable.bb);
        }
        return convertview;
    }

    class ViewHolder{
        TextView Tname;
        TextView Tcon;
        TextView Tauthor;
        TextView Ttime;
        ImageView imageView;
        public ViewHolder(View view)
        {
            Tname =(TextView) view.findViewById(R.id.namefor);
            Tcon =(TextView) view.findViewById(R.id.for_content);
            Tauthor =(TextView) view.findViewById(R.id.forauthor);
            Ttime =(TextView) view.findViewById(R.id.for_time);
            imageView =(ImageView) view.findViewById(R.id.imgfor);

        }
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
}
