package com.example.projectfinal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.projectfinal.R;
import com.example.projectfinal.adapter.AdapterPig;
import com.example.projectfinal.adapter.AdapterShipment;
import com.example.projectfinal.database.Database;
import com.example.projectfinal.entity.Pig;
import com.example.projectfinal.entity.Shipment;

import java.util.ArrayList;

public class PigActivity extends AppCompatActivity {

    SQLiteDatabase database;
    final String DATABASE_NAME="finalproject";
    ListView listView;
    ArrayList<Pig> lists;
    AdapterPig adapterPig;
    Button btninstock;
    Button btnkilled;
    Button btnadd;
    Button btnreturn;
    String query= "SELECT * FROM pig p " +
                    "inner join Status s on p.StatusID=s.ID " +
                    "WHERE p.ShipID=? AND p.StatusID=1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pig);

        addControls();
        readDataP();
    }

    private void addControls() {
        btnadd=findViewById(R.id.btnaddP);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intents=getIntent();
                int sid=intents.getIntExtra("ID",-1);
                Intent intent=new Intent(PigActivity.this, AddpigActivity.class);
                intent.putExtra("SID",sid);
                startActivity(intent);
            }
        });

        btnreturn=findViewById(R.id.btnship);
        btnreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PigActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btninstock=findViewById(R.id.btninstock);
        btninstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query="SELECT * FROM pig p " +
                        "inner join Status s on p.StatusID=s.ID " +
                        "WHERE p.ShipID=? AND p.StatusID=1";
                readDataP();
            }
        });

        btnkilled=findViewById(R.id.btnkilledP);
        btnkilled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query="SELECT * FROM pig p " +
                        "inner join Status s on p.StatusID=s.ID " +
                        "WHERE p.ShipID=? AND p.StatusID=2";
                readDataP();
            }
        });
        listView =findViewById(R.id.listviewpig);
        lists=new ArrayList<>();
        adapterPig =new AdapterPig(this,lists);
        listView.setAdapter(adapterPig);
    }

    private void readDataP(){
        Intent intent=getIntent();
        int sid=intent.getIntExtra("ID",-1);
        int stt=intent.getIntExtra("STT",-1);
        int nop=intent.getIntExtra("NOP",-1);
        database = Database.initDatabase(this,DATABASE_NAME);
        if(stt==2){
            ContentValues contentValues=new ContentValues();
            contentValues.put("StatusID",stt);
            database.update("pig",contentValues,"PigNo=? AND ShipID=?", new String[]{nop+"",sid+""});
        }
        Cursor cursor =database.rawQuery(query, new String[]{sid + ""});
        lists.clear();
        for(int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            int pigno =cursor.getInt(0);
            String status=cursor.getString(5);
            double weight=cursor.getDouble(2);
            int shipid=cursor.getInt(1);
            lists.add(new Pig(pigno,status,weight,shipid));
        }
        adapterPig.notifyDataSetChanged();
        cursor.close();
    }
}