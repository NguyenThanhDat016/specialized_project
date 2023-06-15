package com.example.projectfinal.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectfinal.R;
import com.example.projectfinal.activity.EditpigActivity;
import com.example.projectfinal.activity.EditshipActivity;
import com.example.projectfinal.activity.MainActivity;
import com.example.projectfinal.activity.PigActivity;
import com.example.projectfinal.entity.Shipment;

import java.util.ArrayList;

public class AdapterShipment extends BaseAdapter {

    Activity context;
    ArrayList<Shipment> list;

    public AdapterShipment(Activity context, ArrayList<Shipment> list) {
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
        View row=inflater.inflate(R.layout.listship_row,null);
        TextView txtid= row.findViewById(R.id.txtIDS);
        TextView txtwoc= row.findViewById(R.id.txtWoC);
        TextView txttotalprice= row.findViewById(R.id.txtTotalprice);
        TextView txtnoinday= row.findViewById(R.id.txtNoinday);
        TextView txtdate= row.findViewById(R.id.txtDate);
        TextView txttotalpig= row.findViewById(R.id.txtTotalpig);
        TextView txtprice= row.findViewById(R.id.txtPrice);
        TextView txtstatus=row.findViewById(R.id.txtStatusS);
        Button btnedit=row.findViewById(R.id.btneditS);
        Button btndelete=row.findViewById(R.id.btndeleteS);
        Button btnchange=row.findViewById(R.id.btnchangeS);

        Shipment shipment=list.get(position);
        txtid.setText(shipment.ID+"");
        txtwoc.setText(shipment.WoC+"");
        txttotalprice.setText(shipment.TotalPrice+"");
        txttotalpig.setText(shipment.TotalPig+"");
        txtnoinday.setText(shipment.NoinDay+"");
        txtdate.setText(shipment.Date+"");
        txtprice.setText(shipment.Price+"");
        txtstatus.setText(shipment.Status+"");

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, EditshipActivity.class);
                intent.putExtra("ID",shipment.ID);
                context.startActivity(intent);
            }
        });

        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PigActivity.class);
                intent.putExtra("ID",shipment.ID);
                context.startActivity(intent);
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, MainActivity.class);
                intent.putExtra("ID",shipment.ID);
                intent.putExtra("STT",4);
                context.startActivity(intent);
            }
        });
        return row;
    }
}
