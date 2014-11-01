package com.tutoriales.basedatos01.bbdd;

/**
 * Created by dcrespi on 22/05/13.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.tutoriales.basedatos01.model.Tarea;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Clase que facilita el acceso a una Base de Datos SQLite. La clase se ha creado a partir del
 * codigo del blog.findemor.es 06/02/2011
 **/
public class BaseDatosHelper extends SQLiteOpenHelper {

    // La carpeta por defecto donde Android espera encontrar la Base de Datos de
    // tu aplicacion
    private static String DB_PATH = "/data/data/com.tutoriales.basedatos01/databases/";
    private static String DB_NAME = "TareasDB";
    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor
     *
     * Guarda una referencia al contexto para acceder a la carpeta assets de la
     * aplicacion y a los recursos
     *
     * @param contexto
     **/
    public BaseDatosHelper(Context contexto) {
        super(contexto, DB_NAME, null, 1);
        this.myContext = contexto;
    }

    /**
     * Crea una base de datos vacia en el sistema y la sobreescribe con la que
     * hemos puesto en Assets
     **/
    public void crearDataBase() throws IOException {

        boolean dbExist = comprobarBaseDatos();

        if (dbExist) {
            // Si ya existe no hacemos nada
        } else {
            // Si no existe, creamos una nueva Base de datos en la carpeta por
            // defecto de nuestra aplicacion,
            // de esta forma el Sistema nos permitira sobreescribirla con la que
            // tenemos en la carpeta Assets
            this.getReadableDatabase();
            try {
                copiarBaseDatos();
            } catch (IOException e) {
                throw new Error("Error al copiar la Base de Datos");
            }
        }
    }

    /**
     * Comprobamos si la base de datos existe
     *
     * @return true si existe, false en otro caso
     **/
    private boolean comprobarBaseDatos() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            // No existe
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    /**
     * Copia la base de datos desde la carpeta Assets sobre la base de datos
     * vacia recien creada en la carpeta del sistema, desde donde es accesible
     **/
    private void copiarBaseDatos() throws IOException {

        // Abrimos la BBDD de la carpeta Assets como un InputStream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Carpeta de destino (donde hemos creado la BBDD vacia)
        String outFileName = DB_PATH + DB_NAME;

        // Abrimos la BBDD vacia como OutputStream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // Transfiere los Bytes entre el Stream de entrada y el de Salida
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Cerramos los ficheros abiertos
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    /**
     * Abre la base de datos
     **/
    public void abrirBaseDatos() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);

    }

    /**
     * Cierra la base de datos
     **/
    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // No usamos este metodo
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No usamos este metodo
    }

    // Podemos anyadir metodos publicos que accedan al contenido de la base de
    // datos,
    // para realizar consultas, u operaciones CRUD (create, read, update,
    // delete)

    private final String TABLE_TAREAS = "Tareas";
    private final String TABLE_KEY_IDDB = "iddb";
    private final String TABLE_KEY_ID = "id";
    private final String TABLE_KEY_TITLE = "title";
    private final String TABLE_KEY_ETAG = "etag";
    private final String TABLE_KEY_COMPLETED = "completed";
    private final String TABLE_KEY_DELETED = "deleted";
    private final String TABLE_KEY_DUE = "due";
    private final String TABLE_KEY_NOTES = "notes";
    /*
     * Obtiene todas las tareas desde la Base de Datos
     */
    public ArrayList<Tarea> getTareas() {
        ArrayList<Tarea> listaTareas = new ArrayList<Tarea>();
        try {
            Cursor c = myDataBase.query(TABLE_TAREAS, new String[] {
                    TABLE_KEY_IDDB, TABLE_KEY_ID, TABLE_KEY_TITLE,
                    TABLE_KEY_COMPLETED,TABLE_KEY_DELETED, TABLE_KEY_DUE,
                    TABLE_KEY_ETAG, TABLE_KEY_NOTES}, null,
                    null, null, null, null);

            // Iteramos a traves de los registros del cursor
            c.moveToFirst();
            while (c.isAfterLast() == false) {
                Tarea tarea = new Tarea();
                tarea.setId(c.getString(1));
                tarea.setTitle(c.getString(2));
                tarea.setEtag(c.getString(6));
                tarea.setNotes(c.getString(7));
                listaTareas.add(tarea);
                c.moveToNext();
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaTareas;
    }
}