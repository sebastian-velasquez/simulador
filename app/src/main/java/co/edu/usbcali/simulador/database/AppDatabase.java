package co.edu.usbcali.simulador.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import co.edu.usbcali.simulador.database.account.Account;
import co.edu.usbcali.simulador.database.account.AccountDao;
import co.edu.usbcali.simulador.database.movement.Movement;
import co.edu.usbcali.simulador.database.movement.MovementDao;
import co.edu.usbcali.simulador.database.user.User;
import co.edu.usbcali.simulador.database.user.UserDao;

/**
 * Created by Sebastian on 30/01/2018.
 */
@Database(entities = {User.class, Account.class, Movement.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase{
    public abstract UserDao userDao();
    public abstract AccountDao accountDao();
    public abstract MovementDao movementDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "bank-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
