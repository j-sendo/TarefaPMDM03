package com.example.pmdm03.persistencia;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UsuarioDao {
    @Query("SELECT * FROM Usuario WHERE usuario LIKE :user ")
    Usuario buscarPorId(String user);

    @Insert
    void engadirUsuario(Usuario a);
}
