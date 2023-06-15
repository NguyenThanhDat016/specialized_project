package com.example.projectfinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.projectfinal.R;
import com.example.projectfinal.entity.Pig;
import java.util.ArrayList;

public class AdapterAddPig extends BaseAdapter {
    Context context;
    ArrayList<Pig> list;

    public AdapterAddPig(Context context, ArrayList<Pig> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.listaddpig_row,null);
        TextView txtpigno= row.findViewById(R.id.txtpigadd);
        TextView txtheight=row.findViewById(R.id.txtaddheight);

        Pig pig=list.get(position);
        txtpigno.setText(pig.PigNo+"");
        txtheight.setText(pig.Weight+"");
        return row;
    }
}
