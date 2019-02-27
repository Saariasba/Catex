package softbox.com.catex;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Materias extends AppCompatActivity {
    private AdView mAdView;

    public ListView items;
    public static AdaptadorMaterias adaptador;
    public static ArrayList<MateriasItem> listItems;

    public static String documentoCargado = null;

    public static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materias);
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

    /////Función Cargar Datos////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void cargar() {
        CollectionReference referencia = Inicio.db.collection("usuarios")
                .document(Inicio.ID)
                .collection("historias")
                .document(String.valueOf(Inicio.id))
                .collection("periodos")
                .document(Inicio.periodo + " "+ Inicio.idP)
                .collection("Materias");
        referencia.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot document : task.getResult()) {
                                final String n = document.getId();
                                DocumentReference referencia = Inicio.db.collection("usuarios")
                                        .document(Inicio.ID)
                                        .collection("historias")
                                        .document(String.valueOf(Inicio.id))
                                        .collection("periodos")
                                        .document(Inicio.periodo + " "+ Inicio.idP)
                                        .collection("Materias")
                                        .document(n)
                                        .collection("notas")
                                        .document("notas"); // Crear la referencia del Documento
                                referencia.get() //Obtener Documento de referencia
                                        .addOnCompleteListener(new OnCompleteListener < DocumentSnapshot > () {
                                            @Override
                                            public void onComplete(@NonNull Task < DocumentSnapshot > task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot doc = task.getResult(); //Obtener el documento
                                                    Double total = (Double) doc.get("total");
                                                    if(String.valueOf(total) == "null"){
                                                        total = 0.0;
                                                        MateriasItem nuevo = new MateriasItem(String.valueOf(total),n);
                                                        listItems.add(nuevo);
                                                        adaptador.notifyDataSetChanged();
                                                    }else{
                                                        MateriasItem nuevo = new MateriasItem(String.valueOf(total),n);
                                                        listItems.add(nuevo);
                                                        adaptador.notifyDataSetChanged();
                                                    }
                                                }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() { //Respuesta en caso de no funcipnar el Listener
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Error","No such document!");
                                            }
                                        });
                            }
                            progressDialog.cancel();
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void cargarPublicidad() {
        mAdView = findViewById(R.id.adMaterias);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void listaVacia() {
        TextView vacio = findViewById(R.id.vacio);
        items.setEmptyView(vacio);
    }

    private void lista() {
        listItems = new ArrayList<MateriasItem>();
        items = (ListView) findViewById(R.id.listaMaterias);
        adaptador = new AdaptadorMaterias(this,listItems);
        items.setAdapter(adaptador);
    }

    public void abrirActividades() {
        FloatingActionButton mas = (FloatingActionButton) findViewById(R.id.mas);
        mas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setCancelable(false);
                Intent nuevo = new Intent(getApplicationContext(),MateriasNuevo.class);//Lanzar actividad
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

    /////Función Para Preguntar si un String es NULO/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static boolean esNulo(String s){
        return (s==null || s.trim().equals(""));
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
