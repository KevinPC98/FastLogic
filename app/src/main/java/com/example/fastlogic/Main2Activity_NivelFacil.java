package com.example.fastlogic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity_NivelFacil extends AppCompatActivity {

    private TextView txtvwNombre,txtvwNivel,txtvwPuntaje,txtvwRespuesta,txtvwTiempo;
    private ImageView imgvwEjercicio;
    private Button btnAyuda;
    String nombreUsuario,nivel,stringayuda;
    int numAleatorio,puntaje=0,tiempo=60000,ayuda=3;
    int puntos;
    String EjercicioFacil[] = {"f1","f2","f3","f4","f5","f6","f7","f8","f9","f10"};
    String ResultadoFacil[] = {"30","2","40","28","42","15","3","222","25","32"};
    String EjercicioMedio[] = {"m1","m2","m3","m4","m5","m6","m7","m8","m9","m10"};
    String ResultadoMedio[] = {"100","125","1","24","72","5","1","0","1","68"};
    String EjercicioDificil[] = {"d1","d2","d3","d4","d5","d6","d7","d8","d9","d10"};
    String ResultadoDificil[] = {"10","40","2","6","1","1","1","6","0","2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2__nivel_facil);
        txtvwNombre = (TextView)findViewById(R.id.textViewNombreP);
        txtvwNivel = (TextView)findViewById(R.id.textViewNivelP);
        txtvwPuntaje = (TextView)findViewById(R.id.textViewPuntajeP);
        txtvwRespuesta = (TextView)findViewById(R.id.textViewRespuesta);
        txtvwTiempo = (TextView)findViewById(R.id.textViewTiempo);
        imgvwEjercicio = (ImageView)findViewById(R.id.imageViewEjercicio);
        btnAyuda = (Button)findViewById(R.id.buttonAyuda);

        puntos = getIntent().getIntExtra("puntos",0);
        nombreUsuario = getIntent().getStringExtra("usuario");
        txtvwNombre.setText("Jugador : "+nombreUsuario);
        nivel = getIntent().getStringExtra("nivel");
        txtvwNivel.setText("Nivel : "+nivel);
        txtvwPuntaje.setText("Puntaje : "+puntaje);
        txtvwTiempo.setText("Tiempo: "+tiempo);
        stringayuda = String.valueOf(ayuda);
        btnAyuda.setText(stringayuda);

        GenerarImagen();
        runThread();
    }

    private void runThread(){
        new Thread() {
            public void run() {
                while (tiempo!=0) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtvwTiempo.setText("Tiempo: "+tiempo/1000);
                                tiempo=tiempo-1000;
                                if(tiempo==0){
                                    txtvwTiempo.setText("Tiempo: "+tiempo/1000);
                                    ActualizarBD();
                                    VentanaPuntaje();
                                }
                            }
                        });
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("ERROR: "+e);
                    }
                }
            }
        }.start();
    }

    public void GenerarImagen(){
        numAleatorio = (int)(Math.random()*10);
        int id=0;
        System.out.println("Alazr= "+numAleatorio);
        if(nivel.equals("Facil")){
            id = getResources().getIdentifier(EjercicioFacil[numAleatorio],"drawable",getPackageName());
        } else if(nivel.equals("Medio")){
            id = getResources().getIdentifier(EjercicioMedio[numAleatorio],"drawable",getPackageName());
        } else if(nivel.equals("Dificil")){
            id = getResources().getIdentifier(EjercicioDificil[numAleatorio],"drawable",getPackageName());
        }
        imgvwEjercicio.setImageResource(id);
    }

    public void Escribir(View view){
        int ID = view.getId();
        Button btn = (Button) findViewById(ID);
        String rpta = txtvwRespuesta.getText().toString()+""+btn.getText().toString();
        txtvwRespuesta.setText(rpta);
    }

    public void Limpiar(View view){
        txtvwRespuesta.setText("");
    }

    public void ValidarRespuesta(View view){
        System.out.println("HOLA");
        System.out.println("pts: "+puntos);
        System.out.println("ptj: "+puntaje);
        if(nivel.equals("Facil")){
            ActualizarPuntaje(ResultadoFacil);
        } else if(nivel.equals("Medio")){
            ActualizarPuntaje(ResultadoMedio);

        } else if(nivel.equals("Dificil")){
            ActualizarPuntaje(ResultadoDificil);
        }

    }

    public void ActualizarPuntaje(String Resultado[]){
        String rpta = txtvwRespuesta.getText().toString();
        System.out.println("RPTA: "+rpta);
        if(rpta.equals(Resultado[numAleatorio])){
            System.out.println("ENTRO");
            puntaje = puntos+puntaje;
            System.out.println("PTJ: "+puntaje);
            txtvwPuntaje.setText("Puntaje: "+puntaje);
            System.out.println("TODO CORRECTO");
            txtvwRespuesta.setText("");
            GenerarImagen();
        } else {
            txtvwRespuesta.setText("");
            Toast.makeText(this,"ERROR",Toast.LENGTH_SHORT).show();
        }
    }

    private void ActualizarBD(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"BD",null,1);
        SQLiteDatabase BD = admin.getWritableDatabase();
        ContentValues insertar = new ContentValues();
        insertar.put("nombre",nombreUsuario);
        insertar.put("puntaje",puntaje);
        BD.insert("usuario",null,insertar);
        BD.close();
    }

    public void ToMenu(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void Ayudar(View view){
        ayuda--;
        stringayuda = String.valueOf(ayuda);
        btnAyuda.setText(stringayuda);
        if(ayuda!=0){
            GenerarImagen();
        } else {
            btnAyuda.setEnabled(false);
        }
    }

    private void VentanaPuntaje(){
        final android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.ventanapuntaje,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView txtnombre = view.findViewById(R.id.textViewNombreP);
        txtnombre.setText("Nombre: "+nombreUsuario);
        TextView txtnivel = view.findViewById(R.id.textViewNivelP);
        txtnivel.setText("Nivel: "+nivel);
        TextView txtpuntaje = view.findViewById(R.id.textViewPuntajeP);
        txtpuntaje.setText("Puntaje: "+puntaje);
        Button btnMenu = view.findViewById(R.id.buttonToMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToMenu();
            }
        });
/*
        builder.setView(getLayoutInflater().inflate(R.layout.ventanapuntaje,null));



        builder.setTitle("Puntaje");
        builder.setMessage("Jugador: "+nombreUsuario+"\n"
                            +"Nivel: "+nivel+"\n"
                            +"Puntaje: "+puntaje);
        builder.setPositiveButton("Regresar a Menu", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToMenu();
            }
        });
        builder.show();*/
    }
}
