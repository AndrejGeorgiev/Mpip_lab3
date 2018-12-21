package car.andrej.lab3;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {LanLetEntity.class},version=1)
public abstract class LanLetDatabase extends RoomDatabase {
public abstract LanLenDao lanLenDao();
    private static volatile LanLetDatabase INSTANCE;

    static LanLetDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LanLetDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LanLetDatabase.class, "LanLen_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
