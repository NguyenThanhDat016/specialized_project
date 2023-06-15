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

import com.example.projectfinal.R;
import com.example.projectfinal.database.Database;

public class EditpigActivity extends AppCompatActivity {

    final String DATABASE_NAME="finalproject";
    EditText edtpigno,edtheight;
    ImageButton btnsave,btncancel;
    int nop=-1,sid=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpig);

        addControls();
        addEvents();
        initUI();
    }
    private void addEvents(){
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        nop=intent.getIntExtra("NOP",-1);
        sid=intent.getIntExtra("SID",-1);
        SQLiteDatabase database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor =database.rawQuery("SELECT * FROM pig WHERE PigNo = ? AND ShipID=?", new String[]{nop + "",sid+""});
        cursor.moveToFirst();
        String pigno=cursor.getString(0);
        String height=cursor.getString(2);

        edtpigno.setText(pigno);
        edtheight.setText(height);
    }
    private void addControls() {
        edtheight=findViewById(R.id.editheight);
        edtpigno=findViewById(R.id.editpigno);
        btncancel=findViewById(R.id.btncancelP);
        btnsave=findViewById(R.id.btnsaveP);
    }
    private void update(){
        String pigno=edtpigno.getText().toString();
        String height=edtheight.getText().toString();

        ContentValues contentValues=new ContentValues();
        contentValues.put("PigNo",pigno);
        contentValues.put("Weight",height);
        contentValues.put("StatusID",1);

        SQLiteDatabase database=Database.initDatabase(this,DATABASE_NAME);
        database.update("pig",contentValues,"PigNo=? AND ShipID=?", new String[]{nop+"",sid+""});
        Intent intent=new Intent(this,PigActivity.class);
        intent.putExtra("ID",sid);
        startActivity(intent);
    }
    private void cancel(){
        Intent intent=new Intent(this,PigActivity.class);
        intent.putExtra("ID",sid);
        startActivity(intent);
    }
}