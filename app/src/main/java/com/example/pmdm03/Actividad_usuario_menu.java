package com.example.pmdm03;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pmdm03.persistencia.Usuario;


public class Actividad_usuario_menu extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_barra_accion_usuario,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.abFacerPedido:
                    lanzarActividadPedido();
                break;
            case R.id.abVerPedTram:
                    lanzarPedidsTramite();
                break;
            case R.id.abVerComReal:
                    lanzarComprasReal();
                break;
            case R.id.abSair:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==-1) {
            Toast resultadoPedido= Toast.makeText(this.getBaseContext(),data.getStringExtra("resultado"),Toast.LENGTH_LONG);
            resultadoPedido.show();
        }
    }

    Intent intentLogueo;
    Usuario usuarioLogueado;
    Intent lanzPedidsUser;
    Intent lanzadorActividadPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_usuario_menu);

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        intentLogueo=this.getIntent();
        usuarioLogueado =(Usuario)intentLogueo.getSerializableExtra("usuario");
        lanzPedidsUser=new Intent(this, PedidosUsuario.class);
        lanzadorActividadPedido=new Intent(this, Actividad_pedido_productos.class);

        getSupportActionBar().setTitle(usuarioLogueado.usuario);
        TextView nomeUsuario=findViewById(R.id.nomeusuario);

        nomeUsuario.setText(usuarioLogueado.nome+" "+usuarioLogueado.apelidos);


        Button facerPedido=findViewById(R.id.botonrealizarpedido);
        Button verPedidoTramite=findViewById(R.id.botonencurso);
        Button verCompras=findViewById(R.id.botoncompras);
        Button sair=findViewById(R.id.botonsair);

        facerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzarActividadPedido();
            }
        });

        verPedidoTramite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzarPedidsTramite();
            }
        });

        verCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzarComprasReal();
            }
        });

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void lanzarActividadPedido(){
        lanzadorActividadPedido.putExtra("usuario",usuarioLogueado);
        startActivityForResult(lanzadorActividadPedido,1);
    }
    private void lanzarPedidsTramite(){
        lanzPedidsUser.putExtra("usuario",usuarioLogueado);
        lanzPedidsUser.putExtra("estado","creado");
        startActivity(lanzPedidsUser);
    }
    private void lanzarComprasReal(){
        lanzPedidsUser.putExtra("usuario",usuarioLogueado);
        lanzPedidsUser.putExtra("estado","aceptado");
        startActivity(lanzPedidsUser);
    }
}
