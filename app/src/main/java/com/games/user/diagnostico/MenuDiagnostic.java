package com.games.user.diagnostico;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_diagnostic);
        n = findViewById(R.id.new_element);
        txtvw = findViewById(R.id.textviewadd);
        data = new ContactDiagnostic(this);
        data.open();

        n.setOnClickListener(this);


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

            String Fecha = values.get(i).fechadiagnostic.charAt(0) + "" + values.get(i).fechadiagnostic.charAt(1) + "/" + values.get(i).fechadiagnostic.charAt(2) + "" + values.get(i).fechadiagnostic.charAt(3) + "/" +
                    values.get(i).fechadiagnostic.charAt(4) + "" + values.get(i).fechadiagnostic.charAt(5) + values.get(i).fechadiagnostic.charAt(6) + values.get(i).fechadiagnostic.charAt(7);

            String paths = Environment.getExternalStorageDirectory() +
                    File.separator + Diagnostic.RUTA_IMAGEN + File.separator + 0 + values.get(i).fechadiagnostic + values.get(i).idimagen + ".jpg";

            Bitmap bitmap = BitmapFactory.decodeFile(paths);
            category.add(new Category("olo" + values.get(i).iddiagnostic, "Servicio 1", Fecha + '\n' + values.get(i).rbtndiagnostic, getResizedBitmap(bitmap, this)));
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
                i.putExtra("diagnosticfecha", values.get(position).fechadiagnostic);
                i.putExtra("diagnostichistoria", values.get(position).historiadiagnostic);
                i.putExtra("diagnosticrbtn", values.get(position).rbtndiagnostic);
                i.putExtra("diagnosticnombre", values.get(position).nombrediagnostic);
                i.putExtra("diagnosticedad", values.get(position).edaddiagnostic);
                i.putExtra("diagnosticalergia", values.get(position).alergiadiagnostic);
                i.putExtra("diagnosticpatologia", values.get(position).patologiadiagnostic);
                i.putExtra("diagnosticherida", values.get(position).heridadiagnostic);
                i.putExtra("diagnosticlocalizacion", values.get(position).localizaciondiagnostic);
                i.putExtra("diagnosticmotivo", values.get(position).motivodiagnostic);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), MenuDiagnostic.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intentds = new Intent(MenuDiagnostic.this, MenuDiagnostic.class);
            startActivity(intentds);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(MenuDiagnostic.this, Diagnostic.class);
        startActivity(i);
        finish();
    }
}