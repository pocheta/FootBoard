package fr.michot.projet_android.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Team implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("legacy_id")
    @Expose
    private Integer legacy_id;
    @SerializedName("name")
    @Expose
    private String name;

    protected Team(Parcel in){
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            legacy_id = null;
        } else {
            legacy_id = in.readInt();
        }
        name = in.readString();
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLegacy_id() {
        return legacy_id;
    }

    public void setLegacy_id(Integer legacy_id) {
        this.legacy_id = legacy_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        if (legacy_id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(legacy_id);
        }
        parcel.writeString(name);
    }

    @NonNull
    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                "legacy_id=" + legacy_id +
                ", name='" + name + '\'' +
                '}';
    }
}
