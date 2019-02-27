package softbox.com.catex;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Redes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redes);
        getSupportActionBar().hide();// oculta la barra
        revision();
        abrirActividades();
    }

    private void revision() {
        if(esNulo(Inicio.ID)){
            Intent logIn = new Intent(getApplicationContext(), InicioLogIn.class);//Lanzar actividad
            logIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(logIn);//Iniciar Actividad
        }
    }

    public void abrirActividades() {

        ImageButton BotonCa = (ImageButton) findViewById(R.id.imageButton);//Id del boton y crear en java
        BotonCa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.instagram.com/softbox_technology/?hl=es-la");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);                                       //Iniciar Actividad
            }
        });

        ImageButton BotonM = (ImageButton) findViewById(R.id.imageButton4);//Id del boton y crear en java
        BotonM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.fb.com/SoftBox-Technology-281202349255788");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        ImageButton BotonR = (ImageButton) findViewById(R.id.imageButton7);//Id del boton y crear en java
        BotonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=softbox.com.catex");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        Button volver = (Button) findViewById(R.id.button);//Id del boton y crear en java
        volver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent Inicio = new Intent(getApplicationContext(), Inicio.class);//Lanzar actividad
                startActivity(Inicio);
            }
        });
    }

    /////Función Para Preguntar si un String es NULO/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static boolean esNulo(String s){
        return (s==null || s.trim().equals(""));
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
