package co.edu.usbcali.simulador.database.account;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import co.edu.usbcali.simulador.database.user.User;

/**
 * Created by eiso-10 on 2/2/18.
 */
@Entity(
        indices = {@Index("user_id")},
        foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id")
)
public class Account implements Parcelable {

    public Account() {}

    public Account(int id, int userId, double netBalance, int type) {
        this.id = id;
        this.userId = userId;
        this.netBalance = netBalance;
        this.type = type;
    }

    protected Account(Parcel parcel) {
        id = parcel.readInt();
        userId = parcel.readInt();
        netBalance = parcel.readDouble();
        type = parcel.readInt();
    }


    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "net_balance")
    private double netBalance;

    @ColumnInfo(name = "type")
    private int type;

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

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

    public double getNetBalance() {
        return netBalance;
    }

    public void setNetBalance(double netBalance) {
        this.netBalance = netBalance;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(userId);
        parcel.writeDouble(netBalance);
        parcel.writeInt(type);
    }
}
