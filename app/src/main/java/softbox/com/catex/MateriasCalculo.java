package softbox.com.catex;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MateriasCalculo extends AppCompatActivity {

    public static String documentoCargado = null;
    public static  int bandera = 0;
    public static  int bandera2 = 0;
    public static int posicion = 0;

    private AdView mAdView;

    public ListView items;
    public static AdaptadorMateriasCalculo adaptador;
    public static ArrayList<MateriasCalculoItem> listItems;
    Double total;
    Double totalP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materias_calculo);
        getSupportActionBar().hide();// oculta la barra
        lista();
        abrirActividades();
        edicion();
    }

    private void edicion() {
        if (bandera == 1){
            listItems.clear();
            for (int i = 0;i<MateriasCalculoEditar.listItems.size();i++){
                listItems.add(MateriasCalculoEditar.listItems.get(i));
            }
            listItems.set(posicion,MateriasCalculoEditar.nuevo);
            adaptador.notifyDataSetChanged();
            posicion = 0;
            bandera=0;
            MateriasCalculoEditar.listItems = null;
            MateriasCalculoEditar.nuevo = null;
        }else{
            if(bandera2==1){
                listItems.clear();
                for (int i = 0;i<MateriasCalculoEditar.listItems.size();i++){
                    listItems.add(MateriasCalculoEditar.listItems.get(i));
                }
                adaptador.notifyDataSetChanged();
                posicion = 0;
                bandera2=0;
                MateriasCalculoEditar.listItems = null;
                MateriasCalculoEditar.nuevo = null;
            }else{
                cargar();
            }
        }
    }

    /////Función Cargar Datos////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void cargar() {
        if(esNulo(Materias.documentoCargado)){
            MateriasCalculoItem itemNuevo = new MateriasCalculoItem();
            itemNuevo.setValor("0");
            itemNuevo.setPorcentaje("0");
            listItems.add(itemNuevo);
            adaptador.notifyDataSetChanged();
            calcular();
        }else{
            DocumentReference referencia = Inicio.db.collection("usuarios")
                    .document(Inicio.ID)
                    .collection("historias")
                    .document(String.valueOf(Inicio.id))
                    .collection("periodos")
                    .document(Inicio.periodo + " "+ Inicio.idP)
                    .collection("Materias")
                    .document(Materias.documentoCargado)
                    .collection("notas")
                    .document("notas");
            referencia.get() //Obtener Documento de referencia
                    .addOnCompleteListener(new OnCompleteListener< DocumentSnapshot >() {
                        @Override
                        public void onComplete(@NonNull Task< DocumentSnapshot > task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot doc = task.getResult(); //Obtener el documento
                                if (doc.exists()) { //Saber si existe el documento
                                    ////Cargar información////
                                    Long tamaño = (Long) doc.get("tamaño");
                                    for (int i=0;i<tamaño;i++){
                                        String valor = (String) doc.get("valor"+i);
                                        String porcentaje = (String) doc.get("porcentaje"+i);
                                        String nombre = (String) doc.get("nombre"+i);
                                        MateriasCalculoItem itemNuevo = new MateriasCalculoItem();
                                        itemNuevo.setValor(valor);
                                        itemNuevo.setPorcentaje(porcentaje);
                                        itemNuevo.setTitulo(nombre);
                                        listItems.add(itemNuevo);
                                        adaptador.notifyDataSetChanged();
                                        calcular();
                                    }
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


    private void lista() {
        listItems = new ArrayList<MateriasCalculoItem>();
        items = (ListView) findViewById(R.id.listaCalculoRapidoNuevo);
        adaptador = new AdaptadorMateriasCalculo(this,listItems);
        items.setAdapter(adaptador);
    }


    public void abrirActividades() {
        FloatingActionButton igual = (FloatingActionButton) findViewById(R.id.igual);
        igual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calcular();
            }
        });
        FloatingActionButton mas = (FloatingActionButton) findViewById(R.id.mas);
        mas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int cantidadItem = listItems.size();
                MateriasCalculoItem itemNuevo = new MateriasCalculoItem();
                listItems.add(itemNuevo);
                adaptador.notifyDataSetChanged();
            }
        });
        Button volver = (Button) findViewById(R.id.button);//Id del boton y crear en java
        volver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                documentoCargado=null;
                Intent Materias = new Intent(getApplicationContext(), Materias.class);//Lanzar actividad
                startActivity(Materias);
            }
        });
        Button guardar = (Button) findViewById(R.id.button2);//Id del boton y crear en java
        guardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MateriasCalculo.this);
                    dialogo1.setTitle("Importante");
                    dialogo1.setMessage("¿ Desea guardar este calculo ?");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            calcular();
                            String nombre = documentoCargado;
                            guardar(nombre);
                            Intent Materias = new Intent(getApplicationContext(), Materias.class);//Lanzar actividad
                            Materias.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(Materias);

                        }
                    });
                    dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                        }
                    });
                    dialogo1.show();
            }
        });
    }


    private void calcular() {
        Double cuenta = 0.0;
        Double cuentaP = 0.0;
        for(int i = 0;i<listItems.size();i++){
            MateriasCalculoItem itemNuevo = new MateriasCalculoItem();
            itemNuevo = listItems.get(i);
            if(esNulo(itemNuevo.getValor()) || esNulo(itemNuevo.getPorcentaje())){

            }else{
                cuenta = cuenta + ((Double.parseDouble(itemNuevo.getValor())*Double.parseDouble(itemNuevo.getPorcentaje()))/100);
                cuentaP = cuentaP + Double.parseDouble(itemNuevo.getPorcentaje());
            }
        }
        total = cuenta;
        totalP = cuentaP;
        if(cuenta<=Inicio.max && cuenta>=Inicio.apb){
            TextView textView = (TextView) findViewById(R.id.textView3);  //TextView por ID
            color(textView,"verde",cuenta);
        }else if(cuenta>=Inicio.min && cuenta < Inicio.apb){
            TextView textView = (TextView) findViewById(R.id.textView3);  //TextView por ID
            color(textView,"amarillo",cuenta);

        }else if (cuenta>Inicio.max || cuenta < Inicio.min){
            TextView textView = (TextView) findViewById(R.id.textView3);  //TextView por ID
            color(textView,"rojo",cuenta);

        }
        if(cuentaP>100 || cuentaP<0){
            TextView textView = (TextView) findViewById(R.id.textView6);  //TextView por ID
            color(textView,"rojo",cuentaP);
        }else if(cuentaP==100){
            TextView textView = (TextView) findViewById(R.id.textView6);  //TextView por ID
            color(textView,"verde",cuentaP);
        }else{
            TextView textView = (TextView) findViewById(R.id.textView6);  //TextView por ID
            color(textView,"verde",cuentaP);
        }
    }

    private void color(TextView textView, String color, Double cuenta) {
        SpannableString miTexto = new SpannableString(String.valueOf(cuenta)); //Convierte el String
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if(color.equals("verde")){
            miTexto.setSpan(new ForegroundColorSpan(Color.GREEN), 0, miTexto.length(), 0);
        }else if(color.equals("rojo")){
            miTexto.setSpan(new ForegroundColorSpan(Color.RED), 0, miTexto.length(), 0);
        }else{
            miTexto.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, miTexto.length(), 0);
        }
        builder.append(miTexto);
        StyleSpan boldSpan1 = new StyleSpan(Typeface.BOLD); //Tipo de letra
        miTexto.setSpan(boldSpan1, 0, miTexto.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); //Guarda el texto modificado en variable
        textView.setText(miTexto); //Imprime el texto en TextView
    }


    /////Función Para Preguntar si un String es NULO/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static boolean esNulo(String s){
        return (s==null || s.trim().equals(""));
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onBackPressed() {
        documentoCargado=null;
        Intent Materias = new Intent(getApplicationContext(), Materias.class);//Lanzar actividad
        startActivity(Materias);
    }

    private void guardar(String texto) {
        DocumentReference referencia = Inicio.db.collection("usuarios")
                .document(Inicio.ID)
                .collection("historias")
                .document(String.valueOf(Inicio.id))
                .collection("periodos")
                .document(Inicio.periodo + " "+ Inicio.idP)
                .collection("Materias")
                .document(Materias.documentoCargado)
                .collection("notas")
                .document("notas");
        Map<String, Object> cuentas = new HashMap<>();
        for(int i = 0;i<listItems.size();i++){
            MateriasCalculoItem itemNuevo = new MateriasCalculoItem();
            itemNuevo = listItems.get(i);
            cuentas.put("valor"+i,itemNuevo.getValor());
            cuentas.put("porcentaje"+i,itemNuevo.getPorcentaje());
            cuentas.put("nombre"+i,itemNuevo.getTitulo());
        }
        cuentas.put("total",total);
        cuentas.put("totalP",totalP);
        cuentas.put("tamaño",listItems.size());
        referencia.set(cuentas) //sobreescribe los datos o los escribe por primera vez
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

}