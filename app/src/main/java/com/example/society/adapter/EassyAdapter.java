package com.example.society.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.society.Bean.OrderBean;
import com.example.society.R;

import java.util.List;

public class EassyAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<OrderBean> list;
    public EassyAdapter(Context context, List<OrderBean> list)
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
            convertview=layoutInflater.inflate(R.layout.eassy_item_layout,null);
            viewHolder = new ViewHolder(convertview);
            convertview.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertview.getTag();
        }
        OrderBean orderBean = (OrderBean) getItem(position);
        viewHolder.tvOrderContext.setText(orderBean.getContent());
        viewHolder.tvOrderTime.setText(orderBean.getTime());
        return convertview;
    }

    class ViewHolder{
        TextView tvOrderContext;
        TextView tvOrderTime;
        public ViewHolder(View view)
        {
            tvOrderContext =(TextView) view.findViewById(R.id.item_content);
            tvOrderTime = (TextView) view.findViewById(R.id.item_time);
        }
    }
}
