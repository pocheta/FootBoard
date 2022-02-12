package fr.michot.projet_android.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "country_table")
public class CountryRoom implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private final Integer id;
    @ColumnInfo(name = "name")
    private final String name;
    @ColumnInfo(name = "image_path")
    private final String imagePath;

    public CountryRoom(@NonNull Integer id, String name, String imagePath){
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
    }

    public CountryRoom(@NonNull Country c){
        this.id = c.getId();
        this.name = c.getName();
        this.imagePath = c.getImagePath();
    }

    public CountryRoom(Parcel in) {
        if(in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        imagePath = in.readString();
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
        parcel.writeString(imagePath);
    }

    public static final Creator<CountryRoom> CREATOR = new Creator<CountryRoom>() {
        @Override
        public CountryRoom createFromParcel(Parcel parcel) {
            return new CountryRoom(parcel);
        }

        @Override
        public CountryRoom[] newArray(int size) {
            return new CountryRoom[size];
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

    public String getImagePath() {
        return imagePath;
    }

    @NonNull
    @Override
    public String toString() {
        return "CountryRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
