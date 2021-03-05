package com.example.countryinfo;

import androidx.fragment.app.FragmentActivity;

import android.gesture.OrientedBoundingBox;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.WindowInsetsAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.countryinfo.Model.Geognos;
import com.example.countryinfo.WebService.Asynchtask;
import com.example.countryinfo.WebService.WebService;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

public class InformationMap extends FragmentActivity implements OnMapReadyCallback, Asynchtask {

    private GoogleMap mMap;
    ImageView imageView;
    TextView txtResulJSon,txtname;
    String path_fag ="http://www.geognos.com/api/en/countries/flag/";
    Geognos g = new Geognos();
    private Marker marker;
    LatLng myposition;
    Double latLng,longitude;
    PolylineOptions lineas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        imageView = findViewById(R.id.imgFlag);
        txtResulJSon = findViewById(R.id.txtResultJson);

        txtname= findViewById(R.id.txtName);


        Bundle bundle = this.getIntent().getExtras();
        path_fag = path_fag + bundle.getString("name") +".png";

        ObtenerBandera();
        lineas = new PolylineOptions();
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("http://www.geognos.com/api/en/countries/info/"+bundle.getString("name")+".json",
                datos, InformationMap.this, InformationMap.this);
        ws.execute("GET");
    }

    void ObtenerBandera(){
        //http://www.geognos.com/api/en/countries/flag/EC.png
        Glide.with(InformationMap.this)
                .load(path_fag)
                .into(imageView);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        myposition = new LatLng(-1.574854, -78.290045);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myposition));
    }

    @Override
    public void processFinish(String result) throws JSONException {

        try {

            JSONObject objetoJson = new JSONObject(result);
            JSONObject jsonObjectPrin = objetoJson.getJSONObject("Results");

            g.setName(jsonObjectPrin.getString("Name"));

            JSONObject jsonCapital = jsonObjectPrin.getJSONObject("Capital");
            g.setCapital(jsonCapital.getString("Name"));

            JSONObject jsonGeoRectangle = jsonObjectPrin.getJSONObject("GeoRectangle");
            g.setWest(jsonGeoRectangle.getString("West"));
            g.setEast(jsonGeoRectangle.getString("East"));
            g.setNorth(jsonGeoRectangle.getString("North"));
            g.setSouth(jsonGeoRectangle.getString("South"));

            obtenerRectangle();

            g.setTelPref(jsonObjectPrin.getString("TelPref"));

            JSONObject jsonCountryCodes = jsonObjectPrin.getJSONObject("CountryCodes");
            g.setTld(jsonCountryCodes.getString("tld"));
            g.setIso3(jsonCountryCodes.getString("iso3"));
            g.setIso2(jsonCountryCodes.getString("iso2"));
            g.setFips(jsonCountryCodes.getString("fips"));
            g.setIsoN(jsonCountryCodes.getString("isoN"));

            String resp="";

            resp = "Capital :  " + g.getName() + "\n";
            resp = resp + "Code tld :  " + g.getTld() + "\n";
            resp = resp + "Code iso3 :  " + g.getIso3() + "\n";
            resp = resp + "Code iso2 :  " + g.getIso2() + "\n";
            resp = resp + "Code fips :  " + g.getFips() + "\n";
            resp = resp + "Code isoN :  " + g.getIsoN() + "\n";
            resp = resp + "Code TelPref :  " + g.getTelPref() + "\n";

            JSONArray JSONlista =  jsonObjectPrin.getJSONArray("GeoPt");

            Double a = JSONlista.getDouble(0);
            Double b = JSONlista.getDouble(1);

            myposition = new LatLng(a,b);
            marker = mMap.addMarker(new MarkerOptions()
                    .position(myposition)
                    .title("My position").draggable(true));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myposition));
            CameraPosition camPos = new CameraPosition.Builder()
                    .target(myposition)
                    .zoom(3)
                    .build();
            CameraUpdate camUpd3 =
                    CameraUpdateFactory.newCameraPosition(camPos);
            mMap.animateCamera(camUpd3);

            txtResulJSon.setText(resp);
            txtname.setText(g.getName());

        }catch (JSONException e)
        {
            Toast.makeText(this.getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
        }
    }

    void obtenerRectangle(){
        lineas = new PolylineOptions().
                add( new LatLng (Double.parseDouble(g.getNorth()),Double.parseDouble(g.getWest()))).
                add( new LatLng (Double.parseDouble(g.getNorth()),Double.parseDouble(g.getEast()))).
                add( new LatLng (Double.parseDouble(g.getSouth()),Double.parseDouble(g.getEast()))).
                add( new LatLng (Double.parseDouble(g.getSouth()),Double.parseDouble(g.getWest()))).
                add( new LatLng (Double.parseDouble(g.getNorth()),Double.parseDouble(g.getWest())));
        lineas.color(Color.RED);
        mMap.addPolyline(lineas);

    }
}