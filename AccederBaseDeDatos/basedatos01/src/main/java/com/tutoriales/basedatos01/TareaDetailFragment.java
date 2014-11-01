package com.tutoriales.basedatos01;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.tutoriales.basedatos01.service.TareasContent;
import com.tutoriales.basedatos01.model.Tarea;

/**
 * Un fragment representando una sola ventana de  detalle de  Tarea.
 * Este fragment estara contenido en una  {@link TareaListActivity}
 * para modo de dos paneles (en tablets) o en una {@link TareaDetailActivity}
 * para telefonos.
 */
public class TareaDetailFragment extends Fragment {
    /**
     * Representa el argumento de identificador de tarea.
     */
    public static final String ARG_TASK_ID = "task_id";

    /**
     * El contenido de la tarea que esta representado.
     */
    private Tarea mItem;

    /**
     * El constructor vacio obligatorio para el controlador fragment para instanciar el
     * fragment (e.g. upon screen orientation changes).
     */
    public TareaDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_TASK_ID)) {
            // Carga el conteido de la tarea especificado por el argumento.
            // En un escenario en el mundo real, usar un Loader
            // para cargar el contenido del proveedor de contenido.
            mItem = TareasContent.ITEM_MAP.get(getArguments().getString(ARG_TASK_ID));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tarea_detail, container, false);

        // Visualizar el contenido de la tarea.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.tarea_notes)).setText(mItem.getNotes());
            ((TextView) rootView.findViewById(R.id.tarea_title)).setText(mItem.getTitle());
        }

        return rootView;
    }
}
