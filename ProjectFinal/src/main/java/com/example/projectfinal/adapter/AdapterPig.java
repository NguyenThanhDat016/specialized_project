package com.example.projectfinal.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectfinal.activity.EditpigActivity;
import com.example.projectfinal.activity.PigActivity;
import com.example.projectfinal.database.Database;
import com.example.projectfinal.entity.Pig;
import com.example.projectfinal.R;

import java.util.ArrayList;

public class AdapterPig extends BaseAdapter {
    Activity context;
    ArrayList<Pig> list;

    public AdapterPig(Activity context, ArrayList<Pig> list) {
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
        View row=inflater.inflate(R.layout.listpig_row,null);
        TextView txtid= row.findViewById(R.id.txtpigno);
        TextView txtship=row.findViewById(R.id.txtshipid);
        TextView txtheight= row.findViewById(R.id.txtweight);
        TextView txtstatus= row.findViewById(R.id.txtstatusP);
        Button btnedit=row.findViewById(R.id.btneditP);
        Button btnkilled=row.findViewById(R.id.btnkilled);

        Pig pig=list.get(position);
        txtid.setText(pig.PigNo+"");
        txtship.setText(pig.ShipID+"");
        txtheight.setText(pig.Weight+"");
        txtstatus.setText(pig.Status+"");

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, EditpigActivity.class);
                intent.putExtra("NOP",pig.PigNo);
                intent.putExtra("SID",pig.ShipID);
                context.startActivity(intent);
            }
        });

        btnkilled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,PigActivity.class);
                intent.putExtra("NOP",pig.PigNo);
                intent.putExtra("ID",pig.ShipID);
                intent.putExtra("STT",2);
                context.startActivity(intent);
            }
        });

        return row;
    }
}
