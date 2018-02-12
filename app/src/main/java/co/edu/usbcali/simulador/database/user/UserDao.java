package co.edu.usbcali.simulador.database.user;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Sebastian on 30/01/2018.
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> all();

    @Query("SELECT * FROM user WHERE id = (:id) LIMIT 1")
    User findById(int id);

    @Query("SELECT * FROM user WHERE document_number = (:documentNumber) LIMIT 1")
    User findByDocumentNumber(int documentNumber);

    @Query("SELECT * FROM user WHERE document_number IN (:documentNumbers)")
    List<User> getAllByDocuments(int[] documentNumbers);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Query("SELECT COUNT(*) from user")
    int count();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(User... users);

    @Delete
    void delete(User user);
}
