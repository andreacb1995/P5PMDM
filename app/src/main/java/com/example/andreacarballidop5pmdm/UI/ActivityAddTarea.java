package com.example.andreacarballidop5pmdm.UI;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.andreacarballidop5pmdm.R;
import com.example.andreacarballidop5pmdm.core.Tarea;
import com.example.andreacarballidop5pmdm.database.TareaLab;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public class ActivityAddTarea extends AppCompatActivity{

    String longitud;
    String latitud;
    TareaLab tareaLab;
    List<Tarea> listTareas;
    TextView coordenadas;
    double dlatitud;
    double dlongitud;
    String digitoRecordatorio ;
    String tiempoRecordatorio;
    int PERMISSION_ID = 1;

    private FusedLocationProviderClient fusedLocationClient;
    private Location posicionUsuario;
    final Calendar calendar = Calendar.getInstance();
    final Calendar calendarHora = Calendar.getInstance();

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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        pedirPosicionUsuario();
        añadirTarea();
    }


    public void añadirTarea() {
        TextView tvFecha = findViewById(R.id.tvFecha);
        TextView tvHora= findViewById(R.id.tvHora);
        EditText tvTarea = findViewById(R.id.edTarea);

        Button botonguardar= findViewById(R.id.boton_guardar);
        ImageButton imagenRecordatorio= findViewById(R.id.imagenAddrecordatorio);
        ImageButton imagenUbicacion= findViewById(R.id.imagenAddUbicacion);


        imagenRecordatorio.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                Intent intent = new Intent(getApplicationContext(), ActivityAddRecordatorio.class);
                startActivityForResult(intent,0);

            }
        });

        imagenUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAddTarea.this, Mapa.class);
                intent.putExtra("latitud",String.valueOf(posicionUsuario.getLatitude()) );
                intent.putExtra("longitud", String.valueOf(posicionUsuario.getLongitude()) );
                startActivityForResult(intent,1);
            }
        });

        tvFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                final DatePickerDialog datePicker = new DatePickerDialog(ActivityAddTarea.this, new DatePickerDialog.OnDateSetListener() {

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

                final TimePickerDialog tpd = new TimePickerDialog(ActivityAddTarea.this, new TimePickerDialog.OnTimeSetListener() {

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
                    Toast.makeText(ActivityAddTarea.this, "No se permiten los campos vacíos", Toast.LENGTH_SHORT).show();
                    return;

                }
                if(tvFecha.getText().toString().length()<=0){
                    Toast.makeText(ActivityAddTarea.this, "Debe escoger una fecha", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tvHora.getText().toString().length()<=0){
                    Toast.makeText(ActivityAddTarea.this, "Debe escoger una hora", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tTarea.equals("")){

                    Toast.makeText(ActivityAddTarea.this,  "Campo de la tarea vacío", Toast.LENGTH_SHORT).show();
                    return;
                }

                Tarea tarea = new Tarea(tTarea,calendar.getTime(),calendarHora.getTime(),posicionUsuario.getLatitude(),posicionUsuario.getLongitude());

                Toast.makeText(ActivityAddTarea.this, "Tarea añadida", Toast.LENGTH_SHORT).show();
                String idChannel = tarea.getTextotarea() + "_tarea";
                createNotificationChannel(idChannel);
                añadirNotificacionTarea(tarea, idChannel);
                if (digitoRecordatorio != null){
                    añadirRecordatorio(tarea, idChannel);
                }

                PrimerFragment.PrimerFragment.actualizarListaTareas(tarea);
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

        Intent intent = new Intent(ActivityAddTarea.this,ReminderBroadcast.class);

        String texto= "La tarea: "+ tarea.getTextoTarea() + " ha expirado";
        intent.putExtra("texto", texto);
        intent.putExtra("idChannel", idChannel);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ActivityAddTarea.this,requestCode,intent,PendingIntent.FLAG_UPDATE_CURRENT);

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
        Intent intent = new Intent(ActivityAddTarea.this,ReminderBroadcast.class);
        String texto= "Aviso: "+ tarea.getTextoTarea();
        intent.putExtra("texto", texto);
        intent.putExtra("idChannel", idChannel);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ActivityAddTarea.this,requestcode,intent,PendingIntent.FLAG_UPDATE_CURRENT);

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
            Double dLa = Double.parseDouble(la);
            Double dLo = Double.parseDouble(lo);
            posicionUsuario.setLatitude(dLa);
            posicionUsuario.setLongitude(dLo);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }


    private void pedirPosicionUsuario() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
        } else
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                posicionUsuario = location;
                            }
                        }
                    });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pedirPosicionUsuario();
            }
    }

}