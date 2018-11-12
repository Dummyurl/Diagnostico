package com.games.user.diagnostico;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.microedition.khronos.egl.EGLDisplay;

public class EditDiagnostic extends AppCompatActivity implements View.OnClickListener {


    final int COD_SELECCIONA = 10;
    final int COD_FOTO = 0;
    public static String CARPETA_RAIZ = "MisImagenesDiagnostico/";
    public static String RUTA_IMAGEN = CARPETA_RAIZ + "Diagnosticos";

    Button upd_el, del_btn, whats;
    EditText historial, nombre, edad, alergia, patologia, herida, localizacion, motivo;
    RadioButton verde, amarillo, rojo;
    TextView textfecha;
    ImageView imagen;
    ;
    boolean foto = false;
    String path, Fecha, radiobutn, imagenid, mes, dia;
    private int diaint, mesint, ano;

    long id;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_diagnostic);
        whats = findViewById(R.id.botonwats);
        whats.setOnClickListener(this);
        upd_el = findViewById(R.id.upd_element);
        upd_el.setOnClickListener(this);
        del_btn = findViewById(R.id.del_btn);
        del_btn.setOnClickListener(this);
        textfecha = findViewById(R.id.txvEfecha);
        historial = findViewById(R.id.historial);

        verde = findViewById(R.id.verde);
        amarillo = findViewById(R.id.amarillo);
        rojo = findViewById(R.id.rojo);

        nombre = findViewById(R.id.nombre);
        edad = findViewById(R.id.edad);
        alergia = findViewById(R.id.alergia);
        patologia = findViewById(R.id.patologia);
        herida = findViewById(R.id.herida);
        localizacion = findViewById(R.id.localizacion);
        motivo = findViewById(R.id.motivo);
        imagen = findViewById(R.id.imagemId);


        Intent i = getIntent();
        id = i.getLongExtra("diagnosticid", 0);
        Fecha = i.getStringExtra("diagnosticfecha");
        imagenid = i.getStringExtra("idimagen");
        //name.setText(Fecha);
        textfecha.setText(Fecha.charAt(0) + "" + Fecha.charAt(1) + "/" + Fecha.charAt(2) + "" + Fecha.charAt(3) + "/" +
                Fecha.charAt(4) + "" + Fecha.charAt(5) + Fecha.charAt(6) + Fecha.charAt(7));

        switch (i.getStringExtra("diagnosticrbtn")) {
            case "VERDE":
                verde.setChecked(true);
                break;
            case "AMARILLO":
                amarillo.setChecked(true);
                break;
            case "ROJO":
                rojo.setChecked(true);
                break;
        }
        historial.setText(i.getStringExtra("diagnostichistoria"));
        nombre.setText(i.getStringExtra("diagnosticnombre"));
        edad.setText(i.getStringExtra("diagnosticedad"));
        alergia.setText(i.getStringExtra("diagnosticalergia"));
        patologia.setText(i.getStringExtra("diagnosticpatologia"));
        herida.setText(i.getStringExtra("diagnosticherida"));
        localizacion.setText(i.getStringExtra("diagnosticlocalizacion"));
        motivo.setText(i.getStringExtra("diagnosticmotivo"));


        String paths = Environment.getExternalStorageDirectory() +
                File.separator + RUTA_IMAGEN + File.separator + 0 + Fecha + imagenid + ".jpg";

        Bitmap bitmap = BitmapFactory.decodeFile(paths);
        imagen.setImageBitmap(bitmap);



        //recuso boton a atras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), MenuDiagnostic.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }
        return super.onOptionsItemSelected(item);
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


    public static void onClickWhatsApp(Context context, ImageView iv, String text) {

        Bitmap bm = ((BitmapDrawable) iv.getDrawable()).getBitmap();
        PackageManager pm = context.getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("*/*");

            String fileName = "" + "12" + "FMQ.jpg";
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            File ExternalStorageDirectory = Environment.getExternalStorageDirectory();
            File file = new File(ExternalStorageDirectory + File.separator + fileName);
            FileOutputStream fileOutputStream = null;
            try {
                file.createNewFile();
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bytes.toByteArray());
            } catch (IOException e) {

            } finally {
                if (fileOutputStream != null) {
                    Uri bmpUr = Uri.parse(file.getPath());
                    waIntent.putExtra(Intent.EXTRA_TEXT, text + Html.fromHtml("<br />") +
                            Html.fromHtml("<br />") +
                            "https://play.google.com/store/apps/details?id=com.games.user.agendaimss");
                    waIntent.putExtra(
                            Intent.EXTRA_STREAM,
                            bmpUr);
                    //context.startActivity(Intent.createChooser(waIntent,
                    //        "AgendaIMSS "));
                }
            }
            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            waIntent.setPackage("com.whatsapp");

            Toast.makeText(context, "Selecciona el contacto", Toast.LENGTH_LONG).show();
            context.startActivity(Intent.createChooser(waIntent, "Compartir con"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "WhatsApp no está instalado", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    @Override
    public void onClick(View v) {



        switch (v.getId()) {

            case R.id.botonwats:
                onClickWhatsApp(this, imagen, "Datos del Paciente:" +"\n"+"Fecha: "+ textfecha.getText().toString() +"\n"+
                        "Numero de historial: "+ historial.getText().toString()+"\n"+
                        "Gravedad del Paciente: "+ radiobutn+ "\n"+
                        "Nombre: "+nombre.getText().toString()+"\n"
                        +"Edad: "+ edad.getText().toString()+ "\n"+
                        "Alergia: "+ alergia.getText().toString()+ "\n"+
                        "Patologia: "+ patologia.getText().toString()+ "\n"+
                        "Herida: "+ herida.getText().toString()+ "\n"+
                        "Localizacion: "+ localizacion.getText().toString()+ "\n"+
                        "Motivo: "+ motivo.getText().toString()+ "\n"


                );



                break;


            case R.id.del_btn:

                AlertDialog.Builder builder = new AlertDialog.Builder(EditDiagnostic.this);

                builder.setTitle("  Confirmar  ");
                builder.setMessage("Estas seguro que deseas eliminar ?");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        ContactDiagnostic c = new ContactDiagnostic(getBaseContext());
                        c.open();
                        c.deleteContactDiagnostic(id);
                        finish();
                        dialog.dismiss();
                        Toast.makeText(getBaseContext(), "Elemento eliminado !!", Toast.LENGTH_LONG).show();
                        Intent intentds = new Intent(EditDiagnostic.this, MenuDiagnostic.class);
                        startActivity(intentds);

                        finish();

                    }

                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                break;


            case R.id.upd_element:
                if (textfecha.getText().toString().length() > 0) {

                    if (verde.isChecked()) {
                        radiobutn = "VERDE";
                    }
                    if (amarillo.isChecked()) {
                        radiobutn = "AMARILLO";
                    }
                    if (rojo.isChecked()) {
                        radiobutn = "ROJO";
                    }
                    ContactDiagnostic c = new ContactDiagnostic(getBaseContext());
                    c.open();
                    c.updateDiagnostic(id, Fecha, historial.getText().toString(), radiobutn, nombre.getText().toString(), edad.getText().toString(),
                            alergia.getText().toString(), patologia.getText().toString(), herida.getText().toString(), localizacion.getText().toString(),
                            motivo.getText().toString(),imagenid);

                    historial.setText("");
                    nombre.setText("");

                    edad.setText("");
                    alergia.setText("");
                    patologia.setText("");
                    herida.setText("");
                    localizacion.setText("");
                    motivo.setText("");

                    Toast.makeText(getBaseContext(), "Elemento Actualizado!!", Toast.LENGTH_LONG).show();
                    //se agrega metodo para pasar a nueva actividad
                    Intent intentds = new Intent(EditDiagnostic.this, MenuDiagnostic.class);
                    startActivity(intentds);
                    //se cierra actividdad actual
                    finish();


                } else {
                    Toast.makeText(getBaseContext(), "Error!!", Toast.LENGTH_LONG).show();
                }
                break;


        }
    }

}


