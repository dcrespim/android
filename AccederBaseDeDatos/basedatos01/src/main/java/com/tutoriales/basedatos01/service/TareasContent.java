package com.tutoriales.basedatos01.service;

import android.app.Activity;

import com.tutoriales.basedatos01.bbdd.BaseDatosHelper;
import com.tutoriales.basedatos01.model.Tarea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class TareasContent {

    BaseDatosHelper miBBDDHelper;

    public void crearBBDD(Activity activity) {
        miBBDDHelper = new BaseDatosHelper(activity);
        try {
            miBBDDHelper.crearDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
    }
    /**
     * An array of sample (dummy) items.
     */
    public static List<Tarea> ITEMS = new ArrayList<Tarea>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Tarea> ITEM_MAP = new HashMap<String, Tarea>();

    public TareasContent (Activity activity){
        crearBBDD(activity);
        miBBDDHelper.abrirBaseDatos();
        ITEMS = miBBDDHelper.getTareas();

        for (Tarea tarea : ITEMS) {
            ITEM_MAP.put(tarea.getId(), tarea);
        }
    }
}
