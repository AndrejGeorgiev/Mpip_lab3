package car.andrej.lab3.Service;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import car.andrej.lab3.GoogleAPIService;
import car.andrej.lab3.LanLenViewModel;
import car.andrej.lab3.MapsActivity;
import car.andrej.lab3.wrapper.Example;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirstService extends IntentService {
    private FusedLocationProviderClient mFusedLocationClient;


    private LanLenViewModel viewModel;


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FirstService(String name) {
        super(name);
    }
    public FirstService() {
        super("FirstService");
    }

    @Override
    public void onCreate() {
        super.onCreate();




    }

    @Override
    protected void onHandleIntent( Intent intent) {

        System.out.println("STIGNAV!!!");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        while(true) {

            System.out.println("VRAKAM PODATOCI Vo MINUTE 1");
            getLastLocation();
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }




    }

    public void getLastLocation(){



        @SuppressLint("MissingPermission") final Task location = mFusedLocationClient.getLastLocation();
        location.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful() && task.getResult()!=null) {

                    Location currentLocation = (Location) task.getResult();


                    Double lon=currentLocation.getLongitude();
                    Double lat=currentLocation.getLatitude();


                    Intent l=new Intent();
                    l.setAction("mk.ukim.finki.mpip.IMPLICIT_ACTION");
                    l.putExtra("lon",lon);
                    l.putExtra("lat",lat);
                    sendBroadcast(l);




                } else {

                    Toast.makeText(FirstService.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }





}
