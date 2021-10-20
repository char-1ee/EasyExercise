//package com.example.myapplication.getUserLocation;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentSender;
//import android.content.pm.PackageManager;
//import android.location.LocationManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Looper;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import com.example.myapplication.beans.Coordinates;
//import com.example.myapplication.ui.activities.MainActivity;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.common.api.ResolvableApiException;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.location.LocationSettingsResponse;
//import com.google.android.gms.location.LocationSettingsStatusCodes;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//
//public class location {
//    private LocationRequest locationRequest;
//
//    public void getCurrentLocation(){
//        locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(5000);
//        locationRequest.setFastestInterval(2000);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
//                if (isGPSEnabled()) {
//
//                    LocationServices.getFusedLocationProviderClient(MainActivity.this)
//                            .requestLocationUpdates(locationRequest, new LocationCallback() {
//                                @Override
//                                public void onLocationResult(@NonNull LocationResult locationResult) {
//                                    super.onLocationResult(locationResult);
//
//                                    LocationServices.getFusedLocationProviderClient(MainActivity.this)
//                                            .removeLocationUpdates(this);
//
//                                    if (locationResult != null && locationResult.getLocations().size() >0){
//
//                                        int index = locationResult.getLocations().size() - 1;
//                                        double latitude = locationResult.getLocations().get(index).getLatitude();
//                                        double longitude = locationResult.getLocations().get(index).getLongitude();
//
//                                        AddressText.setText("Latitude: "+ latitude + "\n" + "Longitude: "+ longitude);
//                                    }
//                                }
//                            }, Looper.getMainLooper());
//
//                } else {
//                    turnOnGPS();
//                }
//
//            } else {
//                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//            }
//        }
//    }
//
//    private void turnOnGPS() {
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(locationRequest);
//        builder.setAlwaysShow(true);
//
//        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
//                .checkLocationSettings(builder.build());
//
//        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
//            @Override
//            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
//
//                try {
//                    LocationSettingsResponse response = task.getResult(ApiException.class);
//                    //Toast.makeText(MainActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();
//
//                } catch (ApiException e) {
//
//                    switch (e.getStatusCode()) {
//                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//
//                            try {
//                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
//                                resolvableApiException.startResolutionForResult(, 2);
//                            } catch (IntentSender.SendIntentException ex) {
//                                ex.printStackTrace();
//                            }
//                            break;
//
//                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                            //Device does not have location
//                            break;
//                    }
//                }
//            }
//        });
//    }
//
//    private boolean isGPSEnabled() {
//        LocationManager locationManager = null;
//        boolean isEnabled = false;
//
//        if (locationManager == null) {
//            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        }
//
//        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        return isEnabled;
//    }
//
//
//}
