package softbox.com.catex;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

public class FragmentPasarAnio extends Fragment {

    View vista;
    Button cancelar;
    ImageButton pasar;
    EditText texto;
    FragmentPasarAnio f = this;
    Long idP = Inicio.idP;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_pasar_anio,container,false);
        cancelar = vista.findViewById(R.id.button);
        pasar = vista.findViewById(R.id.imageButton);
        texto = vista.findViewById(R.id.editText);

        pasar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nombre = texto.getText().toString();
                if(esNulo(nombre)){
                    Toast.makeText(getContext(),"Ingresa el nombre del nuevo año",Toast.LENGTH_SHORT).show();
                }else{
                    idP++;
                    baseDeDatos(nombre);
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(f).commit();
            }
        });

        return vista;
    }

    public void baseDeDatos(String anio) {
        //Datos que se van a guardar y Map para organizarlos
        final Map<String, Object> historias = new HashMap<>();
        final Map<String, Object> crear = new HashMap<>();
        crear.put("anio",anio);
        historias.put("Nombre", Inicio.historia);
        historias.put("Divide Años", Inicio.anios);
        historias.put("Nombre Año", anio);
        historias.put("Divisiones", Inicio.periodo);
        historias.put("Cantidad", Inicio.cantidad);
        historias.put("Minimo", Inicio.min);
        historias.put("Maximo", Inicio.max);
        historias.put("Aprobatorio", Inicio.apb);
        historias.put("ID",Inicio.id);
        historias.put("IDP",idP);

        Inicio.db.collection("usuarios")
                .document(Inicio.ID)
                .collection("historias")
                .document(String.valueOf(Inicio.id)) //documento donde se guardan los datos
                .set(historias) //sobreescribe los datos o los escribe por primera vez
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Inicio.db.collection("usuarios")
                                .document(Inicio.ID)
                                .collection("historias")
                                .document(String.valueOf(Inicio.id)) //documento donde se guardan los datos
                                .collection("periodos")
                                .document(Inicio.periodo + " "+ idP)
                                .set(crear) //sobreescribe los datos o los escribe por primera vez
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent intent = new Intent(getActivity(), Inicio.class);
                                        getActivity().startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //Log.w(TAG, "Error writing document", e);
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error writing document", e);
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
