package com.tutoriales.basedatos01;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;




/**
 * El activity representa una lista de tareas. Este activity
 * tiene diferentes representaciones para los dispositivos moviles y dispostivos de tamaño tablet.
 * En dispositivos moviles, el activity representa una lista de elementos, que cuando se tocan,
 * conducen a {@link TareaDetailActivity} que representa detalles de objetos.
 * En tablets, el activity representa la lista de elementos y los detalles del elemento
 * al lado usando dos paneles verticales.
 * <p>
 * El activity hace un uso intensivo de los fragments. La lista de elementos es una
 * {@link TareaListFragment} y el detalle del elemento
 * (si esta presente) es una  {@link TareaDetailFragment}.
 * <p>
 * Este activity tambien implementa el requerido
 * {@link TareaListFragment.Callbacks} interface
 * para escuchar las selecciones de elementos.
 */
public class TareaListActivity extends Activity
        implements TareaListFragment.Callbacks {

    /**
     * Si el Activity esta en modo de dos paneles, por ejemplo si se esta ejecutando en un
     * dispositivo de tipo tablet.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea_list);

        if (findViewById(R.id.tarea_detail_container) != null) {
            // La vista que contiene el detalle estará presente solo in layouts de
            // ventana grande (res/values-large y
            // res/values-sw600dp). Si esta vista esta presente, entonces el
            // Activity debe estar en modo dos planeles.
            mTwoPane = true;

            // En modo dos paneles, la lista de elementos debe tener el
            // estado 'activated' cuando se toca.
            ((TareaListFragment) getFragmentManager()
                    .findFragmentById(R.id.tarea_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * El metodo Callback de {@link TareaListFragment.Callbacks}
     * lo que indica que se ha seleccionado el elemento con el ID seleccionado.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // En modo dos paneles, mostrar la vista de detalle en esta Activity para
            // anyadir o reemplazar el fragment de detalle usando una
            // transaccion fragment.
            Bundle arguments = new Bundle();
            arguments.putString(TareaDetailFragment.ARG_TASK_ID, id);
            TareaDetailFragment fragment = new TareaDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.tarea_detail_container, fragment)
                    .commit();

        } else {
            // En modo mono panel, simplemente empieza el Activity de detalle
            // para el ID seleccionado.
            Intent detailIntent = new Intent(this, TareaDetailActivity.class);
            detailIntent.putExtra(TareaDetailFragment.ARG_TASK_ID, id);
            startActivity(detailIntent);
        }
    }
}
