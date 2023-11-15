package com.example.uber;

import androidx.appcompat.app.AppCompatActivity;
import android.location.Address;
import android.location.Geocoder;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.MarkerOptions;
        import java.io.IOException;
        import java.util.List;
public class MapActivity extends AppCompatActivity  implements OnMapReadyCallback {
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        EditText editText = findViewById(R.id.editTextInput);
        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
                String locationText = editText.getText().toString();
                // Now 'locationText' contains the text entered in the EditText
                // You can use this text to perform any necessary actions, such as displaying it on the map.
                // Call a method to show the location on the map
                showLocationOnMap(locationText);
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(48.866667, 2.333333);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void showLocationOnMap(String locationText) {
        // Use Geocoder to get latitude and longitude from location text
        // For simplicity, let's assume 'getLatLngFromLocationText' is a method you implement
        LatLng locationLatLng = getLatLngFromLocationText(locationText);
        // Move the camera to the specified location on the map
        if (locationLatLng != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 15f));
            // You can add a marker if needed
            mMap.addMarker(new MarkerOptions().position(locationLatLng).title("Marker at " + locationText));
        } else {
            // Handle the case where locationText couldn't be converted to coordinates
            Toast.makeText(this, "Invalid location", Toast.LENGTH_SHORT).show();
        }
    }
    private LatLng getLatLngFromLocationText(String locationText) {
        // Use Geocoder to get LatLng from location text
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName(locationText, 1);
            if (!addresses.isEmpty()) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                return new LatLng(latitude, longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
