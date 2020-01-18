package com.example.pmdm03;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pmdm03.persistencia.BdInteraccion;
import com.example.pmdm03.persistencia.Usuario;

public class ActividadRexistro extends AppCompatActivity {
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_rexistro);


        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Intent actChamante=this.getIntent();
        final BdInteraccion bd=BdInteraccion.get(this.getApplicationContext());

        final EditText introUser=this.findViewById(R.id.introUser);
        final EditText introContra=this.findViewById(R.id.introContra);
        final EditText introNome=this.findViewById(R.id.introNome);
        final EditText introApel=this.findViewById(R.id.introApel);
        final EditText introEmail=this.findViewById(R.id.introEmail);
        final RadioGroup adminSel=this.findViewById(R.id.radioGroupTipoUser);

        Button botRexistrar=this.findViewById(R.id.botonAceptar);

        botRexistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringUsuario, stringContra, stringNome, stringApel, stringEmail;

                boolean bolAdmin = false;
                stringUsuario = introUser.getText().toString();
                stringContra = introContra.getText().toString();
                stringNome = introNome.getText().toString();
                stringApel = introApel.getText().toString();
                stringEmail = introEmail.getText().toString();
                if (adminSel.getCheckedRadioButtonId() == R.id.radioButAdmin) bolAdmin = true;

                if (stringUsuario.isEmpty() || stringContra.isEmpty() || stringNome.isEmpty() || stringApel.isEmpty() || stringEmail.isEmpty())
                    Toast.makeText(view.getContext(), "ERRO. Debe cubrir todos os campos", Toast.LENGTH_LONG).show();
                else {
                    if (stringEmail.matches(Patterns.EMAIL_ADDRESS.pattern())) {
                        Usuario introUser = new Usuario(stringUsuario, stringContra, stringNome, stringApel, stringEmail, bolAdmin);
                        try {
                            bd.insertarUsuario(introUser);
                            actChamante.putExtra("usuarioCreado", stringUsuario);
                            setResult(RESULT_OK, actChamante);
                            finish();
                        } catch (SQLiteConstraintException e) {
                            AlertDialog.Builder alertaUserExist = new AlertDialog.Builder(ActividadRexistro.this, 0);

                            alertaUserExist.setTitle("Erro");
                            alertaUserExist.setMessage("O usuario indicado xa existe");
                            alertaUserExist.setPositiveButton("ok", null);
                            alertaUserExist.show();
                        }
                    } else Toast.makeText(view.getContext(), "ERRO. Formato de email incorrecto", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
