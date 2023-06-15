package com.example.projectfinal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectfinal.R;
import com.example.projectfinal.adapter.AdapterAddPig;
import com.example.projectfinal.adapter.AdapterShipment;
import com.example.projectfinal.database.Database;
import com.example.projectfinal.entity.Pig;
import com.example.projectfinal.entity.Shipment;

import java.util.ArrayList;

public class AddpigActivity extends AppCompatActivity {
    SQLiteDatabase database;
    final String DATABASE_NAME="finalproject";
    ListView listView;
    ArrayList<Pig> lists;
    AdapterAddPig adapterAddPig;
    ImageButton btnadd,btnback;
    EditText edtpigno,edtheight;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpig);

        addControls();
        readDataS();
    }


    private void addControls() {
        listView =findViewById(R.id.listaddpig);
        btnback=findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        btnadd=findViewById(R.id.btnaddpig);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });

        edtpigno=findViewById(R.id.edtinpigno);
        edtheight=findViewById(R.id.edtinheight);
        lists=new ArrayList<>();
        adapterAddPig =new AdapterAddPig(this,lists);
        listView.setAdapter(adapterAddPig);
    }

    private void back(){
        Intent intent=new Intent(AddpigActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private void insert(){
        String pigno=edtpigno.getText().toString();
        String height=edtheight.getText().toString();
        Intent intents=getIntent();
        id=intents.getIntExtra("SID",-1);

        ContentValues contentValues=new ContentValues();
        contentValues.put("PigNo",pigno);
        contentValues.put("Weight",height);
        contentValues.put("StatusID",1);
        contentValues.put("ShipID",id);

        SQLiteDatabase database=Database.initDatabase(this,DATABASE_NAME);
        database.insert("pig",null,contentValues);

        Intent intent=new Intent(this,AddpigActivity.class);
        intent.putExtra("SID",id);
        startActivity(intent);
    }

    private void readDataS() {
        Intent intent=getIntent();
        int sid=intent.getIntExtra("SID",-1);
        database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor =database.rawQuery("SELECT * FROM pig p " +
                                                "inner join status s on p.StatusID=s.ID " +
                                                "WHERE p.ShipID=?", new String[]{sid + ""});
        lists.clear();
        for(int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            int pigno=cursor.getInt(0);
            double height=cursor.getDouble(2);
            String status=cursor.getString(5);
            int shipid=cursor.getInt(1);
            lists.add(new Pig(pigno,status,height,shipid));
        }
        adapterAddPig.notifyDataSetChanged();
    }
}