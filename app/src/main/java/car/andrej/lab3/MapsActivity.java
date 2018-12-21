package car.andrej.lab3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import car.andrej.lab3.Service.FirstService;
import car.andrej.lab3.wrapper.Example;
import car.andrej.lab3.wrapper.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static car.andrej.lab3.RecylceView.Adapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private LanLenViewModel viewModel;
    private static GoogleMap mMap;
    private Retrofit retrofit_agent;
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
    private static GoogleAPIService googleAPIService;
    private static final String API_KEY = "AIzaSyD1qNrBFoW5ZZzxDb6i0Mm5sr-cxYynbXI";
    private static double LON;
    private static double LAT;
    private static List<String> data=new ArrayList<>();



    public static class Breciver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            mMap.clear();
            System.out.println("DOBIV!!!");
            System.out.println("SITE EXTRAS : "+intent.getExtras());
            System.out.println("LON ANDREJ: "+intent.getDoubleExtra("lon",1));
            System.out.println("LAT ANDREJ "+intent.getDoubleExtra("lat",1));
            LON=intent.getDoubleExtra("lon",1);
            LAT=intent.getDoubleExtra("lat",1);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(LAT,LON),6));

           String location= String.valueOf(LAT)+","+String.valueOf(LON);

            googleAPIService.getInfo(location,API_KEY,"5000","restaurant").enqueue(new Callback<Example>() {

                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {
                    data.clear();
                    if(response.isSuccessful()){

                        System.out.println("RESPONSE VO BODY :" + response.body());

                        List<Result> lista_rezultati=response.body().getResults();
                        for(Result result:lista_rezultati){
                        car.andrej.lab3.wrapper.Location loc=result.getGeometry().getLocation();
                        Double lat=loc.getLat();
                        Double lon=loc.getLng();
                        String name=result.getName();
                        data.add(name);



                        mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon))).setTitle(name);


                        }
                        adapter.updateData(data);




                    }
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {

                }
            });




        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setup_adapter();

        viewModel = ViewModelProviders.of(this).get(LanLenViewModel.class);
        IntentFilter filter = new IntentFilter("mk.ukim.finki.mpip.IMPLICIT_ACTION");
        Breciver receiver = new Breciver();
        registerReceiver(receiver, filter);




        init_retrofit();













        Intent serv_intent=new Intent(this,FirstService.class);
        startService(serv_intent);






    }


    public void setup_adapter(){
        data=new ArrayList<>();
        data.add("EMPTY");
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new car.andrej.lab3.RecylceView.Adapter(data,MapsActivity.this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
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


        requestLocationPermisions();


    }

    public void init_retrofit(){

        retrofit_agent = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        googleAPIService = retrofit_agent.create(GoogleAPIService.class);


    }



    public void makeCall(String location,String kluc){
        googleAPIService.getInfo(location,API_KEY,"5000","restaurant").enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if(response.isSuccessful()){

                    System.out.println("RESPONSE VO BODY :" + response.body());


                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 69: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    System.out.println("DADENO");
                    mMap.setMyLocationEnabled(true);

                    // contacts-related task you need to do.
                } else {
                    System.out.println("NE E DADENO");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }

    }


    public void requestLocationPermisions(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},69);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


            return;
        }
        mMap.setMyLocationEnabled(true);


    }




}
