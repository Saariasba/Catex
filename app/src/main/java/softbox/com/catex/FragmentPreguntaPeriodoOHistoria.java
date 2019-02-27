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
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

public class FragmentPreguntaPeriodoOHistoria extends Fragment {

    View vista;
    Button cancelar;
    ImageButton pasar;
    FragmentPreguntaPeriodoOHistoria f = this;
    static Long idP = Inicio.idP;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_pregunta_periodo_o_historia,container,false);
        cancelar = vista.findViewById(R.id.button);
        pasar = vista.findViewById(R.id.imageButton);

        pasar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                idP++;
                baseDeDatos();
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

    public void baseDeDatos() {
        //Datos que se van a guardar y Map para organizarlos
        final Map<String, Object> historias = new HashMap<>();
        final Map<String, Object> crear = new HashMap<>();
        crear.put("creado","si");
        historias.put("Nombre", Inicio.historia);
        historias.put("Divide Años", Inicio.anios);
        historias.put("Nombre Año", Inicio.aniosN);
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
}
