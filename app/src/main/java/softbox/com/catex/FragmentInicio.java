package softbox.com.catex;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class FragmentInicio extends Fragment {

    View vista;
    Button listo,listo2;
    FragmentInicio f = this;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_inicio,container,false);
        listo = vista.findViewById(R.id.button);
        listo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(f).commit();
            }
        });
        listo2 = vista.findViewById(R.id.button2);
        listo2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(f).commit();
            }
        });
        return vista;
    }
}
