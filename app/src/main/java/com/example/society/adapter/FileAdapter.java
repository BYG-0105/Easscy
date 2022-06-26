package com.example.society.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.society.Activity.FileGrazeActivity;
import com.example.society.Bean.Forum;
import com.example.society.R;

import java.io.File;
import java.util.List;

public class FileAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<com.example.society.Bean.File> list;
    Context contexteassy;
    public FileAdapter(Context context, List<com.example.society.Bean.File> list)
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
            convertview=layoutInflater.inflate(R.layout.file_item,null);
            viewHolder = new ViewHolder(convertview);
            convertview.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertview.getTag();
        }
        com.example.society.Bean.File file = (com.example.society.Bean.File) getItem(position);
        viewHolder.Tname.setText(file.getName());
        viewHolder.Tauthor.setText("作者："+file.getAuthor());
        viewHolder.Ttime.setText(file.getTime());
        if(file.getPath() != null)
        {
            Bitmap bitmap = getImgFromDesc(file.getPath());
            viewHolder.imageView.setImageBitmap(bitmap);
        }
        else
        {
            viewHolder.imageView.setImageResource(R.drawable.bb);
        }
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentb = new Intent(contexteassy, FileGrazeActivity.class);
                intentb.putExtra("username",file.getAuthor());
                intentb.putExtra("time",file.getTime());
                contexteassy.startActivity(intentb);
            }
        });
        return convertview;
    }

    class ViewHolder{
        TextView Tname;
        TextView Tauthor;
        TextView Ttime;
        ImageView imageView;
        Button button;
        public ViewHolder(View view)
        {
            Tname =(TextView) view.findViewById(R.id.item_zyname);
            Tauthor =(TextView) view.findViewById(R.id.authorzy);
            Ttime =(TextView) view.findViewById(R.id.zy_time);
            imageView =(ImageView) view.findViewById(R.id.imgfile);
            button =(Button) view.findViewById(R.id.button_look);
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
