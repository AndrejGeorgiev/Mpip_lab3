package car.andrej.lab3;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class LanLenViewModel extends AndroidViewModel {
private LanLenRepository repo;
private LiveData<List<LanLetEntity>> lista;
    public LanLenViewModel( Application application) {
        super(application);
        repo=new LanLenRepository(application);
        lista=repo.getAllLanLen();
    }

    LiveData<List<LanLetEntity>> getAllLanLen(){return lista;}

    public void insert(LanLetEntity lanLetEntity){
        repo.insert(lanLetEntity);
    }

    public void DeleteAll(){
        repo.deleteAll();
    }
}
