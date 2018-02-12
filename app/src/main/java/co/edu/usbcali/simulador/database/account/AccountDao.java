package co.edu.usbcali.simulador.database.account;

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
public interface AccountDao {
    @Query("SELECT * FROM account")
    List<Account> all();

    @Query("SELECT * FROM account WHERE id = (:id) LIMIT 1")
    Account findById(int id);

    @Query("SELECT * FROM account WHERE id IN (:ids)")
    List<Account> getAllByIds(int[] ids);

    @Query("SELECT * FROM account WHERE user_id = (:user_id)")
    List<Account> getAllByUserId(int user_id);

    @Query("SELECT COUNT(*) from account")
    int count();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Account... accounts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(Account... Accounts);

    @Delete
    void delete(Account accounts);
}
