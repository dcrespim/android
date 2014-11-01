package com.tutoriales.basedatos01;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import android.view.MenuItem;


/**
 * Un activity representando la ventana del detalle de una tarea. Este
 * activity solo se usa en dispositivos telefonicos. En dispositivos del tamanyo tablet,
 * los detalles del elemento son representados al lado de la lissta de elementos
 * en el  {@link TareaListActivity}.
 * <p>
 * Este activity es mas que nada un activity 'shell' que no contiene nada
 * mas que un {@link TareaDetailFragment}.
 */
public class TareaDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea_detail);

        // Mostrar el boton Up de la barra de accion
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState no es nulo cuando hay un estado salvado de ese fragment
        // por configuraciones anteriores del activity
        // (e.g. cuando se rota la ventana de vertical a apaisado).
        // En este caso, el fragment se volvera a agregar automaticamente
        // en el contenedor por lo que na sera necesario anyadirlo manualmente.
        // Para mas informacion, ver el Fragments API guide en:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Crea el fragment de detalle y lo anya al activity
            // usando una transaccion fragment.
            Bundle arguments = new Bundle();
            arguments.putString(TareaDetailFragment.ARG_TASK_ID,
                    getIntent().getStringExtra(TareaDetailFragment.ARG_TASK_ID));
            TareaDetailFragment fragment = new TareaDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.tarea_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // Este ID representa el Home o el boton Up. En el caso de este
            // activity, el boton Up se visualizara. Para
            // mas detalles, ver el Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, TareaListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
