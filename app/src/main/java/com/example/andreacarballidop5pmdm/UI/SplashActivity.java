package com.example.andreacarballidop5pmdm.UI;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.andreacarballidop5pmdm.R;
import com.example.andreacarballidop5pmdm.core.Tarea;
import com.example.andreacarballidop5pmdm.database.TareaLab;

import java.util.List;

public class SplashActivity extends Activity {
    TareaLab tareaLab;

    private final int DURACTION_SPLASH= 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tareaLab = TareaLab.get(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },DURACTION_SPLASH);

    }
}