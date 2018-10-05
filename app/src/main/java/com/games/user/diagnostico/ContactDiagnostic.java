package com.games.user.diagnostico;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 30/08/2018.
 */
public class ContactDiagnostic {

    public long iddiagnostic;
    public String fecha;
    public String historia;
    public String rbtn;
    public String nombre;
    public String edad;
    public String alergia;
    public String patologia;
    public String herida;
    public String localizacion;
    public String motivo;
    public String imagen;

    public long getId() {
        return iddiagnostic;
    }

    public String toString() {
        return fecha + " " + rbtn;
    }

    private SQLiteDatabase database;
    private Database dbHelper;
    private String[] ColumnasDiagnostic = {Database.DIAGNOSTIC_ID,
            Database.DIAGNOSTIC_FECHAS,
            Database.DIAGNOSTIC_HISTORIA,
            Database.DIAGNOSTIC_RBTNS,
            Database.DIAGNOSTIC_NOMBRE,
            Database.DIAGNOSTIC_EDAD,
            Database.DIAGNOSTIC_ALERGIA,
            Database.DIAGNOSTIC_PATOLOGIA,
            Database.DIAGNOSTIC_HERIDA,
            Database.DIAGNOSTIC_LOCALIZACION,
            Database.DIAGNOSTIC_MOTIVOS,
            Database.DIAGNOSTIC_IDIMAGEN};


    public ContactDiagnostic(Context context) {
        dbHelper = new Database(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();

    }

    public ContactDiagnostic createDiagnostic(String fechadiagnostic, String historiadiagnostic, String rbtndiagnostic, String nombrediagnostic, String edaddiagnostic,
                                              String alergiadiagnostic, String patologiadiagnostic, String heridadiagnostic, String localizaciondiagnostic, String motivodiagnostic, String imagenid) {

        ContentValues values = new ContentValues();
        values.put(Database.DIAGNOSTIC_FECHAS, fechadiagnostic);
        values.put(Database.DIAGNOSTIC_HISTORIA, historiadiagnostic);
        values.put(Database.DIAGNOSTIC_RBTNS, rbtndiagnostic);
        values.put(Database.DIAGNOSTIC_NOMBRE, nombrediagnostic);
        values.put(Database.DIAGNOSTIC_EDAD, edaddiagnostic);
        values.put(Database.DIAGNOSTIC_ALERGIA, alergiadiagnostic);
        values.put(Database.DIAGNOSTIC_PATOLOGIA, patologiadiagnostic);
        values.put(Database.DIAGNOSTIC_HERIDA, heridadiagnostic);
        values.put(Database.DIAGNOSTIC_LOCALIZACION, localizaciondiagnostic);
        values.put(Database.DIAGNOSTIC_MOTIVOS, motivodiagnostic);
        values.put(Database.DIAGNOSTIC_IDIMAGEN, imagenid);

        long insertId = database.insert(Database.TABLE_DIAGNOSTIC, null,
                values);
        Cursor cursor = database.query(Database.TABLE_DIAGNOSTIC,
                ColumnasDiagnostic, Database.DIAGNOSTIC_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        ContactDiagnostic newContact = cursorToContact(cursor);
        cursor.close();

        return newContact;
    }

    public void updateDiagnostic(long iddiagnostic, String fechadiagnostic, String historiadiagnostic, String rbtndiagnostic, String nombrediagnostic, String edaddiagnostic,
                                 String alergiadiagnostic, String patologiadiagnostic, String heridadiagnostic, String localizaciondiagnostic, String motivodiagnostic) {
        ContentValues values = new ContentValues();
        values.put(Database.DIAGNOSTIC_FECHAS, fechadiagnostic);
        values.put(Database.DIAGNOSTIC_HISTORIA, historiadiagnostic);
        values.put(Database.DIAGNOSTIC_RBTNS, rbtndiagnostic);
        values.put(Database.DIAGNOSTIC_NOMBRE, nombrediagnostic);
        values.put(Database.DIAGNOSTIC_EDAD, edaddiagnostic);
        values.put(Database.DIAGNOSTIC_ALERGIA, alergiadiagnostic);
        values.put(Database.DIAGNOSTIC_PATOLOGIA, patologiadiagnostic);
        values.put(Database.DIAGNOSTIC_HERIDA, heridadiagnostic);
        values.put(Database.DIAGNOSTIC_LOCALIZACION, localizaciondiagnostic);
        values.put(Database.DIAGNOSTIC_MOTIVOS, motivodiagnostic);
        String where = "licenciaid=?";
        String[] whereargs = new String[]{String.valueOf(iddiagnostic)};
        long insertId = database.update(Database.TABLE_DIAGNOSTIC,
                values, where, whereargs);
        Cursor cursor = database.query(Database.TABLE_DIAGNOSTIC,
                ColumnasDiagnostic, Database.DIAGNOSTIC_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();

    }

    public void deleteContactDiagnostic(long iddiagnostic) {
        System.out.println("Contact deleted with id: " + iddiagnostic);
        database.delete(Database.TABLE_DIAGNOSTIC, Database.DIAGNOSTIC_ID
                + " = " + iddiagnostic, null);
    }

    public List<ContactDiagnostic> getAll() {
        List<ContactDiagnostic> comments = new ArrayList<ContactDiagnostic>();

        Cursor cursor = database.query(Database.TABLE_DIAGNOSTIC,
                ColumnasDiagnostic, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ContactDiagnostic contact = cursorToContact(cursor);
            comments.add(contact);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private ContactDiagnostic cursorToContact(Cursor cursor) {
        ContactDiagnostic c = new ContactDiagnostic(null);
        c.iddiagnostic = cursor.getLong(0);
        c.fecha = cursor.getString(1);
        c.historia = cursor.getString(2);
        c.rbtn = cursor.getString(3);
        c.nombre = cursor.getString(4);
        c.edad = cursor.getString(5);
        c.alergia= cursor.getString(6);
        c.patologia = cursor.getString(7);
        c.herida = cursor.getString(8);
        c.localizacion = cursor.getString(9);
        c.motivo = cursor.getString(10);
        c.imagen = cursor.getString(11);
        return c;
    }


}