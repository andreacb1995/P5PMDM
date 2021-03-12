package com.example.andreacarballidop5pmdm.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.andreacarballidop5pmdm.R;
import com.example.andreacarballidop5pmdm.core.Tarea;
import com.example.andreacarballidop5pmdm.database.TareaLab;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, AccionesTarea {

    private boolean viewIsAtHome;

    List<Tarea> listaTareas;
    List<Tarea> tareas;
    ArrayList<Tarea> tareasfinalizadas;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    TareaLab tareaLab;



    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setSupportActionBar(myToolbar);
//        myToolbar.setVisibility(View.GONE);
        //loading the default fragment

        loadFragment(new PrimerFragment());
        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        tareaLab = TareaLab.get(this);

        listaTareas = tareaLab.getTareas();

        FloatingActionButton btAdd = findViewById(R.id.btAdd);



        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityAddTarea.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.crearTarea) {
//            PrimerFragment.PrimerFragment.addTarea();

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.tareasRealizar:
                fragment = new PrimerFragment();
                break;

            case R.id.tareasImportantes:
                fragment = new SegundoFragment();
                break;

            case R.id.tareasFinalizadas:
                fragment = new TercerFragment();
                break;

        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }




    @Override
    public void eliminarTarea(Tarea tarea) {

    }

    @Override
    public void tareaSeleccionada(Tarea tarea) {

    }

    @Override
    public void añadirTareaFinalizada(Tarea tarea) {

    }

    @Override
    public void eliminarTareaFinalizada(Tarea tarea) {

    }

    @Override
    public void añadirTareaFavorita(Tarea tarea) {

    }

    @Override
    public void eliminarTareaFavorita(Tarea tarea) {

    }

    @Override
    public void actualizarListaTareas(Tarea tarea) {

    }

    @Override
    public void modificarTarea(Tarea tarea) {

    }

    @Override
    public void actualizarLista() {

    }


    @Override
    public void onResume() {
        super.onResume();
        /*TareaLab tareaLab = TareaLab.get(this);
        List<Tarea> listaTareas = tareaLab.getTareas();

        ArrayList<Tarea> tareasFinalizadasOnResume = new ArrayList<>();

        for (Tarea t : listaTareas) {
            if (t.getFecha().compareTo(new Date()) < 0) {
                tareasFinalizadasOnResume.add(t);
            }
        }
        if (!tareasFinalizadasOnResume.isEmpty()) {
            tareasCaducadas(tareasFinalizadasOnResume);
        }*/
    }
    private void tareasCaducadas(final List<Tarea> tareasfinalizadas) {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Tareas Finalizadas");
        ArrayList<String> listatareasfinalizadas = new  ArrayList<String>();
        String[] stringtareas = new String[tareasfinalizadas.size()];
        String tareaseliminar = null;
        final boolean[] tareaseleccion = new boolean[tareasfinalizadas.size()];

        for (int i = 0; i < tareasfinalizadas.size(); i++) {
            stringtareas[i] = "Tarea: " + tareasfinalizadas.get(i).getTextotarea() + "\nFecha: "
                    + tareasfinalizadas.get(i).getFormatoFecha();
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
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
                builder.setTitle("¿Eliminar el elemento?");
                builder.setMessage(listaTareasElimino);
                androidx.appcompat.app.AlertDialog.Builder buildereliminar = new AlertDialog.Builder(MainActivity.this);
                buildereliminar.setMessage("¿Eliminar los elementos?");
                buildereliminar.setNegativeButton("Cancelar", null);
                buildereliminar.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = tareasfinalizadas.size() - 1; i >= 0; i--) {
                            if (tareaseleccion[i]) {
                                listaTareas.remove(tareasfinalizadas.get(i));
                                TareaLab.get(MainActivity.this).deleteTarea(tareasfinalizadas.get(i));
                            }
                        }
                        Toast.makeText(MainActivity.this, "Tareas eliminadas correctamente", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                });

                buildereliminar.create().show();
            }
        });
        builder.setPositiveButton("Añadir a tareas finalizadas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                for (int i = tareasfinalizadas.size() - 1; i >= 0; i--) {
                    if (tareaseleccion[i]) {
                        añadirTareaFinalizada(tareasfinalizadas.get(i));
                    }
                }
                Toast.makeText(MainActivity.this, "Tareas añadidas a la lista de tareas finalizadas", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNeutralButton("Cancelar", null);
        builder.create().show();
    }


}

