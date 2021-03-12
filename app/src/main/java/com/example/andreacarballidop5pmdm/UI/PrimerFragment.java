package com.example.andreacarballidop5pmdm.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andreacarballidop5pmdm.R;
import com.example.andreacarballidop5pmdm.core.Tarea;
import com.example.andreacarballidop5pmdm.database.TareaLab;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrimerFragment  extends Fragment implements AccionesTarea{
    public static PrimerFragment PrimerFragment;
    private RecyclerView recyclerView;
    List<Tarea> tareas;
    List<Tarea> listaTareas;
    MyRecyclerViewAdapter adapter;
    private TareaLab tareaLab;
    List<Tarea> tareasSeleccionadas;
    List<Tarea> tareasFavoritas;
    List<Tarea> tareasFinalizadas;
    ArrayList<Tarea> tareasFinalizadasOnResume;

    LinearLayout myToolbar;


    public PrimerFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment1, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvFragmento1);
        PrimerFragment = this;
        tareasSeleccionadas = new ArrayList<>();
        tareasFavoritas= new ArrayList<>();
        tareasFinalizadas=new ArrayList<>();
        listaTareas  = new ArrayList<>();
        actualizarListados();


        return rootView;

    }

    /*Método que se ejecutará cuando la Activity del Fragment esté completamente
    creada.*/
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        myToolbar = getView().findViewById(R.id.toolbar1);
        myToolbar.setVisibility(View.GONE);
        tareasSeleccionadas = new ArrayList<>();

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void actualizarListados() {
        tareaLab = TareaLab.get(getContext());

        listaTareas = tareaLab.getTareas();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyRecyclerViewAdapter(listaTareas,this,getContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void eliminarTarea(Tarea tarea) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Borrar Elemento");
        builder.setMessage("Está seguro de que desea eliminar este elemento?\n\n" + tarea.getTextoTarea());
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                tareas.remove(tarea);
                tareaLab.deleteTarea(tarea);
//                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("No", null);
        builder.create().show();


    }

    @Override
    public void tareaSeleccionada(Tarea tarea) {

    }

    @Override
    public void añadirTareaFinalizada(Tarea tarea) {
        tarea.setFin(true);
        tareasFinalizadas.add(tarea);
        tareaLab.updateTarea(tarea);
//        listaTareas.remove(tarea);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void eliminarTareaFinalizada(Tarea tarea) {
        tarea.setFin(false);
        tareasFinalizadas.remove(tarea);
        tareaLab.updateTarea(tarea);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void añadirTareaFavorita(Tarea tarea) {
        tarea.setFav(true);
        tareasFavoritas.add(tarea);
        tareaLab.updateTarea(tarea);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void eliminarTareaFavorita(Tarea tarea) {
        tarea.setFav(false);
        tareasFavoritas.remove(tarea);
        tareaLab.updateTarea(tarea);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void actualizarListaTareas(Tarea tarea) {
        tareaLab.addTarea(tarea);
        listaTareas.add(tarea);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void modificarTarea(Tarea tarea) {
        Intent intent = new Intent(getContext(), ActivityModificarTarea.class);
        if (tarea != null) {
            String id= String.valueOf(tarea.getId()) ;
            Tarea t = tareaLab.tareaId(id);
            intent.putExtra("id", id);
            intent.putExtra("nombreTarea", tarea.getTextoTarea());
            intent.putExtra("fecha", tarea.getFormatoFecha());
            intent.putExtra("hora", tarea.getFormatoHora());
            intent.putExtra("latitud", String.valueOf(tarea.getLatitud()));
            intent.putExtra("longitud", String.valueOf(tarea.getLongitud()));
        }
        startActivity(intent);
    }

    @Override
    public void actualizarLista() {
        tareaLab = TareaLab.get(getContext());

        listaTareas = tareaLab.getTareas();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyRecyclerViewAdapter(listaTareas,this,getContext());
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void eliminarTareas() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Eliminar tareas");
        ArrayList<String> listatareasfinalizadas = new  ArrayList<String>();
        String[] stringtareas = new String[tareasSeleccionadas.size()];
        String tareaseliminar = null;
        final boolean[] tareaseleccion = new boolean[tareasSeleccionadas.size()];

        for (int i = 0; i < tareasSeleccionadas.size(); i++) {
            stringtareas[i] = "Tarea: " + tareasSeleccionadas.get(i).getTextotarea() + "\nFecha: "
                    + tareasSeleccionadas.get(i).getFormatoFecha();
            tareaseliminar =  stringtareas[i];
        }
        builder.setMultiChoiceItems(stringtareas, tareaseleccion, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i, boolean isChecked) {
                tareaseleccion[i] = isChecked;
            }
        });
        final String listaTareasElimino = tareaseliminar;
        builder.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("¿Eliminar el elemento?");
                builder.setMessage(listaTareasElimino);
                AlertDialog.Builder buildereliminar = new AlertDialog.Builder(getContext());
                buildereliminar.setMessage("¿Eliminar los elementos?");
                buildereliminar.setNegativeButton("Cancelar", null);
                buildereliminar.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = tareasSeleccionadas.size() - 1; i >= 0; i--) {
                            if (tareaseleccion[i]) {
                                listaTareas.remove(tareasSeleccionadas.get(i));
                                TareaLab.get(getContext()).deleteTarea(tareasSeleccionadas.get(i));

                            }
                        }
                        Toast.makeText(getActivity(), "Tareas eliminadas correctamente", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                });

                buildereliminar.create().show();
            }
        });


        builder.setPositiveButton("Cancelar", null);
        builder.create().show();
    }


}
