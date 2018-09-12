package com.games.user.diagnostico;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    public static final String TABLE_DIAGNOSTIC = "diagnostic";
    public static final String DIAGNOSTIC_ID = "diagnosticid";
    public static final String DIAGNOSTIC_FECHAS = "diagnosticfecha";
    public static final String DIAGNOSTIC_HISTORIA = "diagnostichistoria";
    public static final String DIAGNOSTIC_RBTNS = "diagnosticrbtn";
    public static final String DIAGNOSTIC_NOMBRE = "diagnosticnombre";
    public static final String DIAGNOSTIC_EDAD = "diagnosticedad";
    public static final String DIAGNOSTIC_ALERGIA = "diagnosticalergia";
    public static final String DIAGNOSTIC_PATOLOGIA = "diagnosticpatologia";
    public static final String DIAGNOSTIC_HERIDA = "diagnosticherida";
    public static final String DIAGNOSTIC_LOCALIZACION = "diagnosticlocalizacion";
    public static final String DIAGNOSTIC_MOTIVOS = "diagnosticmotivo";
    public static final String DIAGNOSTIC_IDIMAGEN = "idimagen";


    private static final String DATABASE_NAME = "contacts.db";

    private static final int DATABASE_VERSION = 1;


    // Database creation sql statement
    private static final String Pases = "create table "
            + TABLE_DIAGNOSTIC + "(" + DIAGNOSTIC_ID
            + " integer primary key autoincrement,"
            + DIAGNOSTIC_FECHAS + " text not null,"
            + DIAGNOSTIC_HISTORIA + " text not null,"
            + DIAGNOSTIC_RBTNS + " text not null,"
            + DIAGNOSTIC_NOMBRE + " text not null,"
            + DIAGNOSTIC_EDAD + " text not null,"
            + DIAGNOSTIC_ALERGIA + " text not null,"
            + DIAGNOSTIC_PATOLOGIA + " text not null,"
            + DIAGNOSTIC_HERIDA + " text not null,"
            + DIAGNOSTIC_LOCALIZACION + " text not null,"
            + DIAGNOSTIC_MOTIVOS + " text not null,"
            + DIAGNOSTIC_IDIMAGEN + " text not null)";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(Pases);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Database.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIAGNOSTIC);


        onCreate(db);
    }
}