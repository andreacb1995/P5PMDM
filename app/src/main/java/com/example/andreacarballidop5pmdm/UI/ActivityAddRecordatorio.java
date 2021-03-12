package com.example.andreacarballidop5pmdm.UI;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.andreacarballidop5pmdm.R;
import com.example.andreacarballidop5pmdm.database.TareaLab;

public class ActivityAddRecordatorio extends AppCompatActivity {
    private static final String NOTIFICATION_CHANNEL_ID = "0" ;
    Button boton_guardar;
    RadioButton minutos,horas,dias;
    EditText numero;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrecordatorio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        boton_guardar = findViewById(R.id.botonRecordatorio);
        numero = findViewById(R.id.numeroRecordatorio);
        minutos = findViewById(R.id.minutos_RadioButton);
        horas = findViewById(R.id.horas_radioButton);
        dias = findViewById(R.id.dias_RadioButton);


        boton_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                String digito = numero.getText().toString();
                intent.putExtra("digito", digito);
                if(minutos.isChecked()){
                    intent.putExtra("tiempo", "minutos");
                }
                if(horas.isChecked()){
                    intent.putExtra("tiempo","horas");

                }
                if(dias.isChecked()){
                    intent.putExtra("tiempo","dias");
                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }
/*    public void addNotificacion(){

        int digito = numero.getText().length();

        if(digito != 0 && minutos.isChecked()){
            createNotificationChannel();
        }
        if(digito != 0 && horas.isChecked()){
            createNotificationChannel();
        }
        if(digito != 0 && dias.isChecked()){
            createNotificationChannel();
        }
    }*/


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
