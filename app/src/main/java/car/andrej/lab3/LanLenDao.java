package car.andrej.lab3;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LanLenDao {
    @Insert
    void insert(LanLetEntity lanLetEntity);


    @Query("DELETE from LanLetTable")
    void deleteAll();

    @Query("Select * from LanLetTable")
    LiveData<List<LanLetEntity>> getAllLanLet();




}
