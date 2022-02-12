package fr.michot.projet_android.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "country_table")
public class TeamRoom implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private final Integer id;
    @ColumnInfo(name = "name")
    private final String name;

    public TeamRoom(@NonNull Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public TeamRoom(@NonNull Country c){
        this.id = c.getId();
        this.name = c.getName();
    }

    public TeamRoom(Parcel in) {
        if(in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if(id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(name);
    }

    public static final Creator<TeamRoom> CREATOR = new Creator<TeamRoom>() {
        @Override
        public TeamRoom createFromParcel(Parcel parcel) {
            return new TeamRoom(parcel);
        }

        @Override
        public TeamRoom[] newArray(int size) {
            return new TeamRoom[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @NonNull
    @Override
    public String toString() {
        return "CountryRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
