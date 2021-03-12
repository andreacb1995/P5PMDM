package com.example.andreacarballidop5pmdm.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.andreacarballidop5pmdm.core.Tarea;

import java.util.List;

@Dao
public interface TareaDao {

    @Query("SELECT * FROM tarea ORDER BY fecha")
    List<Tarea> getTareas();

    //@Query("SELECT * FROM tarea WHERE esFav = 1")
    //List<Tarea> getTareasImportantes();

    //@Query("SELECT * FROM tarea WHERE finalizado = 1")
    //List<Tarea> getTareasFinalizadas();
    @Query("SELECT * FROM tarea WHERE id LIKE :uuid")
    Tarea getTarea(String uuid);


    @Insert
    void add(Tarea tarea);

    @Delete
    void delete(Tarea tarea);

    @Update
    void update(Tarea tarea);

    @Query("SELECT * FROM tarea WHERE id == :id")
   Tarea tareaId(String id);
}

