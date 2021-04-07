package com.example.fastlogic;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Creamos las variables
    private EditText edtNombre;
    private RadioButton rbtnFacil,rbtnMedio,rbtnDificil;
    int puntos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Casting de Variables
        edtNombre = (EditText)findViewById(R.id.txtNombre);
        rbtnFacil = (RadioButton)findViewById(R.id.rbFacil);
        rbtnMedio = (RadioButton)findViewById(R.id.rbMedio);
        rbtnDificil = (RadioButton)findViewById(R.id.rbDificil);
    }

    public void Jugar(View view){
        String nombre = edtNombre.getText().toString();
        String nivel="";
        if(!nombre.equals("")){
            if(rbtnFacil.isChecked()){
                nivel = "Facil";
                puntos = 1;
            } else if(rbtnMedio.isChecked()){
                nivel = "Medio";
                puntos = 2;

            } else if(rbtnDificil.isChecked()){
                nivel = "Dificil";
                puntos = 4;
            }

            Intent inten = new Intent(this,Main2Activity_NivelFacil.class);
            inten.putExtra("usuario",nombre);
            inten.putExtra("nivel",nivel);
            inten.putExtra("puntos",puntos);
            startActivity(inten);
            finish();

        } else {//Cuando el usuario no ingreso dato
            Toast.makeText(this,"Ingrese su Nombre",Toast.LENGTH_LONG).show();//Mensaje de campo vacio
            edtNombre.requestFocus();//Definimos que variable se va a llenar
            InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtNombre,InputMethodManager.SHOW_IMPLICIT);//referenciamos el componente que se va a llenar
        }
    }

    public void Ranking(View view){
        Intent intent = new Intent(this,Main2Activity_Ranking.class);
        startActivity(intent);
    }

}
