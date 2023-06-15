package com.example.projectfinal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectfinal.R;
import com.example.projectfinal.database.Database;

public class AddshipmentActivity extends AppCompatActivity {

    final String DATABASE_NAME="finalproject";
    Button btnsave,btncancel;
    TextView edtnoinday,edtwoc,edtprice;
    int id=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addshipment);
        addControls();
        addEvents();
    }

    private void addEvents(){
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }

    private void addControls() {
        edtnoinday=findViewById(R.id.edtinnoinday);
        edtprice=findViewById(R.id.edtinprice);
        edtwoc=findViewById(R.id.edtinwoc);
        btnsave=findViewById(R.id.btninsave);
        btncancel=findViewById(R.id.btnincancel);
    }
    private void insert(){
        String noinday=edtnoinday.getText().toString();
        String price=edtprice.getText().toString();
        String woc=edtwoc.getText().toString();

        ContentValues contentValues=new ContentValues();
        contentValues.put("Noinday",noinday);
        contentValues.put("Price",price);
        contentValues.put("WoC",woc);
        contentValues.put("Status",3);
        contentValues.put("Date",getDate());

        SQLiteDatabase database=Database.initDatabase(this,DATABASE_NAME);
        database.insert("shipment",null,contentValues);
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    private void cancel(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public String getDate(){
        String date= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = String.valueOf(java.time.LocalDate.now());
        }
        return date;
    }

}