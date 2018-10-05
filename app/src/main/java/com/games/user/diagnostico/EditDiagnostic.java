package com.games.user.diagnostico;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditDiagnostic extends AppCompatActivity implements View.OnClickListener {
    EditText txthistoria, txtedad, txtnombre, txtalergia, etxtgravedad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_diagnostic);

    }

    @Override
    public void onClick(View v) {

    }
}
