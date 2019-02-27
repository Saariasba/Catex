package softbox.com.catex;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CalculoRapido extends AppCompatActivity {
    private AdView mAdView;

    public ListView items;
    public static AdaptadorCalculoRapido adaptador;
    public static ArrayList<CalculoRapidoItem> listItems;

    public static String documentoCargado = null;

    public static ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo_rapido);
        progressDialog= new ProgressDialog(this);
        getSupportActionBar().hide();// oculta la barra
        revision();
        progressDialog.setMessage("Cargando");
        progressDialog.show();
        progressDialog.setCancelable(false);
        lista();
        cargar();
        abrirActividades();
        cargarPublicidad();
        listaVacia();
    }

    private void revision() {
        if(esNulo(Inicio.ID)){
            Intent logIn = new Intent(getApplicationContext(), InicioLogIn.class);//Lanzar actividad
            logIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(logIn);//Iniciar Actividad
        }
    }

    private void cargarPublicidad() {
        mAdView = findViewById(R.id.adCalculoRapido);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void listaVacia() {
        TextView vacio = findViewById(R.id.vacio);
        items.setEmptyView(vacio);
    }

    /////Función Cargar Datos////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void cargar() {
        CollectionReference referencia = Inicio.db.collection("usuarios")
                .document(Inicio.ID)
                .collection("historias")
                .document(String.valueOf(Inicio.id))
                .collection("periodos")
                .document(Inicio.periodo + " "+ Inicio.idP)
                .collection("Calculos");
        referencia.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot document : task.getResult()) {
                                Double total = (Double) document.get("total");
                                CalculoRapidoItem nuevo = new CalculoRapidoItem(String.valueOf(total),document.getId());
                                listItems.add(nuevo);
                                adaptador.notifyDataSetChanged();
                            }
                            progressDialog.cancel();
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void lista() {
        listItems = new ArrayList<CalculoRapidoItem>();
        items = (ListView) findViewById(R.id.listaCalculoRapido);
        adaptador = new AdaptadorCalculoRapido(this,listItems);
        items.setAdapter(adaptador);
    }

    public void abrirActividades() {
        FloatingActionButton mas = (FloatingActionButton) findViewById(R.id.mas);
        mas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent nuevo = new Intent(getApplicationContext(), CalculoRapidoNuevo.class);//Lanzar actividad
                startActivity(nuevo);
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

    @Override
    public void onBackPressed() {
        Intent Inicio = new Intent(getApplicationContext(), Inicio.class);//Lanzar actividad
        startActivity(Inicio);
    }

    /////Función Para Preguntar si un String es NULO/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static boolean esNulo(String s){
        return (s==null || s.trim().equals(""));
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
