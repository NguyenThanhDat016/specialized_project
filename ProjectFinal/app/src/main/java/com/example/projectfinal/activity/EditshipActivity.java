package com.example.projectfinal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.projectfinal.R;
import com.example.projectfinal.database.Database;

public class EditshipActivity extends AppCompatActivity {
    SQLiteDatabase database;
    EditText edtnoinday,edtwoc,edtprice;
    Button btnsave,btncancel;
    final String DATABASE_NAME="finalproject";
    int id=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editship);

        addControls();
        addEvents();
        initUI();
    }
    private void addEvents(){
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
                update();
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }
    private void initUI() {
        Intent intent=getIntent();
        id=intent.getIntExtra("ID",-1);
        SQLiteDatabase database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor =database.rawQuery("SELECT * FROM shipment WHERE ID = ?", new String[]{id + ""});
        cursor.moveToFirst();
        String woc=cursor.getString(1);
        String noinday=cursor.getString(3);
        String price=cursor.getString(6);

        edtnoinday.setText(noinday);
        edtwoc.setText(woc);
        edtprice.setText(price);
    }
    private void addControls() {
        edtnoinday=findViewById(R.id.edtinnoinday);
        edtprice=findViewById(R.id.edtinprice);
        edtwoc=findViewById(R.id.edtinwoc);
        btnsave=findViewById(R.id.btninsave);
        btncancel=findViewById(R.id.btnincancel);
    }
    private void update(){
        String noinday=edtnoinday.getText().toString();
        String price=edtprice.getText().toString();
        String woc=edtwoc.getText().toString();
        String totalpig=getTotalPig(id);
        String totalprice=getTotalPrice(id);

        ContentValues contentValues=new ContentValues();
        contentValues.put("Noinday",noinday);
        contentValues.put("Price",price);
        contentValues.put("WoC",woc);
        contentValues.put("Status",3);
        contentValues.put("Totalpig",totalpig);
        contentValues.put("Totalprice",totalprice);

        SQLiteDatabase database=Database.initDatabase(this,DATABASE_NAME);
        database.update("shipment",contentValues,"ID=?", new String[]{id+""});
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    private void cancel(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public String getTotalPrice(int sid){
        database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor=database.rawQuery("SELECT s.Price*(SUM(p.Weight)-s.WoC*COUNT(p.PigNo)) as ttprice " +
                "FROM shipment s inner join pig p on s.ID=p.ShipID " +
                "WHERE s.ID=?",new String[]{sid + ""});
        cursor.moveToFirst();
        double ttprice=Math.round(cursor.getDouble(0)*10)/10;
        return String.valueOf(ttprice);
    }

    public String getTotalPig(int sid){
        database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor=database.rawQuery("SELECT COUNT(PigNo) " +
                "FROM pig " +
                "WHERE ShipID=?",new String[]{sid + ""});
        cursor.moveToFirst();
        int ttpig=cursor.getInt(0);
        return String.valueOf(ttpig);
    }
}