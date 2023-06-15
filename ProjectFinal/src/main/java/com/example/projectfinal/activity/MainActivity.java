package com.example.projectfinal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.projectfinal.R;
import com.example.projectfinal.entity.Shipment;
import com.example.projectfinal.adapter.AdapterShipment;
import com.example.projectfinal.database.Database;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase database;
    final String DATABASE_NAME="finalproject";
    ListView listView;
    ArrayList <Shipment> lists;
    AdapterShipment adapterShipment;
    ImageButton btnavai;
    ImageButton btnunavai;
    ImageButton btnadd;

    ImageButton btnLanguageSwitch;
    String query="SELECT * FROM shipment sh " +
            "inner join Status s on sh.Status=s.ID " +
            "WHERE sh.Status=3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        readDataS();
    }

    private void addControls() {
        btnadd=findViewById(R.id.btnaddS);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddshipmentActivity.class);
                startActivity(intent);
            }
        });

        btnunavai=findViewById(R.id.btnUnavailabel);
        btnunavai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query="SELECT * FROM shipment sh " +
                        "inner join Status s on sh.Status=s.ID " +
                        "WHERE sh.Status=4";
                readDataS();
            }
        });
        btnavai=findViewById(R.id.btnAvailabel);
        btnavai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query="SELECT * FROM shipment sh " +
                        "inner join Status s on sh.Status=s.ID " +
                        "WHERE sh.Status=3";
                readDataS();
            }
        });
        btnLanguageSwitch = findViewById(R.id.btnLanguageSwitch);
        btnLanguageSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchLanguage();
            }
        });

        listView = findViewById(R.id.listviewship);
        lists = new ArrayList<>();
        adapterShipment = new AdapterShipment(this, lists);
        listView.setAdapter(adapterShipment);
    }

    private void switchLanguage() {
        Locale currentLocale = getResources().getConfiguration().locale;
        String currentLanguage = currentLocale.getLanguage();


        Locale newLocale;
        if (currentLanguage.equals("en")) {
            newLocale = new Locale("vi");
        } else {
            newLocale = new Locale("en");
        }

        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        conf.setLocale(newLocale);
        res.updateConfiguration(conf, res.getDisplayMetrics());

        recreate();
    }

    private void readDataS(){
        database = Database.initDatabase(this,DATABASE_NAME);
        Intent intent=getIntent();
        int sid=intent.getIntExtra("ID",-1);
        int stt=intent.getIntExtra("STT",-1);
        if(stt==4){
            ContentValues contentValues=new ContentValues();
            contentValues.put("Status",stt);
            database.update("shipment",contentValues,"ID=?", new String[]{sid+""});
        }
        Cursor cursor =database.rawQuery(query, null);
        lists.clear();
        for(int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            int id =cursor.getInt(0);
            double woc=cursor.getDouble(1);
            double totalprice=cursor.getDouble(2);
            int noinday=cursor.getInt(3);
            String date=cursor.getString(4);
            int totalpig=cursor.getInt(5);
            double price=cursor.getDouble(6);
            String status=cursor.getString(9);
            lists.add(new Shipment(id,woc,totalprice,noinday,date,totalpig,price,status));
        }
        adapterShipment.notifyDataSetChanged();
    }


}