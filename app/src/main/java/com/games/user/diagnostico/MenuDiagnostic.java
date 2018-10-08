package com.games.user.diagnostico;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MenuDiagnostic extends AppCompatActivity implements View.OnClickListener {

    Button n;
    ContactDiagnostic data;
    TextView txtvw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_diagnostic);
        n = findViewById(R.id.new_element);
        txtvw = findViewById(R.id.textviewadd);
        data = new ContactDiagnostic(this);
        data.open();
        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("inicio", Context.MODE_PRIVATE);
        n.setOnClickListener(this);
        if (!sharedPref.getBoolean("inicio", false)) {
            final android.support.v7.app.AlertDialog.Builder constructor = new android.support.v7.app.AlertDialog.Builder(this);
            View vista = getLayoutInflater().inflate(R.layout.alert_dialog_inicio, null);
            constructor.setView(vista);
            final android.support.v7.app.AlertDialog dialogo = constructor.create();
            Button botonok = vista.findViewById(R.id.botonok);
            final CheckBox chbx = vista.findViewById(R.id.chbxdialog);
            TextView texto = vista.findViewById(R.id.txt);
            texto.setText("Recuerda que los datos que ingreses y los docuementos archivados solo son organizados en las carpetas" +
                    " del dispositivo, no almacenamos datos en servidores externos, ante todo nos preocupa la seguridad de tu informacion");
            botonok.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               SharedPreferences sharedPref;
                                               sharedPref = getSharedPreferences(
                                                       "inicio", Context.MODE_PRIVATE);
                                               SharedPreferences.Editor editor = sharedPref.edit();
                                               editor.putBoolean("inicio", chbx.isChecked());
                                               editor.commit();
                                               dialogo.cancel();
                                           }
                                       }
            );
            dialogo.show();
        }


        final List<ContactDiagnostic> values = data.getAll();


        // ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_expandable_list_item_1, values);
        //setListAdapter(adapter);
        //ListView listView = getListView();
        //Drawable res = getResources().getDrawable(R.drawable.btnagregar);

        if (values.size() == 0) {
            txtvw.setText("Agrega elementos desde aqui");
        } else {
            txtvw.setText("Selecciona para editar o eleminiar");
        }
        // Toast.makeText(this, ""+ values.size(), Toast.LENGTH_SHORT).show();
        ArrayList<Category> category = new ArrayList<Category>();
        for (int i = 0; i < values.size(); i++) {

            String Fecha = values.get(i).fecha.charAt(0) + "" + values.get(i).fecha.charAt(1) + "/" + values.get(i).fecha.charAt(2) + "" + values.get(i).fecha.charAt(3) + "/" +
                    values.get(i).fecha.charAt(4) + "" + values.get(i).fecha.charAt(5) + values.get(i).fecha.charAt(6) + values.get(i).fecha.charAt(7);
            String paths = Environment.getExternalStorageDirectory() +
                    File.separator + Diagnostic.RUTA_IMAGEN + File.separator + 0 + values.get(i).fecha+ values.get(i).idimagen + ".jpg";
            Bitmap bitmap = BitmapFactory.decodeFile(paths);
            category.add(new Category("olo" + values.get(i).iddiagnostic, "Servicio 1", Fecha + '\n'+ values.get(i).nombre + '\n'+ values.get(i).rbtn+ '\n'+ values.get(i).historia, getResizedBitmap(bitmap , this)));
        }
        ListView listView = findViewById(android.R.id.list);
        AdapterCategory adapter = new AdapterCategory(this, category);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent i = new Intent(MenuDiagnostic.this, EditDiagnostic.class);
                i.putExtra("diagnosticid", values.get(position).iddiagnostic);
                i.putExtra("diagnosticfecha", values.get(position).fecha);
                i.putExtra("diagnostichistoria", values.get(position).historia);
                i.putExtra("diagnosticrbtn", values.get(position).rbtn);
                i.putExtra("diagnosticnombre", values.get(position).nombre);
                i.putExtra("diagnosticedad", values.get(position).edad);
                i.putExtra("diagnosticalergia", values.get(position).alergia);
                i.putExtra("diagnosticpatologia", values.get(position).patologia);
                i.putExtra("diagnosticherida", values.get(position).herida);
                i.putExtra("diagnosticlocalizacion", values.get(position).localizacion);
                i.putExtra("diagnosticmotivo", values.get(position).motivo);
                i.putExtra("idimagen", values.get(position).idimagen);
                startActivity(i);
                fileList();

            }

        });


    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();

        //Refresh your stuff here
        // final List<Contact> values = data.getAll();
        // ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_expandable_list_item_1, values);
        //setListAdapter(adapter);
    }



    public static Bitmap getResizedBitmap(Bitmap bm, Context context) {
        Bitmap resizedBitmap;
        if (bm != null) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth = ((float) metrics.widthPixels / (100) * 28) / width;
            float scaleHeight = ((float) metrics.widthPixels / (100) * 28) / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);

            // "RECREATE" THE NEW BITMAP
            resizedBitmap = Bitmap.createBitmap(
                    bm, 0, 0, width, height, matrix, false);
            bm.recycle();
        } else {
            resizedBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        }
        return resizedBitmap;
    }


    @Override
    public void onClick(View v) {
        Intent i = new Intent(MenuDiagnostic.this, Diagnostic.class);
        startActivity(i);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View vi = inflater.inflate(R.layout.dialogoconfirm, null);
            builder.setView(vi);
            final AlertDialog dialog = builder.create();
            //decidir despues si sera cancelable o no
            dialog.setCancelable(false);

            Button botonsi = vi.findViewById(R.id.botonsi);
            botonsi.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                            MenuDiagnostic.super.onDestroy();
                            System.exit(0);


                        }
                    }
            );
            Button botonno = vi.findViewById(R.id.botonno);
            botonno.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();

                        }
                    }
            );
            dialog.show();
            //Metodos.dialogo( this, getLayoutInflater(), "¿seguro deseas salir de la aplicacion?", 0 );
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }





}



