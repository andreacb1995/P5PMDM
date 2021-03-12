package com.example.andreacarballidop5pmdm.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.andreacarballidop5pmdm.core.DateConverter;
import com.example.andreacarballidop5pmdm.core.Tarea;

public class TareaDataBase {
    @Database(entities = {Tarea.class}, version = 1, exportSchema = false)
    @TypeConverters(DateConverter.class)
    public static abstract class AppDatabase extends RoomDatabase {
        public abstract TareaDao getTareaDao();
    }
}
