package com.quynhlm.dev.lab8.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quynhlm.dev.lab8.Model.Province;
import com.quynhlm.dev.lab8.R;

import java.util.ArrayList;

public class Adapter_Item_Province_Select_GHN extends BaseAdapter {
    Context context;
    ArrayList<Province> list;

    public Adapter_Item_Province_Select_GHN(Context context, ArrayList<Province> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_province_spinner,parent,false);
        TextView tv_province = convertView.findViewById(R.id.textViewProvinceName);
        Province province = list.get(position);
        tv_province.setText(province.getProvinceName());
        return convertView;
    }
}
