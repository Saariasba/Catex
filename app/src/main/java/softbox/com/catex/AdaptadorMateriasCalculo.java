package softbox.com.catex;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdaptadorMateriasCalculo extends BaseAdapter {
    private Context contexto;
    private ArrayList<MateriasCalculoItem> listItems;

    public AdaptadorMateriasCalculo(Context contexto, ArrayList<MateriasCalculoItem> listItems) {
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
        MateriasCalculoItem item = (MateriasCalculoItem) getItem(position);

        convertView = LayoutInflater.from(contexto).inflate(R.layout.elemento_lista_materias_calculo,null);
        FloatingActionButton eliminar = (FloatingActionButton) convertView.findViewById(R.id.eliminar);
        FloatingActionButton editar = (FloatingActionButton) convertView.findViewById(R.id.editar);
        TextView valor = (TextView) convertView.findViewById(R.id.valor);
        TextView porcentaje = (TextView) convertView.findViewById(R.id.porcentaje);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre);

        valor.setText(item.getValor());
        porcentaje.setText(item.getPorcentaje());
        nombre.setText(item.getTitulo());
        cambios(item,valor,porcentaje,nombre,eliminar,editar);

        return convertView;
    }
    private void cambios(final MateriasCalculoItem item, final TextView valor, final TextView porcentaje, final TextView nombre,FloatingActionButton eliminar, FloatingActionButton editar) {
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
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItems.remove(item);
                MateriasCalculo.adaptador.notifyDataSetChanged();
            }
        });
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0;i<listItems.size();i++){
                    if(listItems.get(i)==item){
                        MateriasCalculo.posicion = i;
                    }
                }
                MateriasCalculoEditar.listItems = MateriasCalculo.listItems;
                Intent editar = new Intent(contexto, MateriasCalculoEditar.class);//Lanzar actividad
                contexto.startActivity(editar);
            }
        });
    }
}
