package com.example.society.adapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.society.Activity.ChangePwdActivity;
import com.example.society.Activity.EassyActivity;
import com.example.society.Bean.Eassy;
import com.example.society.R;

import java.io.File;
import java.util.List;

public class EassyAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Eassy> list;
    Context contexteassy;
    public EassyAdapter(Context context, List<Eassy> list)
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
            convertview=layoutInflater.inflate(R.layout.eassy_item_layout,null);
            viewHolder = new ViewHolder(convertview);
            convertview.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertview.getTag();
        }
        Eassy eassy = (Eassy) getItem(position);
        Log.d("Easssy： ", " " + eassy.toString() + "\n");
        viewHolder.tvEassyContext.setText(eassy.getContent());
        viewHolder.tvEassyTime.setText(eassy.getTime());
        Log.d("ImgActivity ", ""+eassy.getImgpath());
        if(eassy.getImgpath() != null)
        {
            Log.d("ImgActivity ", "getImgFromDesc: adsasdajhds！");
            Bitmap bitmap = getImgFromDesc(eassy.getImgpath());
            viewHolder.eassyimg.setImageBitmap(bitmap);
        }
        Log.d("ImgActivity ", "getImgFromDesc: adsasdajhds！");
        /*
      else
        {
            viewHolder.eassyimg.setImageResource(R.drawable.bb);
        }
        */

        viewHolder.tvEassyaut.setText("作者："+eassy.getAuthor());
        Log.d("作者 ", ""+eassy.getAuthor());
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
        TextView tvEassyContext;
        TextView tvEassyTime;
        ImageView eassyimg;
        TextView tvEassyaut;
        public ViewHolder(View view)
        {
            tvEassyContext =(TextView) view.findViewById(R.id.item_content);
            tvEassyaut = (TextView) view.findViewById(R.id.author);
            tvEassyTime = (TextView) view.findViewById(R.id.item_time);
            eassyimg = (ImageView) view.findViewById(R.id.imgeass);
        }
    }
}
