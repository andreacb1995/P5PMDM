package com.example.andreacarballidop5pmdm.UI;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andreacarballidop5pmdm.R;
import com.example.andreacarballidop5pmdm.core.Tarea;
import com.example.andreacarballidop5pmdm.database.TareaLab;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private static List<Tarea> items;

    private AccionesTarea accionesTarea;

    public MyRecyclerViewAdapter(List<Tarea> items, AccionesTarea accionesTarea,Context context) {
        this.items=items;
        this.accionesTarea=accionesTarea;
        this.context = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AccionesTarea accionesTarea;
        // Campos respectivos de un item
        private CardView cv;
        private TextView fecha;
        private TextView hora;
        private TextView textotarea;
        private TextView coordenadas;
        private TextView coma;
        private ImageView imagenMenuContextual;
        private ImageView imagenUbicacion;
        private ImageView imagencheckbox;
        private ImageView imagencheckboxMarcado;
        private ImageView imagenfavorito;
        private ImageView imagenfavoritoRelleno;

        private  List<Tarea> tareasFavoritas;
        private List<Tarea> tareasFinalizadas;
        private ConstraintLayout constraintLayout;

        public ViewHolder(View v, AccionesTarea accionesTarea) {
            super(v);

            cv= (CardView) v.findViewById(R.id.cv);
            fecha = (TextView) v.findViewById(R.id.tv_card_Fecha);
            hora = (TextView) v.findViewById(R.id.tv_card_Hora);
            textotarea = (TextView) v.findViewById(R.id.tv_card_Tarea);
            coma = (TextView) v.findViewById(R.id.coma);
            coordenadas= (TextView) v.findViewById(R.id.tv_card_Ubicacion);
            imagenUbicacion= (ImageButton)v.findViewById(R.id.imagenubicacion);
            imagencheckbox= (ImageButton)v.findViewById(R.id.imagencheckbox);
            imagencheckboxMarcado= (ImageButton)v.findViewById(R.id.imagencheckboxMarcado);
            imagenfavorito= (ImageButton)v.findViewById(R.id.imagenfavorito);
            imagenfavoritoRelleno= (ImageButton)v.findViewById(R.id.imagenfavoritoRelleno);
            imagenMenuContextual= (ImageButton)v.findViewById(R.id.menuContextual);
            constraintLayout = v.findViewById(R.id.constraintTarea);
            this.accionesTarea = accionesTarea;

            tareasFavoritas= new ArrayList<>();
            tareasFinalizadas= new ArrayList<>();


        }

        public void mostrarTarea(final Tarea tarea,Context context) {

            fecha.setText(tarea.getFormatoFecha());
            textotarea.setText(tarea.getTextotarea());
            hora.setText(tarea.getFormatoHora());
            if(tarea.getLongitud() != 0 && tarea.getLongitud()!=0){
                coordenadas.setText("Ver mapa");

            }else{
                imagenUbicacion.setVisibility(View.INVISIBLE);
                coordenadas.setVisibility(View.INVISIBLE);
            }

            coma.setText(",");


            imagenfavorito.setVisibility(tarea.isFav() ? View.INVISIBLE : View.VISIBLE);
            imagenfavoritoRelleno.setVisibility(tarea.isFav() ? View.VISIBLE : View.INVISIBLE);

            imagencheckbox.setVisibility(tarea.isFin() ? View.INVISIBLE : View.VISIBLE);
            imagencheckboxMarcado.setVisibility(tarea.isFin() ? View.VISIBLE : View.INVISIBLE);


            imagencheckbox.setOnClickListener(v -> {
                imagencheckboxMarcado.setVisibility(View.VISIBLE);
                imagencheckbox.setVisibility(View.INVISIBLE);
                accionesTarea.añadirTareaFinalizada(tarea);
                tareasFinalizadas.add(tarea);


            });
            imagencheckboxMarcado.setOnClickListener(v -> {
                imagencheckbox.setVisibility(View.VISIBLE);
                imagencheckboxMarcado.setVisibility(View.INVISIBLE);
                accionesTarea.eliminarTareaFinalizada(tarea);
                tareasFinalizadas.remove(tarea);

            });

            imagenfavorito.setOnClickListener(v -> {
                imagenfavorito.setVisibility(View.INVISIBLE);
                imagenfavoritoRelleno.setVisibility(View.VISIBLE);
                accionesTarea.añadirTareaFavorita(tarea);
                tareasFavoritas.add(tarea);

            });

            imagenfavoritoRelleno.setOnClickListener(v -> {
                imagenfavoritoRelleno.setVisibility(View.INVISIBLE);
                imagenfavorito.setVisibility(View.VISIBLE);
                accionesTarea.eliminarTareaFavorita(tarea);
                tareasFavoritas.remove(tarea);

            });

            coordenadas.setOnClickListener(v -> {
                Intent intent = new Intent(context, Mapa.class);
                intent.putExtra("latitud",String.valueOf(tarea.getLatitud()));
                intent.putExtra("longitud",String.valueOf(tarea.getLongitud()));
                context.startActivity(intent);
            });

            /*imagenMenuContextual.setOnClickListener(v -> {
                PopupMenu popup = new PopupMenu(itemView.getContext(), itemView);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.modificarTarea:
                                accionesTarea.modificarTarea(tarea);

                            case R.id.eliminarTarea:
                                accionesTarea.eliminarTarea(tarea);
//                                Snackbar snackbar = Snackbar.make(itemView, "Tarea eliminada", Snackbar.LENGTH_LONG);
                               *//* cv.setVisibility(View.GONE);
                                snackbar.setAction("Deshacer", new View.OnClickListener() {
                                @Override
                                    public void onClick(View v) {
                                    cv.setVisibility(View.VISIBLE);
                                     }
                                });*//*
                   *//* /*snackbar.addCallback(new Snackbar.Callback() {
                        @Override
                        public void onShown(Snackbar sb) {
                            super.onShown(sb);
                        }

                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);

                            if (event != DISMISS_EVENT_ACTION) {
                                if(cv.getVisibility() == View.GONE) {
                                    accionesTarea.eliminarTarea(tarea);
                                }
                            }
                        }
                    });*//*
//                    snackbar.show();


                        }
                        return false;
                    }
                });
                // here you can inflate your menu
                popup.inflate(R.menu.context_menu);
                popup.setGravity(Gravity.RIGHT);
                popup.show();
            });*/
        }



    }


//


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_tarea, viewGroup, false);
        ViewHolder pvh = new ViewHolder(v,accionesTarea);
        return pvh;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.mostrarTarea(items.get(i),context);
    }

//    public MyRecyclerViewAdapter(MainActivity mainActivity, ArrayList<Tarea> items) {
//        this.items = items;
//    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


   /* @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
        // hold position in view item
        int pos = viewHolder.getAdapterPosition();
        view.setTag(pos);
        communicator.remove(pos);
        recyclerViewAdapter.notifyItemRemoved(pos);
        //Snackbar.make(view, R.string.notice_removed, Snackbar.LENGTH_SHORT).show();
        Snackbar.make(view, R.string.notice_removed, Snackbar.LENGTH_LONG)
                .setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        //get position from view item of snackbar that you set it before.
                        int position = (int)snackbar.getView().getTag();
                        switch (event) {
                            case Snackbar.Callback.DISMISS_EVENT_ACTION:
                                recyclerViewAdapter.notifyItemInserted(position);
                                break;
                            default:
                                communicator.remove(position);
                                break;
                        }
                    }
                })
                .setAction(R.string.action_undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // do nothing
                    }
                })
                .show();

        recyclerViewAdapter.notifyItemRangeChanged(pos, recyclerViewAdapter.getItemCount());
    }*/
}

