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

public class MateriasCalculoEditar extends AppCompatActivity {
    public static ArrayList<MateriasCalculoItem> listItems = null;
    public static MateriasCalculoItem nuevo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materias_calculo_editar);
        getSupportActionBar().hide();// oculta la barra
        nuevo = listItems.get(MateriasCalculo.posicion);
        cargar();
        abrirActividades();
    }

    public void abrirActividades() {
        Button volver = (Button) findViewById(R.id.button);//Id del boton y crear en java
        volver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MateriasCalculo.posicion = 0;
                MateriasCalculo.bandera2 = 1;
                Intent MateriasCalculo = new Intent(getApplicationContext(), MateriasCalculo.class);//Lanzar actividad
                startActivity(MateriasCalculo);
            }
        });
        Button guardar = (Button) findViewById(R.id.button2);//Id del boton y crear en java
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MateriasCalculo.bandera = 1;
                Intent MateriasCalculo = new Intent(getApplicationContext(), MateriasCalculo.class);//Lanzar actividad
                startActivity(MateriasCalculo);
            }
        });
    }

    private void cargar() {
        EditText valor = (EditText) findViewById(R.id.valor);
        EditText porcentaje = (EditText) findViewById(R.id.porcentaje);
        EditText nombre = (EditText) findViewById(R.id.nombre);
        valor.setText(nuevo.getValor());
        porcentaje.setText(nuevo.getPorcentaje());
        nombre.setText(nuevo.getTitulo());
        cambios(valor,porcentaje,nombre,nuevo);
    }

    private void cambios(final EditText valor, final EditText porcentaje,final EditText nombre, final MateriasCalculoItem item) {
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
        nombre.addTextChangedListener(new TextWatcher() {

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
                item.setTitulo(nombre.getText().toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        MateriasCalculo.posicion = 0;
        MateriasCalculo.bandera2 = 1;
        Intent MateriasCalculo = new Intent(getApplicationContext(), MateriasCalculo.class);//Lanzar actividad
        startActivity(MateriasCalculo);
    }
}
