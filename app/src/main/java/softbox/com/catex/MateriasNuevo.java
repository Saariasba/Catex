package softbox.com.catex;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MateriasNuevo extends AppCompatActivity {

    FirebaseFirestore db;
    private AdView mAdView;

    public ListView items;
    private AdaptadorMateriasNuevo adaptador;
    public static ArrayList<MateriasNuevoItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materias_nuevo);
        getSupportActionBar().hide();// oculta la barra
        cargar();
        abrirActividades();
        cargarAds(); //Carga la publicidad
    }

    /////Función Cargar Datos////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void cargar() {
        if(esNulo(Materias.documentoCargado)){
            Materias.progressDialog.cancel();
        }else{
            DocumentReference referencia = Inicio.db.collection("usuarios")
                    .document(Inicio.ID)
                    .collection("historias")
                    .document(String.valueOf(Inicio.id))
                    .collection("periodos")
                    .document(Inicio.periodo + " "+ Inicio.idP)
                    .collection("Materias")
                    .document(Materias.documentoCargado);
            referencia.get() //Obtener Documento de referencia
                    .addOnCompleteListener(new OnCompleteListener< DocumentSnapshot >() {
                        @Override
                        public void onComplete(@NonNull Task< DocumentSnapshot > task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot doc = task.getResult(); //Obtener el documento
                                if (doc.exists()) { //Saber si existe el documento
                                    ////Cargar información////
                                    EditText nombre = findViewById(R.id.editText);
                                    EditText nombre2 = findViewById(R.id.editText2);
                                    EditText nombre3 = findViewById(R.id.editText3);
                                    String nombreP = (String) doc.get("nombreProfesor");
                                    String descripcion = (String) doc.get("descripcion");
                                    nombre.setText(Materias.documentoCargado);
                                    nombre2.setText(nombreP);
                                    nombre3.setText(descripcion);
                                } else {

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
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void cargarAds() {
        MobileAds.initialize(this, "ca-app-pub-7328406967236626~7788304617"); //Inicializar publicidad
        mAdView = findViewById(R.id.adMateriasNuevo); //Enlace de AdView por ID
        AdRequest adRequest = new AdRequest.Builder().build(); //hacer el request de Ads
        mAdView.loadAd(adRequest); //Cargar la respuesta de la solicitud en el AdView
    }

    public void abrirActividades() {
        Button volver = (Button) findViewById(R.id.button);//Id del boton y crear en java
        volver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Materias.documentoCargado=null;
                Intent Materias = new Intent(getApplicationContext(), Materias.class);//Lanzar actividad
                startActivity(Materias);
            }
        });

        Button guardar = (Button) findViewById(R.id.button2);//Id del boton y crear en java
        guardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final EditText nombreE = findViewById(R.id.editText);
                if (esNulo(Materias.documentoCargado)){
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MateriasNuevo.this);
                    dialogo1.setTitle("Importante");
                    dialogo1.setMessage("¿ Deseas guardar esta Materia ?");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            if(nombreE.getText().toString().length() == 0){
                                Toast toast1 = Toast.makeText(getApplicationContext(), "Llena el nombre de la materia", Toast.LENGTH_SHORT);
                                toast1.show();
                            }else{
                                String nombre = nombreE.getText().toString();
                                guardar(nombre);
                                Intent Materia = new Intent(getApplicationContext(), Materias.class);//Lanzar actividad
                                Materia.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(Materia);
                            }
                        }
                    });
                    dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                        }
                    });
                    dialogo1.show();
                }else if(nombreE.getText().toString().equals(Materias.documentoCargado)){
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MateriasNuevo.this);
                    dialogo1.setTitle("Importante");
                    dialogo1.setMessage("¿ Deseas guardar los cambios realizados en esta materia ?");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            if(nombreE.getText().toString().length() == 0){
                                Toast toast1 = Toast.makeText(getApplicationContext(), "Llena el nombre de la materia", Toast.LENGTH_SHORT);
                                toast1.show();
                            }else{
                                Materias.documentoCargado=null;
                                String nombre = nombreE.getText().toString();
                                guardar(nombre);
                                Intent Materia = new Intent(getApplicationContext(), Materias.class);//Lanzar actividad
                                Materia.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(Materia);
                            }
                        }
                    });
                    dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                        }
                    });
                    dialogo1.show();
                }else{
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MateriasNuevo.this);
                    dialogo1.setTitle("Importante");
                    dialogo1.setMessage("¿ El nombre de esta materia ha cambiado, deseas guardarla como una nueva ?");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            if(nombreE.getText().toString().length() == 0){
                                Toast toast1 = Toast.makeText(getApplicationContext(), "Llena el nombre de la materia", Toast.LENGTH_SHORT);
                                toast1.show();
                            }else{
                                Materias.documentoCargado=null;
                                String nombre = nombreE.getText().toString();
                                guardar(nombre);
                                Intent Materia = new Intent(getApplicationContext(), Materias.class);//Lanzar actividad
                                Materia.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(Materia);
                            }
                        }
                    });
                    dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {

                        }
                    });
                    dialogo1.show();
                }
            }
        });
    }

    private void guardar(String nombre) {
        EditText nombreMateria = (EditText) findViewById(R.id.editText);
        EditText nombreProfesor = (EditText) findViewById(R.id.editText2);
        EditText descripcion = (EditText) findViewById(R.id.editText3);
        DocumentReference referencia = Inicio.db.collection("usuarios")
                .document(Inicio.ID)
                .collection("historias")
                .document(String.valueOf(Inicio.id))
                .collection("periodos")
                .document(Inicio.periodo + " "+ Inicio.idP)
                .collection("Materias")
                .document(nombre);
        Map<String, Object> materia = new HashMap<>();
        materia.put("nombreMateria",nombreMateria.getText().toString());
        materia.put("nombreProfesor",nombreProfesor.getText().toString());
        materia.put("descripcion",descripcion.getText().toString());
        referencia.set(materia) //sobreescribe los datos o los escribe por primera vez
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Materias.documentoCargado=null;
        Intent Materias = new Intent(getApplicationContext(), Materias.class);//Lanzar actividad
        startActivity(Materias);
    }

    /////Función Para Preguntar si un String es NULO/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static boolean esNulo(String s){
        return (s==null || s.trim().equals(""));
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
