package fr.michot.projet_android.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Player implements Parcelable {

    @SerializedName("player_id")
    @Expose
    private Integer playerId;
    @SerializedName("team_id")
    @Expose
    private Integer teamId;
    @SerializedName("country_id")
    @Expose
    private Integer countryId;
    @SerializedName("position_id")
    @Expose
    private Integer positionId;
    @SerializedName("common_name")
    @Expose
    private String commonName;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("birthdate")
    @Expose
    private String birthdate;
    @SerializedName("birthcountry")
    @Expose
    private String birthcountry;
    @SerializedName("birthplace")
    @Expose
    private String birthplace;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("image_path")
    @Expose
    private String imagePath;
    private boolean favorite;
    private String teamName;

    protected Player(Parcel in) {
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

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getBirthcountry() {
        return birthcountry;
    }

    public void setBirthcountry(String birthcountry) {
        this.birthcountry = birthcountry;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
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

    @NonNull
    @Override
    public String toString() {
        return "Player{" +
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
                ", favorite=" + favorite +
                '}';
    }
}