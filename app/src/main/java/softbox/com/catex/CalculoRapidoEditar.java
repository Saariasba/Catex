package softbox.com.catex;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CalculoRapidoEditar extends AppCompatActivity {
    public static ArrayList<CalculoRapidoNuevoItem> listItems = null;
    public static CalculoRapidoNuevoItem nuevo = null;
    public static String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo_rapido_editar);
        getSupportActionBar().hide();// oculta la barra
        nuevo = listItems.get(CalculoRapidoNuevo.posicion);
        cargar();
        abrirActividades();
    }

    public void abrirActividades() {
        Button volver = (Button) findViewById(R.id.button);//Id del boton y crear en java
        volver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CalculoRapidoNuevo.posicion = 0;
                CalculoRapidoNuevo.bandera2 = 1;
                Intent CalculoRapidoNuevo = new Intent(getApplicationContext(), CalculoRapidoNuevo.class);//Lanzar actividad
                startActivity(CalculoRapidoNuevo);
            }
        });
        Button guardar = (Button) findViewById(R.id.button2);//Id del boton y crear en java
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculoRapidoNuevo.bandera = 1;
                Intent CalculoRapidoNuevo = new Intent(getApplicationContext(), CalculoRapidoNuevo.class);//Lanzar actividad
                startActivity(CalculoRapidoNuevo);
            }
        });
    }

    private void cargar() {
        EditText valor = (EditText) findViewById(R.id.valor);
        EditText porcentaje = (EditText) findViewById(R.id.porcentaje);
        valor.setText(nuevo.getValor());
        porcentaje.setText(nuevo.getPorcentaje());
        cambios(valor,porcentaje,nuevo);
    }

    private void cambios(final EditText valor, final EditText porcentaje, final CalculoRapidoNuevoItem item) {
        valor.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Fires right before text is changing
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Fires right after the text has changed
                item.setValor(valor.getText().toString());
            }
        });

        porcentaje.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Fires right before text is changing
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Fires right after the text has changed
               item.setPorcentaje(porcentaje.getText().toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        CalculoRapidoNuevo.posicion = 0;
        CalculoRapidoNuevo.bandera2 = 1;
        Intent CalculoRapidoNuevo = new Intent(getApplicationContext(), CalculoRapidoNuevo.class);//Lanzar actividad
        startActivity(CalculoRapidoNuevo);
    }
}
