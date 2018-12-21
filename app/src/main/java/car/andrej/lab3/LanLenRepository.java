package car.andrej.lab3;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class LanLenRepository {
    private LanLenDao lanLenDao;
    private LiveData<List<LanLetEntity>> lista;

    public LanLenRepository(Application application) {
        LanLetDatabase db = LanLetDatabase.getDatabase(application);
        lanLenDao = db.lanLenDao();
        lista=lanLenDao.getAllLanLet();
    }

    LiveData<List<LanLetEntity>> getAllLanLen(){
        return lista;
    }

    public void insert(final LanLetEntity lanLetEntity){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                lanLenDao.insert(lanLetEntity);
                return null;
            }
        }.execute();
    }

    public void deleteAll(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                lanLenDao.deleteAll();
                return null;
            }
        }.execute();
    }



}
