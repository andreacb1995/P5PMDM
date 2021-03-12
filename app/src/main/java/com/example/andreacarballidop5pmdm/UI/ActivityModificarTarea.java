package com.example.andreacarballidop5pmdm.UI;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.andreacarballidop5pmdm.R;
import com.example.andreacarballidop5pmdm.core.Tarea;
import com.example.andreacarballidop5pmdm.database.TareaLab;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ActivityModificarTarea extends AppCompatActivity {

    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    String horaFormateada = "";
    String minutoFormateado = "";
    String longitud;
    String latitud;
    TareaLab tareaLab;
    List<Tarea> listTareas;
    String id;
    String textoTarea;
    String fecha;
    String hora;
    Tarea tareaModifico;
    Tarea tarea;

    String digitoRecordatorio ;
    String tiempoRecordatorio;

    double dLongitud;
    double dLatitud;

    private int h;
    private int m;
    private int yearNotificacion;
    private int monthNotificacion;
    private int dayNotificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tarea_nueva);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tareaLab = TareaLab.get(this);
        listTareas = tareaLab.getTareas();

        id= getIntent().getStringExtra("id");
        textoTarea = getIntent().getStringExtra("nombreTarea");
        fecha = getIntent().getStringExtra("fecha");
        hora = getIntent().getStringExtra("hora");
        longitud = getIntent().getStringExtra("longitud");
        latitud = getIntent().getStringExtra("latitud");

        tareaModifico=tareaLab.getTarea(id);

        modificarTarea();

    }


    public void modificarTarea() {
        TextView tvFecha = findViewById(R.id.tvFecha);
        TextView tvHora= findViewById(R.id.tvHora);
        EditText tvTarea = findViewById(R.id.edTarea);

        Button botonguardar= findViewById(R.id.boton_guardar);
        ImageButton imagenRecordatorio= findViewById(R.id.imagenAddrecordatorio);
        ImageButton imagenUbicacion= findViewById(R.id.imagenAddUbicacion);
        final Calendar calendar = Calendar.getInstance();
        final Calendar calendarHora = Calendar.getInstance();

        tvFecha.setText(fecha);
        tvHora.setText(hora);
        tvTarea.setText(textoTarea);

        imagenRecordatorio.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                Intent intent = new Intent(getApplicationContext(), ActivityAddRecordatorio.class);
                startActivityForResult(intent,0);

            }
        });

        imagenUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityModificarTarea.this, Mapa.class);
                intent.putExtra("latitud",latitud );
                intent.putExtra("longitud", longitud );
                startActivityForResult(intent,1);
            }
        });

        tvFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                final DatePickerDialog datePicker = new DatePickerDialog(ActivityModificarTarea.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        calendar.set(Calendar.YEAR, selectedYear);
                        calendar.set(Calendar.MONTH, selectedMonth);
                        calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                        yearNotificacion = selectedYear;
                        monthNotificacion = selectedMonth;
                        dayNotificacion = selectedDay;
                        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd MMMM 'de' yyyy", Locale.getDefault());
                        tvFecha.setText(formatoFecha.format(calendar.getTime()));
                    }
                }, year, month, day);

                datePicker.show();
            }
        });
        tvHora.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int hour = calendarHora.get(Calendar.HOUR_OF_DAY);
                int minutes = calendarHora.get(Calendar.MINUTE);

                final TimePickerDialog tpd = new TimePickerDialog(ActivityModificarTarea.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int hora, int minutos) {

                        calendarHora.set(Calendar.HOUR_OF_DAY, hora);
                        calendarHora.set(Calendar.MINUTE, minutos);
                        h = hora;
                        m = minutos;
                        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        tvHora.setText(formatoHora.format(calendarHora.getTime()));
                    }
                }, hour, minutes, true);
                tpd.show();
            }
        });


        botonguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tTarea = tvTarea.getText().toString();

                if ( tvFecha.getText().toString().length()<=0 && tvHora.getText().toString().length()<=0 &&tTarea.equals("")){
                    Toast.makeText(ActivityModificarTarea.this, "No se permiten los campos vacíos", Toast.LENGTH_SHORT).show();
                    return;

                }
                if(tvFecha.getText().toString().length()<=0){
                    Toast.makeText(ActivityModificarTarea.this, "Debe escoger una fecha", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tvHora.getText().toString().length()<=0){
                    Toast.makeText(ActivityModificarTarea.this, "Debe escoger una hora", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tTarea.equals("")){

                    Toast.makeText(ActivityModificarTarea.this,  "Campo de la tarea vacío", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(dLatitud !=0 && dLongitud !=0){
                    tareaModifico.modificarTarea(tTarea,calendar.getTime(),calendarHora.getTime(),dLatitud,dLongitud);


                }

                Double lA= tareaModifico.getLatitud();
                Double lO = tareaModifico.getLongitud();
                tareaModifico.modificarTarea(tTarea,calendar.getTime(),calendarHora.getTime(),lA,lO);
                String idChannel = tareaModifico.getTextotarea() + "_tarea";
                createNotificationChannel(idChannel);
                añadirNotificacionTarea(tareaModifico, idChannel);
                if (digitoRecordatorio != null){
                    añadirRecordatorio(tareaModifico, idChannel);
                }

                tareaLab.updateTarea(tareaModifico);
                PrimerFragment.PrimerFragment.actualizarLista();
                Toast.makeText(ActivityModificarTarea.this, "Tarea modificada correctamente", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }

    private void añadirNotificacionTarea(Tarea tarea,String idChannel){
        int requestCode = new Random().nextInt(100);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND , 0 );
        cal.set(Calendar.MINUTE , m);
        cal.set(Calendar.HOUR_OF_DAY ,h);

        cal.set(Calendar.YEAR , yearNotificacion );
        cal.set(Calendar.MONTH , monthNotificacion);
        cal.set(Calendar.DAY_OF_MONTH , dayNotificacion );

        Intent intent = new Intent(ActivityModificarTarea.this,ReminderBroadcast.class);

        String texto= "La tarea: "+ tarea.getTextoTarea() + " ha expirado";
        intent.putExtra("texto", texto);
        intent.putExtra("idChannel", idChannel);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ActivityModificarTarea.this,requestCode,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }


    private void añadirRecordatorio(Tarea tarea,String idChannel){
        int requestcode = new Random().nextInt(100);
        int minutos=0;
        int horas=0;
        int dias=0;
        int digito=0;
        digito=Integer.parseInt(digitoRecordatorio);


        if(tiempoRecordatorio.equals("minutos")){
            minutos+= digito;
        }
        if(tiempoRecordatorio.equals("horas")){
            horas+=digito;
        }
        if(tiempoRecordatorio.equals("dias")){
            dias+=digito;
        }

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND , 0 );
        cal.set(Calendar.MINUTE , minutos - m);
        cal.set(Calendar.HOUR_OF_DAY ,horas - h);

        cal.set(Calendar.YEAR , yearNotificacion );
        cal.set(Calendar.MONTH , monthNotificacion);
        cal.set(Calendar.DAY_OF_MONTH , dayNotificacion - dias);
        Toast.makeText(getApplicationContext(),"Recordatorio añadido",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ActivityModificarTarea.this,ReminderBroadcast.class);
        String texto= "Aviso: "+ tarea.getTextoTarea();
        intent.putExtra("texto", texto);
        intent.putExtra("idChannel", idChannel);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ActivityModificarTarea.this,requestcode,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }

    private void createNotificationChannel(String idChannel) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name="notificationTareas" + idChannel;
            String description = "Canal de notificaciones para las tareas";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(idChannel,name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0   &&   resultCode == RESULT_OK) {


            digitoRecordatorio = data.getStringExtra("digito");
            tiempoRecordatorio = data.getStringExtra("tiempo");
            Toast.makeText(getApplicationContext(),"Recordatorio tarea: " + digitoRecordatorio + " " + tiempoRecordatorio +

                    " antes de que expire la tarea",Toast.LENGTH_SHORT).show();


        }
        if (requestCode == 1   &&   resultCode == RESULT_OK){

            String la = data.getStringExtra("latLngLatitud");
            String lo= data.getStringExtra("latLngLongitud");
            Double dla = Double.parseDouble(la);
            Double dlo = Double.parseDouble(lo);

            dLatitud = Double.parseDouble(la);
            dLongitud = Double.parseDouble(lo);


        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

}

