package com.example.andreacarballidop5pmdm.UI;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.andreacarballidop5pmdm.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Locale;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback{
    private static final float DEFAULT_ZOOM = 12.0f;
    private final LatLng defaultLocation = new LatLng(40.416775, -3.703790);
    public FusedLocationProviderClient fusedLocationProviderClient;
    public LatLng localizacion;
    private Location lastLocation;
    private GoogleMap mMap;
    private double enviarLatitud;
    private double enviarLongitud;
    private Marker marker;
    String longitud;
    String latitud;
    private GoogleMap mapa;

    final int RQS_GooglePlayServices = 1;
    Button botonAdd;
    private FusedLocationProviderClient fusedLocationClient;
    double dlatitud;
    double dlongitud;
    String longitudMarker;
    String latitudMarker;

    private static final int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_maps);


        botonAdd = (Button) findViewById(R.id.botonMapa);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        latitud = getIntent().getStringExtra("latitud");
        longitud = getIntent().getStringExtra("longitud");
        botonAdd.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("latLngLatitud", latitudMarker);
            intent.putExtra("latLngLongitud", longitudMarker);
            setResult(RESULT_OK, intent);
            finish();
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        dlatitud = Double.parseDouble(latitud);
        dlongitud = Double.parseDouble(longitud);
        localizacion = new LatLng(dlatitud, dlongitud);
        mMap=googleMap;
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(localizacion, DEFAULT_ZOOM));
        marker = googleMap.addMarker(new MarkerOptions().position(localizacion).title("Mi ubicación"));
        /*googleMap.addMarker(new MarkerOptions()
                .position(localizacion)
                .title("Mi ubicación"));*/
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (marker != null) {
                    marker.setPosition(latLng);
                } else {
                    marker = googleMap.addMarker(new MarkerOptions().position(latLng).title("Mi ubicación"));
                }
                latitudMarker = String.valueOf(latLng.latitude);
                longitudMarker =String.valueOf(latLng.longitude);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
