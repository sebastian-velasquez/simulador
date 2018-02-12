package co.edu.usbcali.simulador.database.movement;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by eiso-10 on 2/2/18.
 */

@Dao
public interface MovementDao {
    @Query("SELECT * FROM movement")
    List<Movement> all();

    @Query("SELECT * FROM movement WHERE id = (:id) LIMIT 1")
    Movement findById(int id);

    @Query("SELECT * FROM movement WHERE id IN (:ids)")
    List<Movement> getAllByIds(int[] ids);

    @Query("SELECT COUNT(*) from movement")
    int count();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Movement... movements);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(Movement... movements);

    @Delete
    void delete(Movement transactions);
}
