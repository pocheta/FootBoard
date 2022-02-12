package fr.michot.projet_android.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "player_table")
public class PlayerRoom implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "player_id")
    private final Integer playerId;
    @ColumnInfo(name = "team_id")
    private final Integer teamId;
    @ColumnInfo(name = "country_id")
    private final Integer countryId;
    @ColumnInfo(name = "position_id")
    private final Integer positionId;
    @ColumnInfo(name = "common_name")
    private final String commonName;
    @ColumnInfo(name = "display_name")
    private final String displayName;
    @ColumnInfo(name = "fullname")
    private final String fullname;
    @ColumnInfo(name = "firstname")
    private final String firstname;
    @ColumnInfo(name = "lastname")
    private final String lastname;
    @ColumnInfo(name = "nationality")
    private final String nationality;
    @ColumnInfo(name = "birthdate")
    private final String birthdate;
    @ColumnInfo(name = "birthcountry")
    private final String birthcountry;
    @ColumnInfo(name = "birthplace")
    private final String birthplace;
    @ColumnInfo(name = "height")
    private final String height;
    @ColumnInfo(name = "weight")
    private final String weight;
    @ColumnInfo(name = "image_path")
    private final String imagePath;
    @ColumnInfo(name = "favorite")
    private boolean favorite;
    @ColumnInfo(name = "team_name")
    private String teamName;

    public PlayerRoom(@NonNull Integer playerId, Integer teamId, String teamName, Integer countryId, Integer positionId, String commonName, String displayName, String fullname, String firstname, String lastname, String nationality, String birthdate, String birthcountry, String birthplace, String height, String weight, String imagePath) {
        this.playerId = playerId;
        this.teamId = teamId;
        this.teamName = teamName;
        this.countryId = countryId;
        this.positionId = positionId;
        this.commonName = commonName;
        this.displayName = displayName;
        this.fullname = fullname;
        this.firstname = firstname;
        this.lastname = lastname;
        this.nationality = nationality;
        this.birthdate = birthdate;
        this.birthcountry = birthcountry;
        this.birthplace = birthplace;
        this.height = height;
        this.weight = weight;
        this.imagePath = imagePath;
    }

    public PlayerRoom(@NonNull Player p) {
        this.playerId = p.getPlayerId();
        this.teamId = p.getTeamId();
        this.countryId = p.getCountryId();
        this.positionId = getPositionId();
        this.commonName = p.getCommonName();
        this.displayName = p.getDisplayName();
        this.fullname = p.getFullname();
        this.firstname = p.getFirstname();
        this.lastname = p.getLastname();
        this.nationality = p.getNationality();
        this.birthdate = p.getBirthdate();
        this.birthcountry = p.getBirthcountry();
        this.birthplace = p.getBirthplace();
        this.height = p.getHeight();
        this.weight = p.getWeight();
        this.imagePath = p.getImagePath();
        this.favorite = p.isFavorite();
        this.teamName = p.getTeamName();
    }

    protected PlayerRoom(Parcel in) {
        if (in.readByte() == 0) {
            playerId = null;
        } else {
            playerId = in.readInt();
        }
        if (in.readByte() == 0) {
            teamId = null;
        } else {
            teamId = in.readInt();
        }
        if (in.readByte() == 0) {
            countryId = null;
        } else {
            countryId = in.readInt();
        }
        if (in.readByte() == 0) {
            positionId = null;
        } else {
            positionId = in.readInt();
        }
        commonName = in.readString();
        displayName = in.readString();
        fullname = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        nationality = in.readString();
        birthdate = in.readString();
        birthcountry = in.readString();
        birthplace = in.readString();
        height = in.readString();
        weight = in.readString();
        imagePath = in.readString();
        favorite = in.readBoolean();
        teamName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (playerId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(playerId);
        }
        if (teamId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(teamId);
        }
        if (countryId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(countryId);
        }
        if (positionId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(positionId);
        }
        parcel.writeString(commonName);
        parcel.writeString(displayName);
        parcel.writeString(fullname);
        parcel.writeString(firstname);
        parcel.writeString(lastname);
        parcel.writeString(nationality);
        parcel.writeString(birthdate);
        parcel.writeString(birthcountry);
        parcel.writeString(birthplace);
        parcel.writeString(height);
        parcel.writeString(weight);
        parcel.writeString(imagePath);
        parcel.writeBoolean(favorite);
        parcel.writeString(teamName);
    }

    public static final Creator<PlayerRoom> CREATOR = new Creator<PlayerRoom>() {
        @Override
        public PlayerRoom createFromParcel(Parcel in) {
            return new PlayerRoom(in);
        }

        @Override
        public PlayerRoom[] newArray(int size) {
            return new PlayerRoom[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @NonNull
    public Integer getPlayerId() {
        return playerId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public String getTeamName() { return teamName; }

    public Integer getCountryId() {
        return countryId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFullname() {
        return fullname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getNationality() {
        return nationality;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getBirthcountry() {
        return birthcountry;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setTeamName(String teamName) { this.teamName = teamName; }

    @NonNull
    @Override
    public String toString() {
        return "PlayerRoom{" +
                "playerId=" + playerId +
                ", teamId=" + teamId +
                ", teamName=" + teamName +
                ", countryId=" + countryId +
                ", positionId=" + positionId +
                ", commonName='" + commonName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", fullname='" + fullname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", nationality='" + nationality + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", birthcountry='" + birthcountry + '\'' +
                ", birthplace='" + birthplace + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", favorite='" + favorite +
                '}';
    }

}
