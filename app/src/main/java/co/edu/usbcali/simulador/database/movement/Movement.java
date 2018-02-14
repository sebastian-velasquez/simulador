package co.edu.usbcali.simulador.database.movement;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import co.edu.usbcali.simulador.database.account.Account;
import co.edu.usbcali.simulador.database.user.User;

/**
 * Created by eiso-10 on 2/2/18.
 */

@Entity(
        indices = {
                @Index("user_id"),
                @Index("destination")
        },
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id"),
                @ForeignKey(entity = Account.class, parentColumns = "id", childColumns = "destination")
        }
)
public class Movement {

    public Movement() {}

    public Movement(int id, int userId, int destination, double value, int type) {
        this.id = id;
        this.userId = userId;
        this.destination = destination;
        this.value = value;
        this.type = type;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "destination")
    private int destination;

    @ColumnInfo(name = "type")
    private int type;

    @ColumnInfo(name = "value")
    private double value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
