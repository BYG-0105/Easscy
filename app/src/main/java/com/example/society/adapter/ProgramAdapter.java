package com.example.society.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.society.Bean.Programming;
import com.example.society.R;
import java.util.List;

public class ProgramAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Programming> list;

    public ProgramAdapter(Context context, List<Programming> list)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.list=list;

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
            convertview=layoutInflater.inflate(R.layout.program_item,null);
            viewHolder = new ViewHolder(convertview);
            convertview.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertview.getTag();
        }
        Programming programming = (Programming) getItem(position);
        viewHolder.proname.setText(programming.getProname());

        return convertview;
    }

    class ViewHolder{
        TextView proname;

        public ViewHolder(View view)
        {
            proname =(TextView) view.findViewById(R.id.proname);

        }
    }
}
