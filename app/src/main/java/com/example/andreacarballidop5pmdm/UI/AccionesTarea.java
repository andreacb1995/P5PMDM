package com.example.andreacarballidop5pmdm.UI;

import com.example.andreacarballidop5pmdm.core.Tarea;

public interface AccionesTarea {

    void eliminarTarea(Tarea tarea);

    void tareaSeleccionada(Tarea tarea);

    void a├▒adirTareaFinalizada(Tarea tarea);

    void eliminarTareaFinalizada(Tarea tarea);

    void a├▒adirTareaFavorita(Tarea tarea);

    void eliminarTareaFavorita(Tarea tarea);
    void actualizarListaTareas(Tarea tarea);

    void modificarTarea(Tarea tarea);
    void actualizarLista();
}

