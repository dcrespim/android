package com.tutoriales.basedatos01;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tutoriales.basedatos01.model.Tarea;
import com.tutoriales.basedatos01.service.TareasContent;

/**
 * Un ListFragment representando una lista de Tareas. Este fragment
 * también soporta los dispositivos tablets permitiendo listas de elementos al tener un
 * estado 'activated' de la seleccion. This helps indicate which item is
 * currently being viewed in a {@link TareaDetailFragment}.
 * <p>
 * Los Activities que contienen este Fragment DEBEN implementar el {@link Callbacks}
 * interface.
 */
public class TareaListFragment extends ListFragment {

    TareasContent tareasContent;
    /**
     * La serializacion (saved instance state) Bundle key representa la
     * posicion del elemento activado. Solo usado en tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * El actual objeto callback del Fragment, que se notifica los elemntos de lista
     * clickados.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * La posicion del elemento actualmente activado. Solo usado por tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * Una callback interface que todos los activities contienen este fragment deben
     * implementar. Este mecanismo permite que los activities sean notificados de las selecciones
     * de elementos.
     */
    public interface Callbacks {
        /**
         * Callback para cuando un elemento se seleccione.
         */
        public void onItemSelected(String id);
    }

    /**
     * Una implementacion por defecto de {@link Callbacks} interface que no hace
     * nada. Usado solo cuando este fragment no tenga asociada un activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    /**
     * Constructor vacio obligatorio para el gestor de fragments para crear instancias del
     * fragment (e.g. upon screen orientation changes).
     */
    public TareaListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tareasContent = new TareasContent(getActivity());

        setListAdapter(new ArrayAdapter<Tarea>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                tareasContent.ITEMS));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restaura la posicion del elemento previamente serializado activado.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities debe contener este codigo para implementar el callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reinicia callbacks interface activo para la implementacion ficticia.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notifica al callbacks interface activo (la activity, si el fragment es incluido a uno)
        // que un articulo ha sido seleccionado.
        mCallbacks.onItemSelected(tareasContent.ITEMS.get(position).getId());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serializa y persiste la posicion del elemento activado.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Activa el modo activate-on-click. Cuando este modo esta activo, la lista de elmentos
     * se le dara el estado 'activated' cuando se toca.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // Al establecer CHOICE_MODE_SINGLE, ListView dara automaticamente a los elementos
        // el estado 'activated' al tocarse.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
}
