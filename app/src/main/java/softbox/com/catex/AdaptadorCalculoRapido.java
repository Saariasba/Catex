package softbox.com.catex;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdaptadorCalculoRapido extends BaseAdapter {
    private Context contexto;
    private ArrayList<CalculoRapidoItem> listItems;

    public AdaptadorCalculoRapido(Context contexto, ArrayList<CalculoRapidoItem> listItems) {
        this.contexto = contexto;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public  Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CalculoRapidoItem item = (CalculoRapidoItem) getItem(position);

        convertView = LayoutInflater.from(contexto).inflate(R.layout.elemento_lista_calculo_rapido,null);
        TextView valor = (TextView) convertView.findViewById(R.id.valor);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
        FloatingActionButton editar = convertView.findViewById(R.id.editar);
        FloatingActionButton eliminar = convertView.findViewById(R.id.eliminar);

        valor.setText(item.getValor());
        nombre.setText(item.getNombre());

        cambios(editar,eliminar,item);

        return convertView;
    }

    private void cambios(FloatingActionButton editar,FloatingActionButton eliminar, final CalculoRapidoItem item) {
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalculoRapidoItem proximoACargar= item;
                CalculoRapido.documentoCargado = proximoACargar.getNombre();
                Intent nuevo = new Intent(contexto, CalculoRapidoNuevo.class);//Lanzar actividad
                contexto.startActivity(nuevo);
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(contexto);
                dialogo1.setTitle("¿ Deseas Eliminar este calculo ?");
                dialogo1.setMessage("Se eliminará TODA la información contenida sobre el");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        DocumentReference referencia = Inicio.db.collection("usuarios")
                                .document(Inicio.ID)
                                .collection("historias")
                                .document(String.valueOf(Inicio.id))
                                .collection("periodos")
                                .document(Inicio.periodo + " "+ Inicio.idP)
                                .collection("Calculos").
                                document(item.getNombre());
                        referencia.delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //Log.w(TAG, "Error deleting document", e);
                                    }
                                });
                        listItems.remove(item);
                        CalculoRapido.adaptador.notifyDataSetChanged();
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
}
