package com.example.fastlogic;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity_Ranking extends AppCompatActivity {

    private TextView[] nombre = new TextView [3];
    private TextView[] score = new TextView [3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2__ranking);

        nombre[0] = (TextView)findViewById(R.id.txtnombre1);
        nombre[1] = (TextView)findViewById(R.id.txtnombre2);
        nombre[2] = (TextView)findViewById(R.id.txtnombre3);

        score[0] = (TextView)findViewById(R.id.txtscore1);
        score[1] = (TextView)findViewById(R.id.txtscore2);
        score[2] = (TextView)findViewById(R.id.txtscore3);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"BD",null,1);
        SQLiteDatabase BD = admin.getWritableDatabase();
        Cursor consulta = BD.rawQuery("select * from usuario  order by (puntaje) desc",null);
        int i=0;
        while(consulta.moveToNext()){
            String BDnombre1 = consulta.getString(0);
            String BDpuntaje1 = consulta.getString(1);

            nombre[i].setText(BDnombre1);
            score[i].setText(BDpuntaje1);
            System.out.println("i : "+i);
            System.out.println("nombre["+i+"] : "+BDnombre1);
            System.out.println("score["+i+"] : "+BDpuntaje1);
            i++;
            if(i==3){
                break;
            }
        }
        i=0;

        BD.close();


    }
}
