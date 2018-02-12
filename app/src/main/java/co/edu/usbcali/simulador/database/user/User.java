package co.edu.usbcali.simulador.database.user;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sebastian on 30/01/2018.
 */
@Entity(indices = {@Index(value = {"document_number"}, unique = true)})
public class User implements Parcelable {

    public User() {}

    public User(int documentNumber, String firstName, String lastName, String password) {
        this.documentNumber = documentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public User(Parcel parcel) {
        id = parcel.readInt();
        documentNumber = parcel.readInt();
        firstName = parcel.readString();
        lastName = parcel.readString();
        password = parcel.readString();
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "document_number")
    private int documentNumber;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(int documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(documentNumber);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(password);
    }
}
